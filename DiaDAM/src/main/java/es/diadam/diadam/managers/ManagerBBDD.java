package es.diadam.diadam.managers;

import es.diadam.diadam.utils.Properties;
import lombok.NonNull;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.*;
import java.util.Optional;


/**
 * @author Iván Azagra Troya
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
        if (fromProperties) {
            // initConfigFromFiles()
            System.out.println("Inicialización desde archivos comentado");
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
        serverUrl = "localhost";
        serverPort = "3306";
        dataBaseName = "diadam.sqlite";
        jdbcDriver = "org.sqlite.JDBC";
        user = "";
        password = "";
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
     * @throws SQLException Error con la base de datos
     */
    public void open() throws SQLException {
        String url = "jdbc:sqlite:" + Properties.DB_FILE;
        connection = DriverManager.getConnection(url);
    }

    /**
     * Cierra la conexión a la base de datos
     * @throws SQLException servidor no accesible
     */
    public void close() throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }

    /**
     * Realiza una consulta con el preparedStatement
     * @param querySQL consulta la base con lo introducido
     * @param  params parámetros de consulta parametrizada
     * @return Query del prepared statement
     * @throws SQLException error con la base de datos
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
     * @param querySQL consulta la base con lo introducido
     * @param  params parámetros de consulta parametrizada
     * @return Optional de la consulta
     * @throws SQLException error con la base de datos
     */
    public Optional<ResultSet> select(@NonNull String querySQL, Object... params) throws SQLException {
        return Optional.of(executeQuery(querySQL, params));
    }

    /**
     * Realiza una consulta select de manera preparada con preparedStatement
     * @param querySQL consulta a la base de datos
     * @param limit número de registros de la página
     * @param offset desplazamiento de los registros
     * @param params parámetros de consulta parametrizada
     * @return Optional de la consulta
     * @throws SQLException error con la base de datos
     */
    public Optional<ResultSet> select(@NonNull String querySQL, int limit, int offset, Object... params) throws SQLException{
        String query = querySQL + " LIMIT "+ limit + "OFFSET" + offset;
        return Optional.of(executeQuery(query, params));
    }

    /**
     * Hace una inserción en la base de datos de forma preparada
     * @param insertSQL consulta a la base de tipo inserción
     * @param params parámetros de consulta parametrizada
     * @return Optional de la consulta preparada con la clave del registro
     * @throws SQLException error con la base de datos
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
     * @param updateSQL Consulta a la base de datos  de tipo actualización
     * @param params parámetros para consulta parametrizada
     * @return consulta actualizada
     * @throws SQLException error con la base de datos
     */
    public int update(@NonNull String updateSQL, Object... params) throws SQLException {
        return updateQuery(updateSQL, params);
    }

    /**
     * Realiza un delete a la base de datos de forma preparada
     * @param deleteSQL consulta a la base de datos de tipo borrado
     * @param params parámetros de consulta parametrizada
     * @return consulta actualizada con el borrado
     * @throws SQLException error con la base de datos
     */
    public int delete(@NonNull String deleteSQL, Object... params) throws SQLException {
        return updateQuery(deleteSQL, params);
    }

    /**
     * Realiza un update de forma preparada
     * @param genericSQL consulta genérica a la base de datos
     * @param params parámetros de consulta paramétrizada
     * @return número de actualizaciones
     * @throws SQLException error en la base de datos
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
     * @param genericSQL consulta genérica a la base de datos
     * @return actualización
     * @throws SQLException error con la base de datos
     */
    public int genericUpdate(@NonNull String genericSQL) throws SQLException {
        preparedStatement = connection.prepareStatement(genericSQL);
        return preparedStatement.executeUpdate();
    }

    /**
     * Carga los datos desde un fichero externo
     * @param sqlFile archivo donde se almacena la base de datos
     * @param logWriter booleano de escritura
     * @throws FileNotFoundException error por no poder localizar el archivo
     */
    public void initData(@NonNull String sqlFile, boolean logWriter) throws FileNotFoundException {
        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new FileReader(sqlFile));
        sr.setLogWriter(logWriter ? new PrintWriter(System.out): null);
        sr.runScript(reader);
    }

    /**
     * Inicia una transacción
     * @throws SQLException error con la base de datos
     */
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    /**
     * Confirma una transacción
     * @throws SQLException error con la base de datos
     */
    public void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    /**
     * Cancela una transacción
     * @throws SQLException error con la base de datos
     */
    public void rollback() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }
}
