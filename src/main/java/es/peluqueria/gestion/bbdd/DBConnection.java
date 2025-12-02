package es.peluqueria.gestion.bbdd;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String PROPS_FILE = "/db.properties";
    private static String URL;
    private static String USER;
    private static String PASS;

    static {
        try (InputStream in = DBConnection.class.getResourceAsStream(PROPS_FILE)) {
            Properties p = new Properties();
            p.load(in);
            URL = p.getProperty("db.url");
            USER = p.getProperty("db.user");
            PASS = p.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            throw new ExceptionInInitializerError("Error cargando db.properties: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
