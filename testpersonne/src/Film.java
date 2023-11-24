import java.sql.*;
public class Film {
    private int id;
    private String titre;
    private int id_rea;
    public Film(String titre, Personne p) {
        this.titre = titre;
        this.id_rea = p.getId();
        this.id = -1;
    }
    public void delete() throws SQLException {
        DBConnection dbco = new DBConnection();
        Connection connect = dbco.getConnection();
        PreparedStatement stmt = connect.prepareStatement("DELETE FROM Film WHERE ID = ? ");
        stmt.setInt(1,this.id);
        stmt.executeUpdate();
    }
    public void save() throws SQLException, RealisateurAbsentException {
        if (this.id == -1) {
            saveNew();
        }else{
            update();
        }
    }
    private void saveNew() throws SQLException, RealisateurAbsentException {
        if (this.id_rea==-1){
            throw new RealisateurAbsentException();
        }
        else {
            DBConnection dbco = new DBConnection();
            Connection connect = dbco.getConnection();
            String SQLPrep = "INSERT INTO Film (titre, id_rea) VALUES (?,?);";
            PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, this.titre);
            prep.setInt(2, this.id_rea);
            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
    }
    private void update() throws SQLException, RealisateurAbsentException {
        if (this.id_rea==-1){
            throw new RealisateurAbsentException();
        }else {
            DBConnection dbco = new DBConnection();
            Connection connect = dbco.getConnection();
            String SQLprep = "update Film set titre=?, id_rea=? where id=?;";
            PreparedStatement prep = connect.prepareStatement(SQLprep);
            prep.setString(1, this.titre);
            prep.setInt(2, this.id_rea);
            prep.setInt(3, this.id);
            prep.execute();
        }
    }
    public static void createTable() throws SQLException {
        DBConnection dbco = new DBConnection();
        Connection connect = dbco.getConnection();
        String createString = "CREATE TABLE Film ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "TITRE varchar(40) NOT NULL, " + "ID_REA INTEGER(11) NOT NULL, " + "PRIMARY KEY (ID),"
                + " FOREIGN KEY (ID_REA) REFERENCES Personne(ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
        System.out.println("Creation table Film\n");
    }
    public static void deleteTable() throws SQLException {
        DBConnection dbco = new DBConnection();
        Connection connect = dbco.getConnection();
        String drop = "DROP TABLE Film";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
        System.out.println("Suppression table Film");
    }
    public static Film findById(int idCherche){
        Film f =null;
        try {
            DBConnection bdconnect = new DBConnection();
            Connection co = bdconnect.getConnection();
            String SQLPrep = "SELECT * FROM Film;";
            PreparedStatement prep1 = co.prepareStatement(SQLPrep);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();
            // s'il y a un resultat
            while (rs.next()) {
                int id = rs.getInt("id");
                String titre = rs.getString("titre");
                int id_rea = rs.getInt("id_rea");
                if (id==idCherche){
                    f = new Film(titre, Personne.findById(id_rea));
                    f.id=id;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return f;
    }
    public Personne getRealisateur() {
        Personne p = Personne.findById(this.id_rea);
        return p;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_rea(int id_rea) {
        this.id_rea = id_rea;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public int getId_rea() {
        return id_rea;
    }
}
