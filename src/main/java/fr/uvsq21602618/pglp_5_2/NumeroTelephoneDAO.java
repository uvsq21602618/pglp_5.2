package fr.uvsq21602618.pglp_5_2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

/**
 * Classe NumerotTelephoneDAO.
 * @author Nathalie
 *
 */
public class NumeroTelephoneDAO extends DAO<NumeroTelephone> {
    /**
     * Méthode de création.
     * @param obj L'objet à créer
     * @return obj l'objet qui vient d'etre creer
     * @throws IOException Exceptions liees aux entrees/sorties
     */
    @Override
    public NumeroTelephone create(final NumeroTelephone obj)
            throws IOException {
        String nomDir = "NumeroTels";
        File dir = new File(nomDir);
        FileOutputStream fileOut;
        ObjectOutputStream objOut;

        File file = new File(nomDir + "\\" + obj.getId() + ".txt");
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("Le dossier est créé!");
            } else {
                System.out.println("le dossier n'a pas pu être créé!");
            }
        }
        fileOut = new FileOutputStream(file);
        objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(obj);
        objOut.close();
        System.out.println("Le fichier est créé!");
        return obj;
    }
    /**
     * Méthode pour effacer.
     * @param obj L'objet à effacer
     */
    public void delete(final NumeroTelephone obj) {
        String nomDir = "NumeroTels";
        File dir = new File(nomDir);
        if (dir.exists()) {
            File file = new File(nomDir + "\\" + obj.getId() + ".txt");
            if (file.exists()) {
                file.delete();
                System.out.println("Le fichier est supprimé!");
            } else {
                System.out.println("Le fichier à supprimer n'existe pas!");
            }
        } else {
            System.out.println("Le dossier contenant le fichier n'existe pas!");
        }
    }
    /**
     * Méthode de mise à jour.
     * @param obj L'objet à mettre à jour
     * @throws IOException Exception liee aux entreés/sorties
     * @return obj
     */
    public NumeroTelephone update(final NumeroTelephone obj)
            throws IOException {
        String nomDir = "NumeroTels";
        File dir = new File(nomDir);
        if (dir.exists()) {
            File file = new File(nomDir + "\\" + obj.getId() + ".txt");
            if (file.exists()) {
                file.delete();
                obj.maj();
                this.create(obj);
            } else {
                System.out.println("Le fichier à mettre à jour n'existe pas!");
            }
        } else {
            System.out.println("Le dossier contenant le fichier n'existe pas!");
        }
        return obj;
    }
    /**
     * Méthode de recherche des informations.
     * @param id de l'information
     * @return num numéro de telephone du fichier
     * @throws IOException Exception liee aux entreés/sorties
     * @throws ClassNotFoundException Exception lié à une classe inexistante
     */
    public NumeroTelephone find(final int id)
            throws IOException, ClassNotFoundException {
        String nomDir = "NumeroTels";
        File dir = new File(nomDir);
        File search = new File(nomDir + "\\" + id + ".txt");
        Object deserialized = null;
        if (dir.exists()) {
            if (search.exists()) {
                byte[] fileContent = Files.readAllBytes(search.toPath());
                deserialized = deserialize(fileContent);
            } else {
                System.out.println("Le fichier n'existe pas!");
            }
            NumeroTelephone num = (NumeroTelephone) deserialized;
            System.out.println(num.toString());
            return num;
        } else {
            System.out.println("Le dossier n'existe pas!");
        }
        return null;
    }
}
