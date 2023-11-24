import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.DriverManager;

public class DBConnection {
    private static DBConnection instance;
    private Connection connect;
    private String userName = "root";
    private String password = "";
    private String serverName = "localhost";
    //Attention, sous MAMP, le port est 8889
    private String portNumber = "3306";
    private String ancientNom;
    private String dbName = "testpersonne";

    public DBConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        this.connect = DriverManager.getConnection(urlDB, connectionProps);
        this.ancientNom=null;
    }
    public DBConnection(String nom) throws SQLException {
        this.dbName=nom;
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        this.connect = DriverManager.getConnection(urlDB, connectionProps);
        this.ancientNom=null;
    }
    public Connection getConnection() throws SQLException {
        if (instance == null){
            instance = new DBConnection();
        }
        if (this.ancientNom != null && !this.ancientNom.equals(this.dbName)){
            instance=new DBConnection(dbName);
        }
        return instance.connect;
    }
    public void setNomDB(String nomDB){
        this.ancientNom=this.dbName;
        this.dbName=nomDB;
    }

}
