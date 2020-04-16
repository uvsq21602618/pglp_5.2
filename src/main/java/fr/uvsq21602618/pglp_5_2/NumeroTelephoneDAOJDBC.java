package fr.uvsq21602618.pglp_5_2;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NumeroTelephoneDAOJDBC extends DAOJDBC<NumeroTelephone> {
    public NumeroTelephoneDAOJDBC() throws SQLException {
        super();
    }
    /**
     * Méthode de création.
     * @param obj L'objet à créer
     * @return obj qui vient d'etre cree
     * @throws SQLException 
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public NumeroTelephone create(final NumeroTelephone obj) throws SQLException {
        
        DatabaseMetaData dbmd = connect.getMetaData();
        ResultSet rs = dbmd.getTables(null, null, "numero_telephone".toUpperCase(), null); 
        Statement creation = null;
        
        creation = connect.createStatement();
 
        if (!rs.next()) {
            creation.executeUpdate("Create table numero_telephone"
                    + " (id int primary key, descriptif varchar(30),"
                    + " numero varchar(30))");
        }
            
        try {
        creation.executeUpdate("insert into numero_telephone values ("
                + obj.getId() + ",'" + obj.getDescriptif() +"', '" + obj.getNumero() +"')");
        }  catch ( org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException e) {
            System.out.println("Cet id a deja était utilisé pour cette table!");
        }
        rs = creation.executeQuery("SELECT * FROM numero_telephone");
        
        while (rs.next()) { 
            System.out.printf("%d\t%s\t%s\n", rs.getInt("id"), rs.getString("descriptif"), rs.getString("numero"));
          }
        creation.close();
        return obj;
       
    }
    /**
     * Méthode pour effacer.
     * @param obj L'objet à effacer
     * @throws SQLException 
     */
    public void delete(final NumeroTelephone obj) throws SQLException {
        String sql = "delete from numero_telephone where id=" + obj.getId();
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(sql);
        System.out.printf("Le numero de telephone avec l'id " + obj.getId() + " a bien été supprimé!");
    }
    /**
     * Méthode de mise à jour.
     * @param obj L'objet à mettre à jour
     * @throws IOException Exceptions liees aux entrees/sorties
     * @return obj L'objet à mettre à jour
     * @throws SQLException 
     */
    public NumeroTelephone update(final NumeroTelephone obj) throws SQLException {
        Statement stmt = connect.createStatement();
        
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
