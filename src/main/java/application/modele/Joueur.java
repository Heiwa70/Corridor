package application.modele;

import java.util.ArrayList;

public class Joueur {
    private String nom;
    private String couleur;
    private ArrayList<Murs> listeMursNonPoses;
    private ArrayList<Murs> listeMursSurPlateau;
    private Pion pion;

    public Joueur(String nom, String couleur, int nombreMurs){
        setNom(nom);
        setCouleur(couleur);
        this.listeMursNonPoses = new ArrayList<>();
        this.listeMursSurPlateau = new ArrayList<>();
        this.pion = new Pion(couleur);
        initialisationListeMurs(nombreMurs);
    }

    public void initialisationListeMurs(int nombre) {

        for (int nbr = 0; nbr < nombre; nbr++) {
            this.listeMursNonPoses.add(new Murs());
        }
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
}

