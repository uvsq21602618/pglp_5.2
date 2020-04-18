package fr.uvsq21602618.pglp_5_2;

import java.io.IOException;
/**
 * Classe de la fabrique DAO.
 * @author Nathalie
 */
public final class DAOFactory extends AbstractDAOFactory {
    /**
     * Constructeur.
     */
    public DAOFactory() {
      }
    /**
     * Méthode pour récuperer le DAO de NumeroTelephone.
     * @return le DAO correspondant
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public DAO<NumeroTelephone> getNumeroTelephoneDAO()
            throws IOException {
        return new NumeroTelephoneDAO();
    }
    /**
     * Méthode pour récuperer le DAO de Personnel.
     * @return le DAO correspondant
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public DAO<Personnel> getPersonnelDAO() throws IOException {
        return new PersonnelDAO();
    }
    /**
     * Méthode pour récuperer le DAO de GroupePersonnels.
     * @return le DAO correspondant
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public DAO<GroupePersonnels> getGroupePersonnelsDAO()
            throws IOException {
        return new GroupePersonnelsDAO();
    }
}

