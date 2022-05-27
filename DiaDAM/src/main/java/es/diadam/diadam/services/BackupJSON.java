package es.diadam.diadam.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import es.diadam.diadam.models.Persona;
import es.diadam.diadam.utils.LocalDateAdapter;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

public class BackupJSON implements IBackupJSON{
    private static BackupJSON instance;

    private final String APP_PATH = System.getProperty("user.dir");
    private final String DATA_DIR = APP_PATH + File.separator + "data";
    private final String BACKUP_FILE = DATA_DIR + File.separator + "backup" + File.separator;

    private BackupJSON(){
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    public static BackupJSON getInstance(){
        if (instance == null){
            instance = new BackupJSON();
        }
        return instance;
    }


    @Override
    public void backup(List<Persona> persona) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(persona);
        Files.writeString(new File(BACKUP_FILE +  "usuarios.json").toPath(), json);

    }

    @Override
    public ObservableList<Persona> restore() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String json = "";
        json = Files.readString(new File(BACKUP_FILE + "usuarios.json").toPath());
        return gson.fromJson(json, new TypeToken<List<Persona>>(){
        }.getType());
    }
}
