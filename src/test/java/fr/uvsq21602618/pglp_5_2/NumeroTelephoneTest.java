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
 * Classe de tests pour la classe NumeroTelephone.
 * @author Nathalie
 *
 */
public class NumeroTelephoneTest {
    /**
     * Instances de NumeroTelephone.
     */
    NumeroTelephone tel;
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
     * Initialisation des instances pour les tests.
     * @throws IOException Exception liee aux entreés/sorties
     */
    @Before
    public void setUp() throws IOException {
        nomDir = "NumeroTels";
        dir = new File(nomDir);
        
        tel = new NumeroTelephone("fixe", "0167874973", 2);
        numTel = DAOFactory.getNumeroTelephoneDAO();
    }
    /**
     * Teste le méthode getDescriptif.
     */
    @Test
    public void getDescriptifTest() {
        String expected = "fixe";
        assertEquals(expected, tel.getDescriptif());
    }
    /**
     * Teste la méthode getNumeroTest.
     */
    @Test
    public void getNumeroTest() {
        String expected = "0167874973";
        assertEquals(expected, tel.getNumero());
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
     * @throws IOException Exception liee aux entrees/sorties
     * @throws ClassNotFoundException Exception si la classe n'existe pas
     */
    @Test
    public void ConsistencyTest() throws IOException, ClassNotFoundException {
        byte[] serialized1 = serialize(tel);
        byte[] serialized2 = serialize(tel);

        Object deserialized1 = deserialize(serialized1);
        Object deserialized2 = deserialize(serialized2);
        Assert.assertEquals(deserialized1, deserialized2);
        Assert.assertEquals(tel, deserialized1);
        Assert.assertEquals(tel, deserialized2);
    }
    /**
     * Test pour verifier si la methode create de NumeroTelephoneDAO fonctionne.
     * @throws IOException Exception liee aux entrees/sorties
     * @throws ClassNotFoundException Exception si la classe n'existe pas
     */
    @Test
    public void createTest() throws IOException, ClassNotFoundException {       
        numTel.create(tel);
        
        File search = new File(nomDir + "\\" + tel.getId() + ".txt");
        Object deserialized = null;
        
        byte[] fileContent = Files.readAllBytes(search.toPath());
       
        deserialized = deserialize(fileContent);
        NumeroTelephone expected = (NumeroTelephone) deserialized;
        
        assertTrue(dir.exists());
        assertTrue(search.exists());
        assertEquals(expected, tel);
        
        numTel.delete(tel);
        dir.delete();
    }
    /**
     * Test pour verifier si la methode delete de NumeroTelephoneDAO fonctionne.
     * @throws IOException Exception liee aux entrees/sorties
     * @throws ClassNotFoundException Exception si la classe n'existe pas
     */
    @Test
    public void deleteTest() throws IOException, ClassNotFoundException {      
        NumeroTelephone tel2 = new NumeroTelephone("fixe", "0167874963", 3);
        File search = new File(nomDir + "\\" + tel.getId() + ".txt");
        File expected = new File(nomDir + "\\" + tel2.getId() + ".txt");  

        numTel.create(tel);
        numTel.create(tel2);
        numTel.delete(tel);
        
        assertTrue(!search.exists());
        assertTrue(expected.exists());
        numTel.delete(tel2);
    }
    /**
     * Test pour verifier si la methode update de NumeroTelephoneDAO fonctionne.
     * @throws IOException Exception liee aux entrees/sorties
     * @throws ClassNotFoundException Exception si la classe n'existe pas
     */
    @Test
    public void updateTest() throws IOException, ClassNotFoundException {      
        File search = new File(nomDir + "\\" + tel.getId() + ".txt");
        NumeroTelephone tel2 = new NumeroTelephone("newDescriptif", "newNumero", 2);

        numTel.create(tel);
        numTel.update(tel);
        Object deserialized = null;
        
        byte[] fileContent = Files.readAllBytes(search.toPath());
       
        deserialized = deserialize(fileContent);
        NumeroTelephone expected = (NumeroTelephone) deserialized;
        
        assertTrue(search.exists());
        assertEquals(expected, tel2);
        numTel.delete(tel);
    }  
    /**
     * Test pour verifier si la methode find de NumeroTelephoneDAO fonctionne.
     * @throws IOException Exception liee aux entrees/sorties
     * @throws ClassNotFoundException Exception si la classe n'existe pas
     */
    @Test
    public void findTest() throws IOException, ClassNotFoundException {      
        File search = new File(nomDir + "\\" + tel.getId() + ".txt");
        NumeroTelephone expected;
        numTel.create(tel);
        
        expected = numTel.find(2);
        
        assertTrue(search.exists());
        assertEquals(expected, tel);
        numTel.delete(tel);
    }
}
