package fr.uvsq21602618.pglp_5_2;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import fr.uvsq21602618.pglp_5_2.AbstractDAOFactory.DAOType;
import fr.uvsq21602618.pglp_5_2.Personnel.Builder;
/**
 * Singleton contenant le main.
 * @author Nathalie
 */
public enum AppSingleton {
    /**
     * L'enum qui contient le code du main.
     */
    ENVIRONNEMENT;
    /**
     * Execution du programme.
     * @throws SQLException Exception liee a l'acces a la base de donnees
     * @throws IOException Exceptions liees aux entrees/sorties
     * @throws ClassNotFoundException Exceptions liees a une classe inexistante
     */
    public void run() throws IOException,
    SQLException, ClassNotFoundException {
        /*NumeroTelephone portable =
                new NumeroTelephone("portable", "0751624519", 1);
        NumeroTelephone portable2 =
                new NumeroTelephone("portable2", "0651624519", 2);
        NumeroTelephone portable1maj =
                new NumeroTelephone("portablemaj", "0851624519", 1);
        NumeroTelephone portable3 =
                new NumeroTelephone("portable3", "0699999999", 3);*/

        /*DAOJDBC<NumeroTelephone> numTelJDBC;
        numTelJDBC = DAOFactoryJDBC.getNumeroTelephoneDAOJDBC();
        numTelJDBC.create(portable);
        numTelJDBC.create(portable2);
        numTelJDBC.delete(portable2);
        numTelJDBC.find(1);
        numTelJDBC.update(portable1maj);
        numTelJDBC.update(portable3);*/

        /*DAOJDBC<Personnel> personnel = DAOFactoryJDBC.getPersonnelDAOJDBC();
        Builder b = new Builder("SMITH", "John", "secrétaire",
                LocalDate.of(1964, 8, 25), 1);
        b.numTelephones(portable);
        b.numTelephones(portable2);
        b.numTelephones(portable3);
        Personnel secretaire = b.build();

        personnel.create(secretaire);
        personnel.delete(secretaire);
        personnel.create(secretaire);
        personnel.find(1);*/

        NumeroTelephone portable =
                new NumeroTelephone("portable", "0751624519", 1);
        Builder b = new Builder("SMITH", "John", "secrétaire",
                LocalDate.of(1964, 8, 25), 1);
        b.numTelephones(portable);
        Personnel secretaire = b.build();
        NumeroTelephone portable2 =
                new NumeroTelephone("portable", "0651424519", 2);
        Builder b2 = new Builder("WHITE", "Jim", "chef de service",
                LocalDate.of(1964, 8, 25), 2);
        b2.numTelephones(portable2);
        Personnel chefDeService = b2.build();

        DAOJDBC<GroupePersonnels> grPersoJDBC =
                new DAOFactoryJDBC().getGroupePersonnelsDAO();
        GroupePersonnels departement =
                new GroupePersonnels("Departement", 1);
        GroupePersonnels service =
                new GroupePersonnels("Service", 3);
        service.add(chefDeService);
        departement.add(secretaire);
        departement.add(service);

        grPersoJDBC.create(departement);
        grPersoJDBC.delete(service);
        grPersoJDBC.create(service);
        grPersoJDBC.find(3);

        ((GroupePersonnelsDAOJDBC) grPersoJDBC)
        .affichageTableGroupePersonnels();
        ((GroupePersonnelsDAOJDBC) grPersoJDBC)
        .affichageTableAppartenanceGroupe();
        ((GroupePersonnelsDAOJDBC) grPersoJDBC)
        .affichageTableAppartenancePersonnel();

        DAO<Personnel> personnelDAO = AbstractDAOFactory
                .getFactory(DAOType.JDBC).getPersonnelDAO();
        //System.out.println(personnelDAO.find(1) + "\n");
        personnelDAO.find(1);
        personnelDAO = AbstractDAOFactory
                .getFactory(DAOType.FILE).getPersonnelDAO();
        personnelDAO.find(1);

        DAOJDBC<Personnel> personnel = new DAOFactoryJDBC().getPersonnelDAO();
        ((PersonnelDAOJDBC) personnel).affichageTablePersonnel();
        ((PersonnelDAOJDBC) personnel).affichageTableCorrespondance();

        DAOJDBC<NumeroTelephone> numeroTel = new DAOFactoryJDBC()
                .getNumeroTelephoneDAO();
        ((NumeroTelephoneDAOJDBC) numeroTel).affichageTableNumero();
    }
    /**
     * Main.
     * @param args pour le main
     * @throws SQLException Exception liee a l'acces a la base de donnees
     * @throws ClassNotFoundException Exception lié à une classe inexistante
     * @throws IOException liee aux entreés/sorties
     */
    public static void main(final String[] args) throws SQLException,
    ClassNotFoundException, IOException {
        ENVIRONNEMENT.run();
    }
}
