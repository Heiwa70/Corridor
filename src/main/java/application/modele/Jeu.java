package application.modele;

import java.lang.reflect.Array;
import java.util.*;

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

    public void listeMouvementsPion(int x, int y){
        if(testCase(x,y,this.plateau.VALPION)){

            List<List<Integer>> vec = Arrays.asList(Arrays.asList(1,0),Arrays.asList(0,1),Arrays.asList(-1,0),Arrays.asList(0,-1));
            for (List<Integer> i : vec){
                if(!testCase(x+i.get(0), y+i.get(1),this.plateau.VALCASEMURS)){
                    if(testCase(x+2*i.get(0), y+2*i.get(1),this.plateau.VALPION)){
                        if (!testCase(x+3*i.get(0), y+3*i.get(1),this.plateau.VALCASEMURS)){
                            if(testCase(x+4*i.get(0), y+4*i.get(1),this.plateau.VALCASEPION)){
                                //x+4*i.get(0) et y+4*i.get(1) possibilité

                            }
                        }else{
                            if (i.get(0)==0) {
                                if (testCase(x - 1, y + 2 * i.get(1), this.plateau.VALCASEMURS)) {
                                    if (testCase(x - 2, y + 2 * i.get(1), this.plateau.VALCASEPION)) {
                                        //x-2 et y+2*i.get(1) possibilité
                                    }
                                }
                                if (testCase(x + 1, y + 2 * i.get(1), this.plateau.VALCASEMURS)) {
                                    if (testCase(x + 2, y + 2 * i.get(1), this.plateau.VALCASEPION)) {
                                        //x+2 et y+2*i.get(1) possibilité
                                    }
                                }
                            }
                            else {
                                if (testCase(x+2*i.get(0), y -1, this.plateau.VALCASEMURS)) {
                                    if (testCase(x+2*i.get(0), y - 2, this.plateau.VALCASEPION)) {
                                        //x+2*i.get(0)  et y-2 possibilité
                                    }
                                }
                                if (testCase(x+2*i.get(0), y +1, this.plateau.VALCASEMURS)) {
                                    if (testCase(x+2*i.get(0), y + 2 , this.plateau.VALCASEPION)) {
                                        //x+2*i.get(0) et y+2 possibilité
                                    }
                                }
                            }
                        }
                    }
                }else{
                    //x+2*i.get(0), y+2*i.get(1)  possibilité
                }
            }
        }
    }

    public boolean testCase(int x, int y, int type){
        Emplacement laCase = this.plateau.getEmplacementCasePion(x,y);
        if(laCase!=null){
            return laCase.getValeur() == type;
        }
        return false;
    }

}
