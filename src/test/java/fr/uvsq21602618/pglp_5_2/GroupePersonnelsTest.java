package fr.uvsq21602618.pglp_5_2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * Classe contenant les tests de la classe GroupePersonnels.
 * @author Nathalie
 *
 */
public class GroupePersonnelsTest {
    /**
     * Instances de GroupePersonnels.
     */
     GroupePersonnels g, g2, g3;
    /**
     * Instance du dossier contenant les fichiers objets.
     */
    File dir;
    /**
     * Le DAO des NumeroTelephoneDAO.
     */
    DAO<NumeroTelephone> numTel;
    /**
     * Le nom du dossier contenant les fichiers 
     * liés aux numeroTelephone.
     */
    String nomDir;
    /**
     * DAO de GroupePersonnels.
     */
    DAO<GroupePersonnels> groupePersoDAO;
    /**
     * Initialisation des instances pour les tests.
     * @throws IOException Exception liee aux entreés/sorties
     */
    @Before
    public void setUp() throws IOException {
        nomDir = "Groupes";
        dir = new File(nomDir);
        
        g = new GroupePersonnels("groupe perso", 1);
        g2 = new GroupePersonnels("groupe perso2", 2);
        g3 = new GroupePersonnels("groupe perso3", 3);
        
        groupePersoDAO = DAOFactory.getGroupePersonnelsDAO();
    }
    /**
     * Test de la méthode add.
     */
    @Test
    public void addTest() {

        g.add(g2);
        assertEquals(g.getChildren().get(0), g2);
    }
    /**
     * Test de la méthode remove.
     */
    @Test
    public void removeTest() {
        g.add(g2);
        g.add(g3);
        g.remove(g2);
        assertEquals(g.getChildren().get(0), g3);
    }
    /**
     * Méthode de désérialisation.
     * @param bytes le tableau d'octets à transformer en objet.
     * @return L'objet obtenu.
     * @throws ClassNotFoundException Exception si la classe n'existe pas.
     * @throws IOException Exception liee aux entreés/sorties
     */
    private Object deserialize(final byte[] bytes) throws ClassNotFoundException, IOException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }
    /**
     * Méthode de sérialisation.
     * @param obj L'objet à transformer en flux d'octets.
     * @return flux d'octets de l'objet.
     * @throws IOException Exception liee aux entreés/sorties
     */
    private byte[] serialize(final Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    /**
     * Tests sur la transformation dans les deux sens.
     * @throws IOException Exception liee aux entreés/sorties
     * @throws ClassNotFoundException Exception si la classe n'existe pas
     */
    @Test
    public void ConsistencyTest() throws IOException, ClassNotFoundException {
        byte[] serialized1 = serialize(g);
        byte[] serialized2 = serialize(g);

        Object deserialized1 = deserialize(serialized1);
        Object deserialized2 = deserialize(serialized2);
        Assert.assertEquals(deserialized1, deserialized2);
        Assert.assertEquals(g, deserialized1);
        Assert.assertEquals(g, deserialized2);
    }
    /**
     * Test pour verifier si la methode create de GroupePersonnelsDAO fonctionne.
     * @throws IOException Exception liee aux entrees/sorties
     * @throws ClassNotFoundException Exception si la classe n'existe pas
     */
    @Test
    public void createTest() throws IOException, ClassNotFoundException {       
        groupePersoDAO.create(g);
        File search = new File(nomDir + "\\" + g.getId() + ".txt");
        Object deserialized = null;
        
        byte[] fileContent = Files.readAllBytes(search.toPath());
       
        deserialized = deserialize(fileContent);
        GroupePersonnels expected = (GroupePersonnels) deserialized;
        
        assertTrue(dir.exists());
        assertTrue(search.exists());
        assertEquals(expected, g);
        
        groupePersoDAO.delete(g);
        dir.delete();
    }
    /**
     * Test pour verifier si la methode delete de GroupePersonnelsDAO fonctionne.
     * @throws IOException Exception liee aux entrees/sorties
     * @throws ClassNotFoundException Exception si la classe n'existe pas
     */
    @Test
    public void deleteTest() throws IOException, ClassNotFoundException {      
        File search = new File(nomDir + "\\" + g.getId() + ".txt");
        File expected = new File(nomDir + "\\" + g2.getId() + ".txt");  

        groupePersoDAO.create(g);
        groupePersoDAO.create(g2);
        groupePersoDAO.delete(g);
        
        assertTrue(!search.exists());
        assertTrue(expected.exists());
        groupePersoDAO.delete(g2);
    }
    /**
     * Test pour verifier si la methode update de GroupePersonnelsDAO fonctionne.
     * @throws IOException Exception liee aux entrees/sorties
     * @throws ClassNotFoundException Exception si la classe n'existe pas
     */
    @Test
    public void updateTest() throws IOException, ClassNotFoundException {      
        File search = new File(nomDir + "\\" + g.getId() + ".txt");

        groupePersoDAO.create(g);
        groupePersoDAO.update(g);
        Object deserialized = null;
        
        byte[] fileContent = Files.readAllBytes(search.toPath());
       
        deserialized = deserialize(fileContent);
        GroupePersonnels expected = (GroupePersonnels) deserialized;
        
        assertTrue(search.exists());
        assertEquals(expected, g);
        groupePersoDAO.delete(g);
    }  
    /**
     * Test pour verifier si la methode find de GroupePersonnelsDAO fonctionne.
     * @throws IOException Exception liee aux entrees/sorties
     * @throws ClassNotFoundException Exception si la classe n'existe pas
     */
    @Test
    public void findTest() throws IOException, ClassNotFoundException {      
        File search = new File(nomDir + "\\" + g.getId() + ".txt");
        GroupePersonnels expected;
        groupePersoDAO.create(g);
        
        expected = groupePersoDAO.find(1);
        
        assertTrue(search.exists());
        assertEquals(expected, g);
        groupePersoDAO.delete(g);
    }
}
