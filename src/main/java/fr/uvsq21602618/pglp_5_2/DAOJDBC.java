package fr.uvsq21602618.pglp_5_2;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Classe abstraite de DataAccessObject.
 * @author Nathalie
 *
 * @param <T> La classe choisie
 */
public abstract class DAOJDBC<T> {
    
    private String dbUrl;
    protected Connection connect;
    
    public DAOJDBC() throws SQLException {
        dbUrl = "jdbc:derby:donneesPourDB\\jdbcDB;create=true";
        
        try {
            connect = DriverManager.getConnection(dbUrl);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Méthode de création.
     * @param obj L'objet à créer
     * @return T une classe donnée
     * @throws IOException Exceptions liées aux entrées/sorties
     * @throws SQLException 
     */
    public abstract T create(T obj) throws IOException, SQLException;
    /**
     * Méthode pour effacer.
     * @param obj l'objet à supprimer
     * @throws SQLException 
     */
    public abstract void delete(T obj) throws SQLException;
    /**
     * Méthode de mise à jour.
     * @param obj L'objet à mettre à jour
     * @throws IOException Exception liee aux entrees/sorties
     * @return T une instance de la classe donnée
     * @throws SQLException 
     */
    public abstract T update(T obj) throws IOException, SQLException;
    /**
     * Méthode de recherche des informations.
     * @param id de l'information
     * @return T une classe donnée
     * @throws FileNotFoundException Exception si le fichier n'existe pas
     * @throws IOException Exception liee aux entrees/sorties
     * @throws ClassNotFoundException Exception si la classe n'existe pas
     * @throws SQLException 
     */
    public abstract T find(int id) throws FileNotFoundException,
    ClassNotFoundException, IOException, SQLException;
    /**
     * Méthode de désérialisation.
     * @param bytes le tableau d'octets à transformer en objet.
     * @return L'objet obtenu.
     * @throws ClassNotFoundException Exception si la
     * classe n'existe pas
     * @throws IOException Exception liee aux entrees/sorties
     */
    public Object deserialize(final byte[] bytes) throws ClassNotFoundException,
    IOException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }
}
