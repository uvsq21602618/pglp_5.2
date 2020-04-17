package fr.uvsq21602618.pglp_5_2;

import java.io.IOException;
import java.sql.SQLException;

public class DAOFactoryJDBC {
    /**
     * Constructeur.
     */
    private DAOFactoryJDBC() {
        throw new IllegalStateException("Utility class");
      }
    /**
     * Méthode pour récuperer le DAO de NumeroTelephone.
     * @return le DAO correspondant
     * @throws IOException Exceptions liees aux entrees/sorties
     * @throws SQLException 
     */
    public static DAOJDBC<NumeroTelephone> getNumeroTelephoneDAOJDBC()
            throws IOException, SQLException {
        return new NumeroTelephoneDAOJDBC();
    }
    /**
     * Méthode pour récuperer le DAO de Personnel.
     * @return le DAO correspondant
     * @throws IOException Exceptions liees aux entrees/sorties
     * @throws SQLException 
     */
    public static DAOJDBC<Personnel> getPersonnelDAOJDBC() throws IOException, SQLException {
        return new PersonnelDAOJDBC();
    }
    /**
     * Méthode pour récuperer le DAO de GroupePersonnels.
     * @return le DAO correspondant
     * @throws IOException Exceptions liees aux entrees/sorties
     * @throws SQLException 
     */
    public static DAOJDBC<GroupePersonnels> getGroupePersonnelsDAOJDBC()
            throws IOException, SQLException {
        return new GroupePersonnelsDAOJDBC();
    }
}
