package es.diadam.diadam.services;


import es.diadam.diadam.dto.ProductoDTO;
import es.diadam.diadam.utils.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Jorge Sánchez Berrocoso
 */
public class Storage {
    private static Storage instance;
    Logger logger = LogManager.getLogger(Storage.class);

    private Storage() {
        makeDirectory();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    private void makeDirectory() {
        if (!Files.exists(Paths.get(Properties.DATA_DIR))) {
            try {
                Files.createDirectory(Paths.get(Properties.DATA_DIR));
                Files.createDirectory(Paths.get(Properties.BACKUP_DIR));
                Files.createDirectory(Paths.get(Properties.IMAGES_DIR));
                // Imagenes
            } catch (IOException e) {
                logger.error("Error al crear directorio de trabajo de la aplicación");
            }
        }
    }

    public void backup(List<ProductoDTO> personas) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                Files.newOutputStream(Paths.get(Properties.BACKUP_FILE))
        );
        oos.writeObject(personas);
        oos.close();
    }

    public List<ProductoDTO> restore() throws IOException, ClassNotFoundException {
        List<ProductoDTO> personas = new ArrayList<>();
        ObjectInputStream ois = new ObjectInputStream(
                Files.newInputStream(Paths.get(Properties.BACKUP_FILE))
        );
        personas = (List<ProductoDTO>) ois.readObject();
        ois.close();
        return personas;
    }

    public void copyFile(String source, String destination) throws IOException {
        System.out.println("Copiando " + source + " a " + destination);
        if (Files.exists(Paths.get(source))) {
            Files.copy(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } else {
            logger.warn("No existe el archivo " + source);
        }
    }

    public void deleteFile(String file) throws IOException {
        System.out.println("Borrando " + file);
        if (Files.exists(Paths.get(file))) {
            Files.delete(Paths.get(file));
        } else {
            logger.warn("No existe el archivo " + file);
        }
    }
}
