package fr.uvsq21602618.pglp_5_2;

import java.io.IOException;
import java.sql.SQLException;
/**
 * La fabrique de DAO pour JDBC.
 * @author Nathalie
 *
 */
public class DAOFactoryJDBC extends AbstractDAOFactory {
    /**
     * Constructeur.
     */
    public DAOFactoryJDBC() {
    }
    /**
     * Méthode pour récuperer le DAO de NumeroTelephone.
     * @return le DAO correspondant
     * @throws IOException Exceptions liees aux entrees/sorties
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    public DAOJDBC<NumeroTelephone> getNumeroTelephoneDAO()
            throws IOException, SQLException {
        return new NumeroTelephoneDAOJDBC();
    }
    /**
     * Méthode pour récuperer le DAO de Personnel.
     * @return le DAO correspondant
     * @throws IOException Exceptions liees aux entrees/sorties
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    public DAOJDBC<Personnel> getPersonnelDAO()
            throws IOException, SQLException {
        return new PersonnelDAOJDBC();
    }
    /**
     * Méthode pour récuperer le DAO de GroupePersonnels.
     * @return le DAO correspondant
     * @throws IOException Exceptions liees aux entrees/sorties
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    public DAOJDBC<GroupePersonnels> getGroupePersonnelsDAO()
            throws IOException, SQLException {
        return new GroupePersonnelsDAOJDBC();
    }
}
