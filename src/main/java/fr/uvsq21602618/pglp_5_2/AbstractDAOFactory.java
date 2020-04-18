package fr.uvsq21602618.pglp_5_2;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Une fabrique abstraite pour DAO.
 * @author Nathalie
 *
 */
public abstract class AbstractDAOFactory {
    /**
     * Enumeration du type de DAO possible.
     * @author Nathalie
     *
     */
    public enum DAOType {FILE, JDBC;}
    /**
     * Méthode pour recuperer le DAO de NumeroTelephone.
     * @return le DAO de NumeroTelephone.
     * @throws SQLException 
     * @throws IOException 
     */
    public abstract DAO<NumeroTelephone> getNumeroTelephoneDAO() throws IOException, SQLException;
    /**
     * Méthode pour recuperer le DAO de PersonnelDAO.
     * @return le DAO de Personnel.
     * @throws SQLException 
     * @throws IOException 
     */
    public abstract DAO<Personnel> getPersonnelDAO() throws IOException, SQLException;
    /**
     * Méthode pour recuperer le DAO de GroupePersonnels.
     * @return le DAO de GroupePersonnels.
     * @throws SQLException 
     * @throws IOException 
     */
    public abstract DAO<GroupePersonnels> getGroupePersonnelsDAO() throws IOException, SQLException;
    /**
     * Constructeur de la fabrique de DAO abstraite.
     * @param type le type de DAO qu'on utilise
     * @return la fabrique de DAO adapte au type precise, si il
     * n'existe pas renvoie null
     */
    public static AbstractDAOFactory getFactory(DAOType type) {
        if (type == DAOType.FILE) return new DAOFactory();
        if (type == DAOType.JDBC) return new DAOFactoryJDBC();
        return null;
    }
}
