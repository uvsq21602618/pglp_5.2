package fr.uvsq21602618.pglp_5_2;

import java.io.IOException;
import java.sql.SQLException;

public class PersonnelDAOJDBC extends DAOJDBC<Personnel> {

    public PersonnelDAOJDBC() throws SQLException {
        super();
    }
    /**
     * Méthode de création.
     * @param obj L'objet à créer
     * @return obj qui vient d'etre cree
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public Personnel create(final Personnel obj) {
        return obj;
       
    }
    /**
     * Méthode pour effacer.
     * @param obj L'objet à effacer
     */
    public void delete(final Personnel obj) {
   
    }
    /**
     * Méthode de mise à jour.
     * @param obj L'objet à mettre à jour
     * @throws IOException Exceptions liees aux entrees/sorties
     * @return obj L'objet à mettre à jour
     */
    public Personnel update(final Personnel obj) {
        return obj;
           
    }
    /**
     * Méthode de recherche des informations.
     * @param id de l'information
     * @return  le GroupePersonnel du fichier, null sinon
     * @throws IOException liee aux entreés/sorties
     * @throws ClassNotFoundException Exception lié à une classe inexistante
     */
    public Personnel find(final int id) {
                return null;
        
    }
}
