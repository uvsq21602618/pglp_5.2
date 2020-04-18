package fr.uvsq21602618.pglp_5_2;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Classe de NumeroTelephoneDAOJDBC.
 * @author Nathalie
 *
 */
public class NumeroTelephoneDAOJDBC extends DAOJDBC<NumeroTelephone> {
    /**
     * initialisation de la constante 3 pour eviter le "magic number".
     */
    static final int TROIS = 3;
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
    public NumeroTelephone create(final NumeroTelephone obj)
            throws SQLException {
        DatabaseMetaData dbmd = getConnect().getMetaData();
        ResultSet rs = dbmd.getTables(null, null,
                "numero_telephone".toUpperCase(), null);

        try (Statement creation = getConnect().createStatement()) {
            if (!rs.next()) {
                creation.executeUpdate("Create table numero_telephone"
                        + " (id int primary key, descriptif varchar(30),"
                        + " numero varchar(30))");
            }
            try {
                String updateString = ("insert into numero_telephone"
                        + " values (?, ?, ?)");
                PreparedStatement update =
                        getConnect().prepareStatement(updateString);

                update.setInt(1, obj.getId());
                update.setString(2, obj.getDescriptif());
                update.setString(TROIS, obj.getNumero());

                update.executeUpdate();
                update.close();

                rs = creation.executeQuery("SELECT * FROM numero_telephone");

                System.out.println("---Table numero_telephone:---\n");
                System.out.println("id\t descriptif\t numero");
                while (rs.next()) {
                    System.out.printf("%d\t%s\t%s%n", rs.getInt("id"),
                            rs.getString("descriptif"), rs.getString("numero"));
                }
                System.out.println("------------------------------------\n");
                System.out.println("L'objet " + obj.toString()
                + " a bien été enregistré!\n");
                rs.close();
            }  catch (org.apache.derby.shared.common.error
                    .DerbySQLIntegrityConstraintViolationException e) {
                System.out.println("Cet id a deja était utilisé pour"
                        + " la table numero_de_telephone!\n");
            }

            creation.close();
            return obj;
        }
    }
    /**
     * Méthode pour effacer.
     * @param obj L'objet à effacer
     * @throws SQLException  Exception liee a l'acces a la base de donnees
     */
    public void delete(final NumeroTelephone obj) throws SQLException {
        try (Statement stmt = getConnect().createStatement()) {
            try (ResultSet  rs = stmt.executeQuery("select id"
                    + " from numero_telephone where id="
                    + obj.getId())) {

                if (!rs.next()) {
                    System.out.println("Cet identifiant pour numero n'a pas"
                            + " encore été utilisé,"
                            + "il n'y a donc pas de suppression possible.");
                } else {
                    String sql = "delete from correspondance where id_numero="
                            + obj.getId();
                    stmt.executeUpdate(sql);
                    sql = "delete from numero_telephone"
                            + " where id=" + obj.getId();
                    stmt.executeUpdate(sql);
                    System.out.printf("Le numero de telephone avec l'id "
                            + obj.getId()
                            + " a bien été supprimé!\n");
                }
                stmt.close();
            }
        }
    }
    /**
     * Méthode de mise à jour.
     * On met a jour l'objet base sur son id.
     * @param obj L'objet à mettre à jour
     * @return obj L'objet à mettre à jour
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    public NumeroTelephone update(final NumeroTelephone obj)
            throws SQLException {
        try (Statement stmt = getConnect().createStatement()) {
            try (ResultSet result = stmt.executeQuery("select descriptif,"
                    + " numero from numero_telephone where id="
                    + obj.getId())) {
                if (!result.next()) {
                    System.out.println("Cet identifiant pour numero"
                            + " n'a pas encore été utilisé,"
                            + "il n'y a donc pas de mise a jour possible.");
                    this.create(obj);
                } else {
                    this.delete(obj);
                    this.create(obj);
                    System.out.println("La mise à jour du numero d'id "
                            + obj.getId()
                            + " dans la table numero_de_telephone"
                            + " a été effectué!\n");
                }
                stmt.close();
                return obj;
            }
        }
    }
    /**
     * Méthode de recherche des informations.
     * @param id de l'information
     * @return  une instance de NumeroTelephone qu'on a cherchee, null sinon
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    public NumeroTelephone find(final int id) throws SQLException {
        NumeroTelephone search = null;
        DatabaseMetaData dbmd = getConnect().getMetaData();
        try (Statement exist = getConnect().createStatement()) {
            ResultSet rsEx = dbmd.getTables(null, null,
                    "numero_telephone".toUpperCase(),
                    null);
            if (rsEx.next()) {
                try (Statement stmt = getConnect().createStatement()) {
                    try (ResultSet rs = stmt.executeQuery("select descriptif,"
                            + " numero from numero_telephone"
                            + " where id=" + id)) {
                        if (!rs.next()) {
                            System.out.println("Il n'y a pas de"
                                    + " numero correspondant"
                                    + " a l'id" + id + " dans la"
                                    + " table numero_de_telephone!\n");
                            return null;
                        }
                        String desc = rs.getString("descriptif");
                        String num = rs.getString("numero");
                        search = new NumeroTelephone(desc, num, id);
                        System.out.println("Le numero suivant a ete trouve"
                                + " avec l'identifiant " + id + ":");
                        System.out.println(search.toString() + "\n");
                        rs.close();
                        stmt.close();
                    }
                }
            } else {
                System.out.println("Il n'y a pas encore de numeros"
                        + " dans la base de données!\n");
            }
        }
        return search;
    }

    /**
     * Methode pour afficher le contenu de la table numero_telephone.
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    public void affichageTableNumero() throws SQLException {
        DatabaseMetaData dbmd = getConnect().getMetaData();
        try (Statement exist = getConnect().createStatement()) {
            ResultSet rsEx = dbmd.getTables(null, null,
                    "numero_telephone".toUpperCase(),
                    null);
            if (rsEx.next()) {
                try (Statement stmt = getConnect().createStatement()) {
                    try (ResultSet rs = stmt.executeQuery("SELECT *"
                            + " FROM numero_telephone")) {
                        System.out.println("---Table numero_telephone:---\n");
                        System.out.println("id\t descriptif\t numero\t");
                        while (rs.next()) {
                            System.out.printf("%d\t%s \t%s %n",
                                    rs.getInt("id"),
                                    rs.getString("descriptif"),
                                    rs.getString("numero"));
                        }
                        System.out.println("-----------------------"
                                + "-------------\n");
                        rs.close();
                    }
                }
            } else {
                System.out.println("Il n'y a pas encore de numeros"
                        + " dans la base de données!\n");
            }
        }
    }
}
