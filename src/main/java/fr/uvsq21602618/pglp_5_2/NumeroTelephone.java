package fr.uvsq21602618.pglp_5_2;

import java.io.Serializable;

/**
 * Classe qui représente le numéro de téléphone.
 * @author natha
 *
 */
public class NumeroTelephone implements Serializable {
    /**
     * SerialVersion.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Méthode de comparaison.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NumeroTelephone other = (NumeroTelephone) obj;
        if (descriptif == null) {
            if (other.descriptif != null) {
                return false;
            }
        } else if (!descriptif.equals(other.descriptif)) {
            return false;
        }
        if (numero == null) {
            if (other.numero != null) {
                return false;
            }
        } else if (!numero.equals(other.numero)) {
            return false;
        }
        return true;
    }
    /**
     * Méthode de hachage.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((descriptif == null) ? 0 : descriptif.hashCode());
        result = prime * result + id;
        result = prime * result
                + ((numero == null) ? 0 : numero.hashCode());
        return result;
    }
    /**
     * Le type de numéro (fixe, portable...).
     */
    private String descriptif;
    /**
     * Le numéro en lui-même avec des chiffres.
     */
    private String numero;
    /**
     * Le numero d'id du telephone.
     */
    private int id;
    /**
     * Le constructeur du numéro de téléphone.
     * @param desc la description du numéro (fixe, portable ...)
     * @param num le numéro de téléphones
     * @param id2 l'id du numero
     */
    public NumeroTelephone(final String desc, final String num, final int id2) {
        this.descriptif = desc;
        this.numero = num;
        this.setId(id2);
    }
    /**
     * Méthode pour récupérer le descriptif du numéro.
     * @return string le descriptif
     */
    public String getDescriptif() {
        return descriptif;
    }
    /**
     * Méthode pour definir le descriptif du telephone.
     * @param desc le descriptif du telephone
     */
    public void setDescriptif(final String desc) {
        this.descriptif = desc;
    }
    /**
     * Méthode pour récupérer le numéro de téléphone.
     * @return string le numéro
     */
    public String getNumero() {
        return numero;
    }
    /**
     * Méthode pour definir le numero du telephone.
     * @param num le numero du telephone
     */
    public void setNumero(final String num) {
        this.numero = num;
    }
    /**
     * Méthode pour recuperer le descriptif ainsi que le numero.
     * @return le descriptif et le numero
     */
    public String toString() {
        return this.getDescriptif() + ": " + this.getNumero();
    }
    /**
     * Méthode pour récupérer l'id du telephone.
     * @return int l'id
     */
    public int getId() {
        return id;
    }
    /**
     * Méthode pour definir l'id du telephone.
     * @param id2 du telephone
     */
    public void setId(final int id2) {
        this.id = id2;
    }
}
