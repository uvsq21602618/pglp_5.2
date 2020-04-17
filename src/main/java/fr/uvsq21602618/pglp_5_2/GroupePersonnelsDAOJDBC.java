package fr.uvsq21602618.pglp_5_2;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Classe de GroupePersonnelsDAOJDBC. 
 * @author Nathalie
 *
 */
public class GroupePersonnelsDAOJDBC extends DAOJDBC<GroupePersonnels> {
    /**
     * Le DAO de Personnel.
     */
    public static DAOJDBC<Personnel> persoJDBC;
    /**
     * Constructeur de GroupePersonnelsDAOJDBC.
     * @throws SQLException Exception liee a l'acces a la base de donnees
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public GroupePersonnelsDAOJDBC() throws SQLException, IOException {
        super();
        persoJDBC = DAOFactoryJDBC.getPersonnelDAOJDBC();
    }
    /**
     * Méthode de création.
     * @param obj L'objet à créer
     * @return obj qui vient d'etre cree
     * @throws SQLException Exception liee a l'acces a la base de donnees
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public GroupePersonnels create(final GroupePersonnels obj) throws SQLException, IOException {
        DatabaseMetaData dbmd = connect.getMetaData();
        ResultSet rs = dbmd.getTables(null, null, "groupe_personnels".toUpperCase(), null); 
        Statement creation = null;

        creation = connect.createStatement();

        if (!rs.next()) {
            creation.executeUpdate("Create table groupe_personnels"
                    + " (id int primary key, nom_groupe varchar(30))");
        } try {
            creation.executeUpdate("insert into groupe_personnels values ("
                    + obj.getId() + ",'" + obj.getNomGroupe()
                    + "')");
            rs = creation.executeQuery("SELECT * FROM groupe_personnels");

            System.out.println("---Table groupe_personnels:---\n");
            System.out.println("id\t nom_groupe\t");
            while (rs.next()) { 
                System.out.printf("%d\t%s\n", rs.getInt("id"),
                        rs.getString("nom_groupe"));
            }
            System.out.println("------------------------------------\n");

            rs.close();
            Personnel p;
            GroupePersonnels gp;
            for (Composant comp : obj.getChildren()) {
                if (comp instanceof Personnel) { 
                    p = (Personnel) comp;
                    persoJDBC.create(p);
                    this.appartientPersonnel(obj.getId(), p.getId());
                } else {  
                    gp = (GroupePersonnels) comp;
                    this.create(gp);
                    this.appartientGroupe(obj.getId(), gp.getId());
                } 
            }

            System.out.println("L'objet " + obj.toString() + "a bien été enregistré!\n\n");
        }  catch (org.apache.derby.shared.common.error
                .DerbySQLIntegrityConstraintViolationException e) {
            System.out.println("Cet id a deja était utilisé"
                    + " pour la table GroupePersonnels!\n");
        }
        creation.close();
        return obj;

    }
    /**
     * Méthode pour effacer.
     * @param obj L'objet à effacer
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    public void delete(final GroupePersonnels obj) throws SQLException {
        Statement stmt = connect.createStatement();
        int idGroupe;
        idGroupe = obj.getId();
        String sql;

        sql = "delete from appartenance_personnel where id_groupe=" + idGroupe;
        stmt.executeUpdate(sql);     
        
        sql = "delete from appartenance_sous_groupe where id_groupe=" + idGroupe;
        stmt.executeUpdate(sql);
        
        sql = "delete from appartenance_sous_groupe where id_sousGroupe=" + idGroupe;
        stmt.executeUpdate(sql);

        sql = "delete from groupe_personnels where id=" + idGroupe;
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.printf("Le groupe avec l'id " + obj.getId() 
        + " a bien été supprimé!\n");
    }
    /**
     * Méthode de mise à jour.
     * @param obj L'objet à mettre à jour
     * @throws IOException Exceptions liees aux entrees/sorties
     * @return obj L'objet à mettre à jour
     */
    public GroupePersonnels update(final GroupePersonnels obj) {
        return obj;

    }
    /**
     * Méthode de recherche des informations.
     * @param id de l'information
     * @return gp le GroupePersonnel du fichier, null sinon
     * @throws IOException liee aux entreés/sorties
     * @throws ClassNotFoundException Exception lié à une classe inexistante
     */
    public GroupePersonnels find(final int id) {
        return null;

    }
    /**
     * Methode pour creer la table qui associe le composant de classe Personnel
     * avec le groupe de Personnels auquel il appartient.
     * @param idGroupe identifiant du groupe
     * @param idPerso identifiant du personnel
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    private void appartientPersonnel(final int idGroupe, final int idPerso)
            throws SQLException {

        DatabaseMetaData dbmd = connect.getMetaData();
        ResultSet rs = dbmd.getTables(null, null, "appartenance_personnel".toUpperCase(), null); 
        Statement stmt = null;

        stmt = connect.createStatement();

        if (!rs.next()) {
            stmt.executeUpdate("Create table appartenance_personnel"
                    + " (id_groupe int NOT NULL, id_personnel int NOT NULL, "
                    + "primary key (id_groupe, id_personnel),"
                    + "foreign key (id_groupe) references groupe_personnels(id), "
                    + "foreign key (id_personnel) references personnel(id))");
        }

        try {
            stmt.executeUpdate("insert into appartenance_personnel values ("
                    + idGroupe + "," + idPerso + ")");
            rs = stmt.executeQuery("SELECT * FROM appartenance_personnel");

            System.out.println("---Table appartenance_personnel:---\n");
            System.out.println("id_groupe\t id_personnel");
            while (rs.next()) { 
                System.out.printf("%d\t\t%d\n", rs.getInt("id_groupe"),
                        rs.getInt("id_personnel"));
            }
            System.out.println("------------------------------------\n");
            rs.close(); 
            stmt.close();
        }  catch ( org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException e) {
            System.out.println("Cet id a deja était utilisé pour la "
                    + "table appartenance_personnel!\n");
        }
    }
    /**
     * Methode pour creer la table qui associe le composant de classe Personnel
     * avec le groupe de Personnels auquel il appartient.
     * @param idGroupe identifiant du groupe
     * @param idPerso identifiant du personnel
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    private void appartientGroupe(final int idGroupe, final int idSousGr)
            throws SQLException {

        DatabaseMetaData dbmd = connect.getMetaData();
        ResultSet rs = dbmd.getTables(null, null,
                "appartenance_sous_groupe".toUpperCase(), null); 
        Statement stmt = null;

        stmt = connect.createStatement();

        if (!rs.next()) {
            stmt.executeUpdate("Create table appartenance_sous_groupe"
                    + " (id_groupe int NOT NULL, id_sousGroupe int NOT NULL, "
                    + "primary key (id_groupe, id_sousGroupe),"
                    + "foreign key (id_groupe) references groupe_personnels(id), "
                    + "foreign key (id_sousGroupe) references groupe_personnels(id))");
        }

        try {
            stmt.executeUpdate("insert into appartenance_sous_groupe values ("
                    + idGroupe + "," + idSousGr + ")");
            rs = stmt.executeQuery("SELECT * FROM appartenance_sous_groupe");

            System.out.println("---Table appartenance_sous_groupe:---\n");
            System.out.println("id_groupe\t id_sousGroupe");
            while (rs.next()) { 
                System.out.printf("%d\t\t%d\n", rs.getInt("id_groupe"),
                        rs.getInt("id_sousGroupe"));
            }
            System.out.println("------------------------------------\n");
            rs.close(); 
            stmt.close();
        }  catch ( org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException e) {
            System.out.println("Cet id a deja était utilisé pour la "
                    + "table appartenance_sous_groupe!\n");
        }
    }
    /**
     * Methode pour afficher le contenu de la table groupe_personnels.
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    public void affichage_table_GroupePersonnels() throws SQLException {
        DatabaseMetaData dbmd = connect.getMetaData();
        ResultSet rs = dbmd.getTables(null, null, "groupe_personnels".toUpperCase(), null);
        Statement stmt = connect.createStatement();
        rs = stmt.executeQuery("SELECT * FROM groupe_personnels");

        System.out.println("---Table groupe_personnels:---\n");
        System.out.println("id\t nom_groupe\t");
        while (rs.next()) { 
            System.out.printf("%d\t%s\n", rs.getInt("id"),
                    rs.getString("nom_groupe"));
        }
        System.out.println("------------------------------------\n");

        rs.close();
    }
}
