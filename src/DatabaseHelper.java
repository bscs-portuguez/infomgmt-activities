import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String DB_PATH = System.getProperty("user.dir") + "/portuguez.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS portuguez (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    username TEXT NOT NULL UNIQUE,\n" +
                    "    password TEXT NOT NULL,\n" +
                    "    first_name TEXT,\n" +
                    "    middle_initial TEXT,\n" +
                    "    last_name TEXT,\n" +
                    "    email TEXT,\n" +
                    "    birthday TEXT,\n" +
                    "    date_registered TEXT,\n" +
                    "    role TEXT NOT NULL DEFAULT 'user'\n" +
                    ");";
            stmt.execute(sql);

            sql = "INSERT OR IGNORE INTO portuguez (username, password, role) " +
                    "VALUES ('admin', 'password', 'admin');";
            stmt.execute(sql);

            System.out.println("Database initialized at: " + DB_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
