import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void testGetConnection() throws SQLException {
        DBConnection db = new DBConnection();
        Connection c = db.getConnection();
        Connection c2 = db.getConnection();
        assertEquals(c,c2);
    }

    /*
    @Test
    void testClassConnection() throws SQLException {
        DBConnection db = new DBConnection();
        assertEquals(Connection.class, db.getConnection().getClass());
    }
     */
}