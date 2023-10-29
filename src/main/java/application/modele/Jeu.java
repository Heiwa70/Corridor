package application.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jeu {

    private Plateau plateau;
    private ArrayList<Joueur> listeJoueurs;
    private int joueurActuel;
    private HashMap<Joueur, Integer> pointsJoueur;

    public Jeu(int taillePlateauWidth, int taillePlateauHeight, ArrayList<Joueur>  listeJoueur){
        this.plateau = new Plateau(taillePlateauWidth, taillePlateauHeight);
        this.listeJoueurs = listeJoueur;
        this.pointsJoueur = new HashMap<>();
        this.joueurActuel = 0;
    }

    private void initialisation(){
        for(Joueur joueur:this.listeJoueurs){
            this.pointsJoueur.put(joueur,0);
        }
    }

    public void reinitialisation(){
        this.joueurActuel = 0;
        for(Joueur joueur:this.listeJoueurs){
            this.pointsJoueur.put(joueur,0);
        }
    }

    public boolean testEmplacementCasePion(int x, int y){
        return this.plateau.recupererEmplacementCasePion(x, y).getOccupation();
    }

    public boolean testEmplacementHorizontalMurs(int x, int y){
        return this.plateau.recupererEmplacementHorizontalMurs(x, y).getOccupation();
    }

    public boolean testEmplacementVerticaleMurs(int x, int y){
        return this.plateau.recupererEmplacementVerticaleMurs(x, y).getOccupation();
    }
}
