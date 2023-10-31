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

    public void listeMouvementsPion(int x, int y){
        if(testCase(x,y,this.plateau.VALPION)){

            int[][] pas = {{0,-1},{0,1},{-1,0},{1}};

            int pasX = pas[0][0];
            int pasY = pas[0][1];


            // HAUT
            if(testCase(x, y-1,this.plateau.VALCASEMURS)){
                if(testCase(x, y-2,this.plateau.VALPION)){
                    if(testCase(x, y-3,this.plateau.VALCASEMURS)){
                        if(testCase(x, y-4,this.plateau.VALCASEPION)){
                            //x et y-4 possibilité
                        }
                    }else{
                        if(testCase(x-1, y-2,this.plateau.VALCASEMURS)){
                            if(testCase(x-2, y-2,this.plateau.VALCASEPION)){
                                //x-2 et y-2 possibilité
                            }
                        }
                        if(testCase(x+1, y-2,this.plateau.VALCASEMURS)){
                            if(testCase(x+2, y-2,this.plateau.VALCASEPION)){
                                //x+2 et y-2 possibilité
                            }
                        }
                    }
                }
            }else{
                //x, y-2 possibilité
            }

            // BAS
            if(testCase(x, y+1,this.plateau.VALCASEMURS)){
                if(testCase(x, y+2,this.plateau.VALPION)){
                    if(testCase(x, y+3,this.plateau.VALCASEMURS)){
                        if(testCase(x, y+4,this.plateau.VALCASEPION)){
                            //x et y+4 possibilité
                        }
                    }else{
                        if(testCase(x-1, y+2,this.plateau.VALCASEMURS)){
                            if(testCase(x-2, y+2,this.plateau.VALCASEPION)){
                                //x-2 et y+2 possibilité
                            }
                        }
                        if(testCase(x+1, y+2,this.plateau.VALCASEMURS)){
                            if(testCase(x+2, y+2,this.plateau.VALCASEPION)){
                                //x+2 et y+2 possibilité
                            }
                        }
                    }
                }
            }else{
                //x, y+2 possibilité
            }



            // Gauche
            if(testCase(x-1, y,this.plateau.VALCASEMURS)){
                if(testCase(x-2, y,this.plateau.VALPION)){
                    if(testCase(x-3, y,this.plateau.VALCASEMURS)){
                        if(testCase(x-4, y,this.plateau.VALCASEPION)){
                            //x et y-4 possibilité
                        }
                    }else{
                        if(testCase(x-2, y-1,this.plateau.VALCASEMURS)){
                            if(testCase(x-2, y-2,this.plateau.VALCASEPION)){
                                //x-2 et y-2 possibilité
                            }
                        }
                        if(testCase(x-2, y+1,this.plateau.VALCASEMURS)){
                            if(testCase(x-2, y+2,this.plateau.VALCASEPION)){
                                //x-2 et y+2 possibilité
                            }
                        }
                    }
                }
            }else{
                //x-2, y possibilité
            }

            // Droite
            if(testCase(x+1, y,this.plateau.VALCASEMURS)){
                if(testCase(x+2, y,this.plateau.VALPION)){
                    if(testCase(x+3, y,this.plateau.VALCASEMURS)){
                        if(testCase(x+4, y,this.plateau.VALCASEPION)){
                            //x+4 et y possibilité
                        }
                    }else{
                        if(testCase(x+2, y-1,this.plateau.VALCASEMURS)){
                            if(testCase(x+2, y-2,this.plateau.VALCASEPION)){
                                //x+2 et y-2 possibilité
                            }
                        }
                        if(testCase(x+2, y+1,this.plateau.VALCASEMURS)){
                            if(testCase(x+2, y+2,this.plateau.VALCASEPION)){
                                //x+2 et y+2 possibilité
                            }
                        }
                    }
                }
            }else{
                //x+2, y possibilité
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
