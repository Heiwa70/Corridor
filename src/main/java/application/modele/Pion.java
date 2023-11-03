/**
 * Classe Pion écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.modele;

/**
 * La classe Pion est utilisé pour gérer le pion de chaque joueur.
 * Elle a une couleur et elle est positionné sur un emplacement du plateau.
 */
public class Pion {
    private String couleur;
    private Emplacement emplacement;

    /**
     * Constructeur du Pion qui initialise sa couleur et son emplacement.
     * @param couleur String, la couleur du pion.
     * @param emplacement Emplacement, l'emplacement du pion sur le plateau.
     */
    public Pion(String couleur, Emplacement emplacement){
        setCouleur(couleur);
        setEmplacement(emplacement);
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Emplacement getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    public String toString(){
        return this.emplacement.toString();
    }
}
