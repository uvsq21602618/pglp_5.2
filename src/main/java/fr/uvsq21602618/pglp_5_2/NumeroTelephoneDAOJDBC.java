package fr.uvsq21602618.pglp_5_2;

import java.io.IOException;
import java.sql.SQLException;

public class NumeroTelephoneDAOJDBC extends DAOJDBC<NumeroTelephone> {
    public NumeroTelephoneDAOJDBC() throws SQLException {
        super();
    }
    /**
     * Méthode de création.
     * @param obj L'objet à créer
     * @return obj qui vient d'etre cree
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public NumeroTelephone create(final NumeroTelephone obj) {
        return obj;
       
    }
    /**
     * Méthode pour effacer.
     * @param obj L'objet à effacer
     */
    public void delete(final NumeroTelephone obj) {
   
    }
    /**
     * Méthode de mise à jour.
     * @param obj L'objet à mettre à jour
     * @throws IOException Exceptions liees aux entrees/sorties
     * @return obj L'objet à mettre à jour
     */
    public NumeroTelephone update(final NumeroTelephone obj) {
        return obj;
           
    }
    /**
     * Méthode de recherche des informations.
     * @param id de l'information
     * @return  le GroupePersonnel du fichier, null sinon
     * @throws IOException liee aux entreés/sorties
     * @throws ClassNotFoundException Exception lié à une classe inexistante
     */
    public NumeroTelephone find(final int id) {
                return null;
        
    }
}
