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
        this.pion = new Pion(couleur);

        initialisationListeMurs(this.listeMursNonPoses, nombreMurs);
        initialisationListeMurs(this.listeMursSurPlateau, 0);
    }

    public Joueur(String nom, String couleur, int nombreMurs, int nombreMursPoses){
        setNom(nom);
        setCouleur(couleur);
        this.pion = new Pion(couleur);
        initialisationListeMurs(this.listeMursNonPoses, nombreMurs);
        initialisationListeMurs(this.listeMursSurPlateau, nombreMursPoses);
    }

    public void initialisationListeMurs(ArrayList<Murs> liste, int nombre) {
        liste = new ArrayList<>();
        for (int nbr = 0; nbr < nombre; nbr++) {
            liste.add(new Murs());
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

    public String toString(){
        return this.nom+" : "+this.couleur+" : "+this.listeMursNonPoses.size()+" : "+this.listeMursSurPlateau.size();
    }
}

