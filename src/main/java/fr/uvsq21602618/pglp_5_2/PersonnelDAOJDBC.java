package fr.uvsq21602618.pglp_5_2;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Classe de PersonnelDAOJDBC.
 * @author Nathalie
 *
 */
public class PersonnelDAOJDBC extends DAOJDBC<Personnel> {
    /**
     * Le DAO de numeroTelephone.
     */
    public static DAOJDBC<NumeroTelephone> numTelJDBC;
    /**
     * Constructeur de PersonnelDAOJDBC.
     * @throws SQLException Exception liee a l'acces a la base de donnees
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public PersonnelDAOJDBC() throws SQLException, IOException {
        super();
        numTelJDBC = DAOFactoryJDBC.getNumeroTelephoneDAOJDBC();
    }
    /**
     * Méthode de création.
     * @param obj L'objet à créer
     * @return obj qui vient d'etre cree
     * @throws SQLException 
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public Personnel create(final Personnel obj) throws SQLException,
    IOException {
        DatabaseMetaData dbmd = connect.getMetaData();
        ResultSet rs = dbmd.getTables(null, null, "personnel".toUpperCase(), null); 
        Statement creation = null;
        
        creation = connect.createStatement();
 
        if (!rs.next()) {
            creation.executeUpdate("Create table personnel"
                    + " (id int primary key, nom varchar(30),"
                    + " prenom varchar(30), fonction varchar(30),"
                    + "date_de_naissance varchar(30))");
        }
          
        try {
        creation.executeUpdate("insert into personnel values ("
                + obj.getId() + ",'" + obj.getNom() +"', '" 
                + obj.getPrenom() +"','" + obj.getFonction() +"', '" 
                + obj.getDateNaissance().toString()
                + "')");
        rs = creation.executeQuery("SELECT * FROM personnel");
        
        System.out.println("---Table Personnel:---\n");
        System.out.println("id\t nom\t prenom\t fonction\t date_de_naissance");
        while (rs.next()) { 
            System.out.printf("%d\t%s\t%s\t%s\t%s\n", rs.getInt("id"),
                    rs.getString("nom"), rs.getString("prenom"),
                    rs.getString("fonction"), rs.getString("date_de_naissance"));
          }
        System.out.println("------------------------------------\n");
        
        rs.close();
        for (NumeroTelephone num : obj.getNumTelephones()) {
            numTelJDBC.create(num);
            this.correspondance(obj.getId(), num.getId());
        }
        
        System.out.println("L'objet " + obj.toString() + "a bien été enregistré!\n\n");
        }  catch ( org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException e) {
            System.out.println("Cet id a deja était utilisé pour la table personnel!\n");
        }
        creation.close();
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
    
    /**
     * Methode pour creer la table qui associe le numero a un membre du 
     * personnel.
     * @param idPerso identifiant du personnel
     * @param idNum identifiant d'un des numeros de telephone
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    private void correspondance(final int idPerso, final int idNum) throws SQLException {
        DatabaseMetaData dbmd = connect.getMetaData();
        ResultSet rs = dbmd.getTables(null, null, "correspondance".toUpperCase(), null); 
        Statement stmt = null;
        
        stmt = connect.createStatement();
 
        if (!rs.next()) {
            stmt.executeUpdate("Create table correspondance"
                    + " (id_personnel int NOT NULL, id_numero int NOT NULL, "
                    + "primary key (id_personnel, id_numero),"
                    + "foreign key (id_personnel) references personnel(id),"
                    + "foreign key (id_numero) references numero_telephone(id))");
        }
            
        try {
        stmt.executeUpdate("insert into correspondance values ("
                + idPerso + "," + idNum + ")");
        rs = stmt.executeQuery("SELECT * FROM correspondance");
        
        System.out.println("---Table correspondance:---\n");
        System.out.println("id_personnel\t id_numero");
        while (rs.next()) { 
            System.out.printf("%d\t\t%d\n", rs.getInt("id_personnel"),
                    rs.getInt("id_numero"));
          }
        System.out.println("------------------------------------\n");
        rs.close(); 
        stmt.close();
        }  catch ( org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException e) {
            System.out.println("Cet id a deja était utilisé pour la table personnel!\n");
        }
    }
}
