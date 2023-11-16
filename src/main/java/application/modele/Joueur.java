/**
 * Classe Joueur écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.modele;

import application.controleur.Plateau;

import java.util.ArrayList;

/**
 * La classe Joueur est utilisé dans le jeu pour identifier les différents joueurs.
 */
public class Joueur {
    private String nom;
    private String couleur;
    private ArrayList<Murs> listeMursNonPoses;
    private ArrayList<Murs> listeMursSurPlateau;
    private Pion pion;
    private int id;

    /**
     * Constructeur de la classe Joueur.
     * Chaque joueur à un id, un nom, une couleur, un pion, une liste de murs possés et non posés.
     *
     * @param id              int, Identifiant du joueur.
     * @param nom             String, Label du joueur.
     * @param couleur         String, Couleur du pion.
     * @param nombreMurs      int, Nombre de murs disponibles.
     * @param nombreMursPoses int, Nombre de murs indisponibles.
     */
    public Joueur(int id, String nom, String couleur, int nombreMurs, int nombreMursPoses) {
        Log.info("Joueur", "Création du joueur '" + nom + "' de couleur '" + couleur + "'.");
        setId(id);
        setNom(nom);
        setCouleur(couleur);
        this.listeMursNonPoses = initialisationListeMurs(nombreMurs);
        this.listeMursSurPlateau = initialisationListeMurs(nombreMursPoses);
    }

    /**
     * Cette initialisation permet de créer les murs.
     *
     * @param nombre int, Nombre de murs a créer.
     * @return ArrayList<Murs>, Liste des murs créés.
     */
    public ArrayList<Murs> initialisationListeMurs(int nombre) {
        ArrayList<Murs> liste = new ArrayList<>();
        for (int nbr = 0; nbr < nombre; nbr++) {
            liste.add(new Murs());
        }
        return liste;
    }

    /**
     * Permet de changer l'emplacement du pion.
     * La valeur de l'emplacement change.
     *
     * @param emplacement Emplacement
     */
    public void setPion(Emplacement emplacement) {

        emplacement.setValeur(Val.__PION__);
        if (this.pion instanceof Pion) {
            this.pion.setEmplacement(emplacement);
        } else {
            this.pion = new Pion(couleur, emplacement);
        }
        Log.info("Joueur", "Changement de l'emplacement du pion, de '" +
                getCoordsString() + "', à '" + emplacement.getX() + " : " + emplacement.getY() + "'."
        );
    }

    public String getNom() {
        return nom;
    }

    private void setNom(String nom) {
        this.nom = nom;
    }

    public String getCouleur() {
        return couleur;
    }

    private void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    /**
     * Converti les coordonnée de l'emplacement en String.
     *
     * @return String
     */
    public String getCoordsString() {
        return getX() + " : " + getY();
    }

    public int getX() {
        return this.pion.getEmplacement().getX();
    }

    public int getY() {
        return this.pion.getEmplacement().getY();
    }

    /**
     * Converti toutes les données du joueur en un seul String.
     *
     * @return String
     */
    public String toString() {
        return this.id + " : " +
                this.nom + " : " +
                this.couleur + " : " +
                //getCoordsString() + " : " +
                this.listeMursNonPoses.size() + " : " +
                this.listeMursSurPlateau.size();
    }
    public  boolean testSetMur(){
        return !listeMursNonPoses.isEmpty();
    }
    public Murs setMur(Emplacement casegauche,Emplacement casemilieu,Emplacement casedroite){
        if (testSetMur()) {
            Murs mur = listeMursNonPoses.get(0);
            listeMursNonPoses.remove(0);
            mur.setPosition(casegauche,casemilieu,casedroite);
            listeMursSurPlateau.add(mur);
            Log.info("Joueur","Le joueur "+this.nom+" a posé un mur en "+casegauche.toStringCoords()+" "+casedroite.toStringCoords()+" il lui reste "+this.listeMursNonPoses.size()+" mur(s)");
            return mur;
        }
        Log.warn("Joueur","Mur non posé car le joueur n'a plus de mur");
        return new Murs();
    }
    public boolean undoSetMur(Murs mur, Plateau plateau){
        listeMursSurPlateau.remove(mur);
        listeMursNonPoses.add(new Murs());
        mur.undosetPosition(plateau.getEmplacement(mur.getCasesPrisent()[0].getX(),mur.getCasesPrisent()[0].getY()),plateau.getEmplacement(mur.getCasesPrisent()[1].getX(),mur.getCasesPrisent()[1].getY()),plateau.getEmplacement(mur.getCasesPrisent()[2].getX(),mur.getCasesPrisent()[2].getY()));
        Log.info("Joueur","Le joueur "+this.nom+" a retiré un mur ");
        return true;
    }

}

