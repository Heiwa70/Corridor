package application.controleur;

import application.modele.Emplacement;
import application.modele.GestionSauvegardes;
import application.modele.Joueur;
import application.modele.Val;

import java.util.*;

public class Jeu {

    private Plateau plateau;
    private ArrayList<Joueur> listeJoueurs;
    private int idJoueurActuel;
    private HashMap<Joueur, Integer> pointsJoueur;
    private Integer[][] emplacementJoueur;
    private Scanner scanner;
    private GestionSauvegardes gestionSauvegardes;

    public Jeu(int taillePlateauWidth, int taillePlateauHeight, ArrayList<Joueur>  listeJoueur){
        gestionSauvegardes = new GestionSauvegardes();
        this.plateau = new Plateau(taillePlateauWidth, taillePlateauHeight);
        this.listeJoueurs = listeJoueur;
        this.pointsJoueur = new HashMap<>();
        this.emplacementJoueur = new Integer[][]{
            {(taillePlateauWidth)/2, taillePlateauHeight-1},
            {(taillePlateauWidth)/2, 0},
            {taillePlateauWidth-1, (taillePlateauHeight)/2},
            {0, (taillePlateauHeight)/2},
        };

        initialisation();
        this.scanner = new Scanner(System.in);
    }

    public Jeu(String nomSauvegarde){

        gestionSauvegardes = new GestionSauvegardes();
        chargement(nomSauvegarde);
        this.scanner = new Scanner(System.in);
    }

    private void initialisation(){
        int position = 0;
        this.idJoueurActuel = 0;
        for(Joueur joueur:this.listeJoueurs){
            joueur.setPion(this.plateau.getEmplacementCasePion(this.emplacementJoueur[position][0], this.emplacementJoueur[position][1]));
            this.pointsJoueur.put(joueur,0);
            position++;
        }
    }

    public List<Integer[]> listeMouvementsPion(int x, int y){

        List<Integer[]> possibilites = new ArrayList<>();

        if(testCase(x,y,Val.__PION__)){

            List<List<Integer>> vec = Arrays.asList(Arrays.asList(1,0),Arrays.asList(0,1),Arrays.asList(-1,0),Arrays.asList(0,-1));
            for (List<Integer> i : vec){
                if(!testCase(x+i.get(0), y+i.get(1), Val.CASEMURS)) {
                    if (testCase(x + 2 * i.get(0), y + 2 * i.get(1), Val.__PION__)) {
                        if (!testCase(x + 3 * i.get(0), y + 3 * i.get(1), Val.CASEMURS)) {
                            if (testCase(x + 4 * i.get(0), y + 4 * i.get(1), Val.CASEPION)) {
                                //x+4*i.get(0) et y+4*i.get(1) possibilité
                                ajoutPossibilite(possibilites,x + 4 * i.get(0), y + 4 * i.get(1));
                            }
                        } else {
                            if (i.get(0) == 0) {
                                if (testCase(x - 1, y + 2 * i.get(1), Val.CASEMURS)) {
                                    if (testCase(x - 2, y + 2 * i.get(1), Val.CASEPION)) {
                                        //x-2 et y+2*i.get(1) possibilité
                                        ajoutPossibilite(possibilites,x - 2, y + 2 * i.get(1));
                                    }
                                }
                                if (testCase(x + 1, y + 2 * i.get(1), Val.CASEMURS)) {
                                    if (testCase(x + 2, y + 2 * i.get(1), Val.CASEPION)) {
                                        //x+2 et y+2*i.get(1) possibilité
                                        ajoutPossibilite(possibilites,x + 2, y + 2 * i.get(1));
                                    }
                                }
                            } else {
                                if (testCase(x + 2 * i.get(0), y - 1, Val.CASEMURS)) {
                                    if (testCase(x + 2 * i.get(0), y - 2, Val.CASEPION)) {
                                        //x+2*i.get(0)  et y-2 possibilité
                                        ajoutPossibilite(possibilites,x + 2 * i.get(0), y - 2);
                                    }
                                }
                                if (testCase(x + 2 * i.get(0), y + 1, Val.CASEMURS)) {
                                    if (testCase(x + 2 * i.get(0), y + 2, Val.CASEPION)) {
                                        //x+2*i.get(0) et y+2 possibilité
                                        ajoutPossibilite(possibilites,x + 2 * i.get(0), y + 2);
                                    }
                                }
                            }
                        }

                    } else {
                        //x+2*i.get(0), y+2*i.get(1)  possibilité
                        ajoutPossibilite(possibilites,x + 2 * i.get(0), y + 2 * i.get(1));
                    }
                }
            }
        }
        return possibilites;
    }

    private void ajoutPossibilite(List<Integer[]> possibilites, int x, int y){
        if(testEmplacementSurPlateau(x,y)){
            possibilites.add(new Integer[]{x, y});
        }
    }

    public boolean testEmplacementSurPlateau(int x, int y){
        return 0<=x && x<this.plateau.getWidth() && 0<=y && y< this.plateau.getHeight();
    }

    public boolean testCase(int x, int y, Val type){
        Emplacement laCase = this.plateau.getEmplacement(x,y);
        if(laCase!=null){
            return laCase.getValeur() == type;
        }
        return false;
    }

    public Joueur getJoueurActuel(){
        return this.listeJoueurs.get(this.idJoueurActuel);
    }

    public void start(){
        while(!finPartie()){
            System.out.println(this.plateau.toString(false));
            Joueur joueurActuel = getJoueurActuel();
            System.out.println("C'est à '"+joueurActuel.getNom()+"' de jouer.");
            System.out.println("Votre pion est situé à : "+joueurActuel.getCoordsString());
            List<Integer[]> listeMouvementsPossibles = listeMouvementsPion(joueurActuel.getX(), joueurActuel.getY());

            System.out.println("\nVoici les coups possible par votre pion :");
            int nbr = 0;
            for(Integer[] position : listeMouvementsPossibles){
                System.out.println(nbr+")   -  X = "+position[0]+", Y = "+position[1]);
                nbr++;
            }

            int choix = 0;

            do {
                System.out.print("\nQuel est votre choix ?");
                try {
                    choix = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                }
            } while (choix<0 || choix>=nbr);

            Integer[] newPosition = listeMouvementsPossibles.get(choix);

            System.out.println("\nLe joueur '"+"' déplace son pion de la case à : \n    X = "+joueurActuel.getX()+" ->  X = "+newPosition[0]+"\n    Y = "+joueurActuel.getX()+" ->   Y = "+newPosition[1]);
            this.plateau.getEmplacement(joueurActuel.getX(), joueurActuel.getY()).setValeur(Val.CASEPION);
            joueurActuel.setPion(this.plateau.getEmplacement(newPosition[0], newPosition[1]));
            this.idJoueurActuel = (this.idJoueurActuel+1)%this.listeJoueurs.size();
            break;
        }
    }

    public boolean finPartie(){
        return false;
    }

    public void sauvegarde(String nomFichier){

        gestionSauvegardes.enregistrement(nomFichier, this.plateau, this.pointsJoueur, this.idJoueurActuel);
    }

    private void chargement(String nomFichier) {

        HashMap<Plateau, HashMap<Joueur, Integer>> data = gestionSauvegardes.chargement(nomFichier, this.idJoueurActuel);

        for (Plateau plateauSave : data.keySet()) {
            this.plateau = plateauSave;
            this.pointsJoueur = data.get(plateauSave);
            ArrayList<Joueur> listeTemporaire = new ArrayList<>();
            for(Joueur joueurSave : this.pointsJoueur.keySet()){
                listeTemporaire.add(joueurSave);
            }
            this.listeJoueurs = listeTemporaire;
        }
    }
}
