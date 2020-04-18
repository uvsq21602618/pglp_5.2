package fr.uvsq21602618.pglp_5_2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import fr.uvsq21602618.pglp_5_2.Personnel.Builder;
/**
 * Classe de PersonnelDAOJDBC.
 * @author Nathalie
 *
 */
public class PersonnelDAOJDBC extends DAOJDBC<Personnel> {
    /**
     * Le DAO de numeroTelephone.
     */
    private DAOJDBC<NumeroTelephone> numTelJDBC;
    /**
     * Constructeur de PersonnelDAOJDBC.
     * @throws SQLException Exception liee a l'acces a la base de donnees
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public PersonnelDAOJDBC() throws SQLException, IOException {
        super();
        numTelJDBC = new DAOFactoryJDBC().getNumeroTelephoneDAO();
    }
    /**
     * Méthode de création.
     * @param obj L'objet à créer
     * @return obj qui vient d'etre cree
     * @throws SQLException Exception liee a l'acces a la base de donnees
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public Personnel create(final Personnel obj) throws SQLException,
    IOException {
        DatabaseMetaData dbmd = getConnect().getMetaData();
        ResultSet rs = dbmd.getTables(null, null,
                "personnel".toUpperCase(), null);

        try (Statement creation = getConnect().createStatement()) {
            if (!rs.next()) {
                creation.executeUpdate("Create table personnel"
                        + " (id int primary key, nom varchar(30),"
                        + " prenom varchar(30), fonction varchar(30),"
                        + "date_de_naissance varchar(30))");
            }
            try {
                String updateString = ("insert into personnel values"
                        + " (?, '?', '?', '?', '?')");
                PreparedStatement update =
                        getConnect().prepareStatement(updateString);
                update.setInt(1, obj.getId());
                update.setString(2, obj.getNom());
                update.setString(3, obj.getPrenom());
                update.setString(4, obj.getFonction());
                update.setString(5, obj.getDateNaissance().toString());

                update.executeUpdate();
                update.close();

                rs = creation.executeQuery("SELECT * FROM personnel");

                System.out.println("---Table Personnel:---\n");
                System.out.println("id\t nom\t prenom\t fonction\t"
                        + " date_de_naissance");
                while (rs.next()) {
                    System.out.printf("%d\t%s\t%s\t%s\t%s%n", rs.getInt("id"),
                            rs.getString("nom"), rs.getString("prenom"),
                            rs.getString("fonction"),
                            rs.getString("date_de_naissance"));
                }
                System.out.println("---------------------"
                        + "---------------\n");

                rs.close();
                for (NumeroTelephone num : obj.getNumTelephones()) {
                    numTelJDBC.create(num);
                    this.correspondance(obj.getId(), num.getId());
                }

                System.out.println("L'objet " + obj.toString()
                + "a bien été enregistré!\n\n");
            } catch (org.apache.derby.shared.common.error
                    .DerbySQLIntegrityConstraintViolationException e) {
                System.out.println("Cet id a deja était utilisé"
                        + " pour la table personnel!\n");
            }
            creation.close();
            return obj;
        }
    }
    /**
     * Méthode pour effacer.
     * @param obj L'objet à effacer
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    public void delete(final Personnel obj) throws SQLException {
        try (Statement stmt = getConnect().createStatement()) {
            try (Statement stmt2 = getConnect().createStatement()) {
                int idNum, idPerso;
                idPerso = obj.getId();
                ResultSet rs = stmt.executeQuery("SELECT * FROM"
                        + " correspondance WHERE"
                        + " id_personnel=" + idPerso);
                String sql;

                while (rs.next()) {
                    idNum = rs.getInt("id_numero");
                    sql = "delete from correspondance"
                            + " where id_personnel=" + idPerso
                            + "and id_numero=" + idNum;
                    stmt2.executeUpdate(sql);
                    sql = "delete from numero_telephone where id=" + idNum;
                    stmt2.executeUpdate(sql);
                    System.out.printf("Le numero avec l'id " + idNum
                            + " a bien été supprimé!\n");
                }
                sql = "delete from appartenance_personnel"
                        + " where id=" + obj.getId();
                stmt2.executeUpdate(sql);
                sql = "delete from appartenance_sous_groupe"
                        + " where id=" + obj.getId();
                stmt2.executeUpdate(sql);
                sql = "delete from personnel where id=" + obj.getId();
                stmt2.executeUpdate(sql);
                stmt.close();
                stmt2.close();
                System.out.printf("Le personnel avec l'id " + obj.getId()
                + " et les correspondances associées"
                + " ont bien été supprimé!\n");
            }
        }
    }
    /**
     * Méthode de mise à jour.
     * @param obj L'objet à créer
     * @return obj qui vient d'etre cree
     * @throws SQLException Exception liee a l'acces a la base de donnees
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    public Personnel update(final Personnel obj)
            throws SQLException, IOException {
        try (Statement stmt =
                getConnect().createStatement()) {
            try (ResultSet result = stmt.executeQuery("select *"
                    + "from personnel where id="
                    + obj.getId())) {
                if (!result.next()) {
                    System.out.println("Cet identifiant pour personnel"
                            + " n'a pas encore été utilisé,"
                            + "il n'y a donc pas de mise a jour possible.");
                    this.create(obj);
                } else {
                    this.delete(obj);
                    this.create(obj);
                    System.out.println("La mise à jour du personnel d'id "
                            + obj.getId()
                            + " dans la table personnel a été effectué!\n");
                }
                result.close();
                stmt.close();
                return obj;
            }
        }
    }
    /**
     * Méthode de recherche des informations.
     * @param id de l'information
     * @return  le GroupePersonnel du fichier, null sinon
     * @throws SQLException Exception liee a l'acces a la base de donnees
     * @throws FileNotFoundException liee au fichier non trouve
     * @throws IOException liee aux entreés/sorties
     * @throws ClassNotFoundException Exception lié à une classe inexistante
     */
    public Personnel find(final int id) throws SQLException,
    FileNotFoundException, ClassNotFoundException, IOException {
        Personnel search = null;
        try (Statement stmt =
                getConnect().createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select *"
                    + "from personnel"
                    + " where id=" + id)) {
                if (!rs.next()) {
                    System.out.println("Il n'y a pas de personnel"
                            + " correspondant a l'id "
                            + id + " dans la table personnel!\n");
                    return null;
                }
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String fonction = rs.getString("fonction");
                String date = rs.getString("date_de_naissance");
                String[] tab = date.split("-");
                LocalDate lDate =
                        LocalDate.of(Integer.parseInt(tab[0]),
                        Integer.parseInt(tab[1]), Integer.parseInt(tab[2]));
                Builder b = new Builder(nom,
                        prenom, fonction,
                        lDate, id);
                search = b.build();

                int idNum;
                NumeroTelephone numTel;
                try (ResultSet rs2 = stmt.executeQuery("select *"
                        + " from correspondance"
                        + " where id_personnel=" + id)) {
                    while (rs2.next()) {
                        idNum = rs2.getInt("id_numero");
                        numTel = numTelJDBC.find(idNum);
                        if (numTel != null) {
                            b.numTelephones(numTel);
                        }
                    }

                    System.out.println("Le personnel suivant a ete"
                            + " trouve avec l'identifiant " + id + ":");
                    System.out.println(search.toString() + "\n");

                    rs2.close();
                    rs.close();
                    stmt.close();
                    return search;
                }
            }
        }
    }

    /**
     * Methode pour creer la table qui associe le numero a un membre du
     * personnel.
     * @param idPerso identifiant du personnel
     * @param idNum identifiant d'un des numeros de telephone
     * @throws SQLException Exception liee a l'acces a la base de donnees
     */
    private void correspondance(final int idPerso, final int idNum)
            throws SQLException {
        DatabaseMetaData dbmd = getConnect().getMetaData();
        ResultSet rs = dbmd.getTables(null, null,
                "correspondance".toUpperCase(), null);

        try (Statement stmt =
                getConnect().createStatement()) {
            if (!rs.next()) {
                stmt.executeUpdate("Create table correspondance"
                        + " (id_personnel int NOT NULL,"
                        + " id_numero int NOT NULL, "
                        + "primary key (id_personnel, id_numero),"
                        + "foreign key (id_personnel) references"
                        + " personnel(id),"
                        + "foreign key (id_numero) references"
                        + " numero_telephone(id))");
            }

            try {
                stmt.executeUpdate("insert into correspondance values ("
                        + idPerso + "," + idNum + ")");
                try (ResultSet rs2 = stmt.executeQuery("SELECT *"
                        + " FROM correspondance")) {
                    System.out.println("---Table correspondance:---\n");
                    System.out.println("id_personnel\t id_numero");
                    while (rs2.next()) {
                        System.out.printf("%d\t\t%d%n",
                                rs2.getInt("id_personnel"),
                                rs2.getInt("id_numero"));
                    }
                    System.out.println("---------------"
                            + "---------------------\n");
                    rs2.close();
                    rs.close();
                    stmt.close();
                }
            }  catch (org.apache.derby.shared.common
                    .error.DerbySQLIntegrityConstraintViolationException e) {
                System.out.println("Cet id a deja était utilisé pour "
                        + "la table correspondance!\n");
            }
        }
    }
}
