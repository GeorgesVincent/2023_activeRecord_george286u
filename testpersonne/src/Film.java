import java.sql.*;
public class Film {
    private int id;
    private String titre;
    private int id_rea;

    public Film(String titre, int id_rea) {
        this.titre = titre;
        this.id_rea = id_rea;
    }
    public void delete() throws SQLException {
        DBConnection dbco = new DBConnection();
        Connection connect = dbco.getConnection();
        PreparedStatement stmt = connect.prepareStatement("DELETE FROM Film WHERE ID = ? ");
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
    private void update() throws SQLException {
        DBConnection dbco = new DBConnection();
        Connection connect = dbco.getConnection();
        String SQLprep = "update Film set titre=?, id_rea=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_rea);
        prep.setInt(3, this.id);
        prep.execute();
    }
    public static void deleteTable() throws SQLException {
        DBConnection dbco = new DBConnection();
        Connection connect = dbco.getConnection();
        String drop = "DROP TABLE Film";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
        System.out.println("Suppression table Film");
    }
    public Film findById(int idCherche){
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
                    f = new Film(titre,id_rea);
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
}
