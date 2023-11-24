import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonneTest {

    @BeforeEach
    public void initEach() throws SQLException {
        Personne.createTable();
        Personne p1 = new Personne("Spielberg", "Steven");
        p1.setId(1);
        p1.save();
        Personne p2 = new Personne("Scott", "Ridley");
        p2.setId(2);
        p2.save();
        Personne p3 = new Personne("Kubrick", "Stanley");
        p3.setId(3);
        p3.save();
        Personne p4 = new Personne("Fincher", "David");
        p4.setId(4);
        p4.save();
    }
    @AfterEach
    public void cleanUpEach() throws SQLException {
        Personne.deleteTable();
    }

    @Test
    void findAll() {
        Personne p1 = new Personne("Spielberg", "Steven");
        p1.setId(1);
        Personne p2 = new Personne("Scott", "Ridley");
        p2.setId(2);
        Personne p3 = new Personne("Kubrick", "Stanley");
        p3.setId(3);
        Personne p4 = new Personne("Fincher", "David");
        p4.setId(4);
        ArrayList<Personne> listeTest = new ArrayList<>();
        listeTest.add(p1);
        listeTest.add(p2);
        listeTest.add(p3);
        listeTest.add(p4);
        ArrayList<Personne> listePersonne;
        listePersonne = Personne.findAll();
        assertEquals(listeTest,listePersonne);
    }

    @Test
    void findById() {
        Personne p = Personne.findById(1);
        Personne test = new Personne("Spielberg", "Steven");
        test.setId(1);
        assertEquals(test,p);
    }

    @Test
    void findByName() {
        Personne p1 = new Personne("Spielberg", "Steven");
        p1.setId(1);
        ArrayList<Personne> listeTest = new ArrayList<>();
        listeTest.add(p1);
        ArrayList<Personne> listePers = Personne.findByName("Spielberg");
        assertEquals(listeTest, listePers);
    }
}