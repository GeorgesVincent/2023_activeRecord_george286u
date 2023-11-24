import java.sql.*;
import java.util.ArrayList;

public class Personne {

    private int id;
    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = -1;
    }
    public void delete() throws SQLException {
        DBConnection dbco = new DBConnection();
        Connection connect = dbco.getConnection();
        PreparedStatement stmt = connect.prepareStatement("DELETE FROM Personne WHERE ID = ? ");
        stmt.setInt(1,this.id);
        stmt.executeUpdate();
    }
    public void save() throws SQLException {
        if (this.id == -1) {
            saveNew();
        }else{
            update();
        }
    }
    private void saveNew() throws SQLException {
        DBConnection dbco = new DBConnection();
        Connection connect = dbco.getConnection();
        String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
        PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.executeUpdate();
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
        }
    }
    private void update() throws SQLException {
        DBConnection dbco = new DBConnection();
        Connection connect = dbco.getConnection();
        String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.setInt(3, this.id);
        prep.execute();
    }
    public static void createTable() throws SQLException {
        DBConnection dbco = new DBConnection();
        Connection connect = dbco.getConnection();
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
        System.out.println("Creation table Personne\n");
    }
    public static void deleteTable() throws SQLException {
        DBConnection dbco = new DBConnection();
        Connection connect = dbco.getConnection();
        String drop = "DROP TABLE Personne";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
        System.out.println("Suppression table Personne");
    }
    public static ArrayList<Personne> findAll(){
        ArrayList<Personne> listePers = null;
        try {
            DBConnection bdconnect = new DBConnection();
            Connection co = bdconnect.getConnection();
            String SQLPrep = "SELECT * FROM Personne;";
            PreparedStatement prep1 = co.prepareStatement(SQLPrep);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            listePers = new ArrayList<>();
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int id = rs.getInt("id");
                Personne p = new Personne(nom,prenom);
                p.id=id;
                listePers.add(p);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return listePers;
    }
    public static Personne findById(int idCherche){
        Personne p =null;
        try {
            DBConnection bdconnect = new DBConnection();
            Connection co = bdconnect.getConnection();
            String SQLPrep = "SELECT * FROM Personne;";
            PreparedStatement prep1 = co.prepareStatement(SQLPrep);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int id = rs.getInt("id");
                if (id==idCherche){
                    p = new Personne(nom,prenom);
                    p.id=id;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return p;
    }
    public static ArrayList<Personne> findByName(String nomCherche){
        ArrayList<Personne> listePers = null;
        try {
            DBConnection bdconnect = new DBConnection();
            Connection co = bdconnect.getConnection();
            String SQLPrep = "SELECT * FROM Personne;";
            PreparedStatement prep1 = co.prepareStatement(SQLPrep);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            listePers = new ArrayList<>();
            // s'il y a un resultat
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int id = rs.getInt("id");
                if (nomCherche.equals(nom)) {
                    Personne p = new Personne(nom, prenom);
                    p.id = id;
                    listePers.add(p);
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return listePers;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public int getId() {
        return id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o){
        boolean b = false;
        Personne p = (Personne) o;
        if (this.id == p.getId() && this.nom.equals(p.getNom()) && this.prenom.equals(p.getPrenom())){
            b=true;
        }
        return b;
    }
}
