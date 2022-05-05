package es.diadam.diadam.managers;

import es.diadam.diadam.utils.Properties;
import lombok.NonNull;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.*;
import java.util.Optional;


/**
 * @Author Iván Azagra Troya
 * Realmente Jose Luis pero vamos a hacer como que no me estoy "inspirando"
 */
public class ManagerBBDD {
    private static ManagerBBDD controller;
    // No leemos de propiedades
    private final boolean  fromProperties = false;
    private String serverUrl;
    private String serverPort;
    private String dataBaseName;
    private String user;
    private String password;

    private String jdbcDriver;
    // Para manejar las conexiones y respuestas
    private Connection connection;
    private PreparedStatement preparedStatement;

    /**
     * Constructor privado para Singleton
     * Inicializa la configuración de acceso al servidor y abre la conexión
     */
    private ManagerBBDD() {
        if(fromProperties) {
            // initConfigFromFiles()
        } else {
            initConfig();
        }
    }

    public static ManagerBBDD getInstance() {
        if(controller == null) {
            controller = new ManagerBBDD();
        }
        return controller;
    }

    // Con sqlite solo hace falta el driver, como es para testeo solo pondré el jdbc
    // En el initConfigFromFiles irán todos los componentes para dar la posibilidad
    // A poder cambiar todos los componentes a preferencia del usuario.
    private void initConfig() {
        jdbcDriver = "org.sqlite.JDBC";
    }

    // Carga la configuración de acceso al servidor de base de datos desde properties
    /*private void initConfigFromFiles() {
        ApplicationProperties properties = new ApplicationProperties();
        serverUrl = properties.readProperty("database.server.url");
        serverPort = properties.readProperty("database.server.port");
        dataBaseName = properties.readProperty("database.name");
        jdbcDriver = properties.readProperty("database.jdbc.driver");
        user = properties.readProperty("database.user");
        password = properties.readProperty("database.password");
    }*/

    /**
     * Abre la base de datos
     * @throws SQLException
     */
    public void open() throws SQLException {
        // TODO(cLASE PROPERTIES PARA LAS DEPENDENCIAS DEL MANAGER DE BBDD)
        String url = "jdbc:sqlite:" + Properties.DB_FILE;
        connection = DriverManager.getConnection(url);
    }

    /**
     * Cierra la conexión a la base de datos
     * @throws SQLException
     */
    public void close() throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }

    /**
     * Realiza una consulta con el preparedStatement
     * @param querySQL
     * @param params
     * @return
     * @throws SQLException
     */
    private ResultSet executeQuery(@NonNull String querySQL, Object... params) throws SQLException {
        preparedStatement = connection.prepareStatement(querySQL);
        for(int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    /**
     * Realiza una consulta select de manera preparada con preparedStatement
     * @param querySQL
     * @param params
     * @return
     * @throws SQLException
     */
    public Optional<ResultSet> select(@NonNull String querySQL, Object... params) throws SQLException {
        return Optional.of(executeQuery(querySQL, params));
    }

    /**
     * Realiza una consulta select de manera preparada con preparedStatement
     * @param querySQL
     * @param limit
     * @param offset
     * @param params
     * @return
     * @throws SQLException
     */
    public Optional<ResultSet> select(@NonNull String querySQL, int limit, int offset, Object... params) throws SQLException{
        String query = querySQL + " LIMIT "+ limit + "OFFSET" + offset;
        return Optional.of(executeQuery(query, params));
    }

    /**
     * Hace una inserción en la base de datos de forma preparada
     * @param insertSQL
     * @param params
     * @return
     * @throws SQLException
     */
    public Optional<ResultSet> insert(@NonNull String insertSQL, Object... params) throws SQLException {
        preparedStatement = connection.prepareStatement(insertSQL, preparedStatement.RETURN_GENERATED_KEYS);
        for(int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        preparedStatement.executeUpdate();
        return Optional.of(preparedStatement.getGeneratedKeys());
    }

    /**
     * Realiza una consulta de tipo update de manera preparada
     * @param updateSQL
     * @param params
     * @return
     * @throws SQLException
     */
    public int update(@NonNull String updateSQL, Object... params) throws SQLException {
        return updateQuery(updateSQL, params);
    }

    /**
     * Realiza un delete a la base de datos de forma preparada
     * @param deleteSQL
     * @param params
     * @return
     * @throws SQLException
     */
    public int delete(@NonNull String deleteSQL, Object... params) throws SQLException {
        return updateQuery(deleteSQL, params);
    }

    /**
     * Realiza un update de forma preparada
     * @param genericSQL
     * @param params
     * @return
     * @throws SQLException
     */
    private int updateQuery(@NonNull String genericSQL, Object... params) throws SQLException {
        preparedStatement = connection.prepareStatement(genericSQL);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i+ 1, params[i]);
        }
        return preparedStatement.executeUpdate();
    }

    /**
     * Crea una consulta genérica para crear tablas, vistas y procedimientos
     * @param genericSQL
     * @return
     * @throws SQLException
     */
    public int genericUpdate(@NonNull String genericSQL) throws SQLException {
        preparedStatement = connection.prepareStatement(genericSQL);
        return preparedStatement.executeUpdate();
    }

    /**
     * Carga los datos desde un fichero externo
     * @param sqlFile
     * @param logWriter
     * @throws FileNotFoundException
     */
    public void initData(@NonNull String sqlFile, boolean logWriter) throws FileNotFoundException {
        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new FileReader(sqlFile));
        sr.setLogWriter(logWriter ? new PrintWriter(System.out): null);
        sr.runScript(reader);
    }

    /**
     * Inicia una transacción
     * @throws SQLException
     */
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    /**
     * Cancela una transacción
     * @throws SQLException
     */
    public void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollback() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }
}
