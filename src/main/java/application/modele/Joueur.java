/**
 * Classe Joueur écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.modele;

import java.util.ArrayList;

/**
 * La classe Joueur
 */
public class Joueur {
    private String nom;
    private String couleur;
    private ArrayList<Murs> listeMursNonPoses;
    private ArrayList<Murs> listeMursSurPlateau;
    private Pion pion;
    private int id;

    public Joueur(int id, String nom, String couleur, int nombreMurs, int nombreMursPoses) {
        setId(id);
        setNom(nom);
        setCouleur(couleur);
        this.listeMursNonPoses = initialisationListeMurs(nombreMurs);
        this.listeMursSurPlateau = initialisationListeMurs(nombreMursPoses);
    }

    public ArrayList<Murs> initialisationListeMurs(int nombre) {
        ArrayList<Murs> liste = new ArrayList<>();
        for (int nbr = 0; nbr < nombre; nbr++) {
            liste.add(new Murs());
        }
        return liste;
    }

    public void setPion(Emplacement emplacement) {

        emplacement.setValeur(Val.__PION__);
        this.pion = new Pion(couleur, emplacement);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getCoordsString() {
        return getX() + " : " + getY();
    }

    public int getX() {
        return this.pion.getEmplacement().getX();
    }

    public int getY() {
        return this.pion.getEmplacement().getY();
    }

    public String toString() {
        return this.id + " : " + this.nom + " : " + this.couleur + " : " + getCoordsString() + " : " + this.listeMursNonPoses.size() + " : " + this.listeMursSurPlateau.size();
    }
}

