package fr.uvsq21602618.pglp_5_2;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NumeroTelephoneDAOJDBC extends DAOJDBC<NumeroTelephone> {
    /**
     * Constructeur de NumeroTelephoneDAOJDBC.
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    public NumeroTelephoneDAOJDBC() throws SQLException {
        super();
    }
    /**
     * Méthode de création.
     * @param obj L'objet à créer
     * @return obj qui vient d'etre cree
     * @throws SQLException Exception liee a l'acces a la base de donnees
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
            System.out.println("Cet id a deja était utilisé pour cette table!\n");
        }
        rs = creation.executeQuery("SELECT * FROM numero_telephone");
        
        while (rs.next()) { 
            System.out.printf("%d\t%s\t%s\n", rs.getInt("id"),
                    rs.getString("descriptif"), rs.getString("numero"));
          }
        System.out.println("L'objet " + obj.toString() + " a bien été enregistré!\n");
        creation.close();
        return obj;
       
    }
    /**
     * Méthode pour effacer.
     * @param obj L'objet à effacer
     * @throws SQLException  Exception liee a l'acces a la base de donnees
     */
    public void delete(final NumeroTelephone obj) throws SQLException {
        String sql = "delete from numero_telephone where id=" + obj.getId();
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.printf("Le numero de telephone avec l'id " + obj.getId() 
            + " a bien été supprimé!\n");
    }
    /**
     * Méthode de mise à jour.
     * On met a jour l'objet base sur son id.
     * @param obj L'objet à mettre à jour
     * @return obj L'objet à mettre à jour
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    public NumeroTelephone update(final NumeroTelephone obj) throws SQLException {
        Statement stmt = connect.createStatement();
        ResultSet result = null;           
        result = stmt.executeQuery("select descriptif,"
                + " numero from numero_telephone where id="
                + obj.getId());
        if (!result.next()){
            System.out.println("Cet identifiant n'a pas encore été utilisé,"
                    + "il n'y a donc pas de mise a jour possible."); 
            this.create(obj);
        }
       else{
           this.delete(obj);
           this.create(obj);
           System.out.println("La mise à jour du numero d'id " + obj.getId() 
                + " a été effectué!\n");
          }   
        stmt.close();
        return obj;
    }
    /**
     * Méthode de recherche des informations.
     * @param id de l'information
     * @return  une instance de NumeroTelephone qu'on a cherchee, null sinon
     * @throws SQLException Exception liee a l'acces a la base de donnees
     * @throws ClassNotFoundException Exception lié à une classe inexistante
     */
    public NumeroTelephone find(final int id) throws SQLException {
        NumeroTelephone search;
        Statement stmt = connect.createStatement();
        ResultSet rs = null;           
        rs = stmt.executeQuery("select descriptif,"
                + " numero from numero_telephone"
                + " where id=" + id);
        if (rs.next() == false) {
            System.out.println("Il n'y a pas de numero correspondant a l'id"
                    + id + "!\n");
          }
        String desc = rs.getString("descriptif");
        String num = rs.getString("numero");
        search = new NumeroTelephone(desc, num, id);
        System.out.println(search.toString()+ "\n");
       
        stmt.close();
        return search;
        
    }
}
