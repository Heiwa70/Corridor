/**
 * Classe Jeu écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.controleur;

import application.modele.Emplacement;
import application.modele.GestionSauvegardes;
import application.modele.Joueur;
import application.modele.Val;
import application.modele.Log;

import java.util.*;

/**
 * La classe Jeu permet de jouer sur le terminal le jeu du Quoridor.
 */
public class Jeu {

    private String nomPartie;
    private Plateau plateau;
    private Joueur[] listeJoueurs;
    private int idJoueurActuel;
    private HashMap<Joueur, Integer> pointsJoueur;
    private Integer[][] emplacementJoueur;
    private Scanner scanner;
    private GestionSauvegardes gestionSauvegardes;

    /**
     * Ce constructeur permet de créer directement une partie.
     */
    public Jeu() {
        gestionSauvegardes = new GestionSauvegardes();
        this.scanner = new Scanner(System.in);
        initialisation();
        start();
    }

    /**
     * Ce constructeur permet de charger une partie et d'en créer une si elle n'existe pas.
     */
    public Jeu(String nomSauvegarde) {
        gestionSauvegardes = new GestionSauvegardes();
        this.scanner = new Scanner(System.in);
        if (!chargement(nomSauvegarde)) {
            initialisation();
        }
        start();
    }

    /**
     * Initialise le jeu en demandant à l'utilisateur de donner un nom et de créer des joueurs.
     */
    private void initialisation() {
        int nombreJoueur = 2;
        int nombreMurs = 10;
        int width = 9;
        int height = 9;

        System.out.print("Nom de la partie : ");
        this.nomPartie = scanner.nextLine();

        do {
            System.out.print("\nNombre de joueurs entre 2-4 (defaut = 2): ");
            try {
                nombreJoueur = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        } while (nombreJoueur > 4 || nombreJoueur < 2);

        this.listeJoueurs = new Joueur[nombreJoueur];

        int idJoueur = 0;

        while (nombreJoueur > 0) {

            System.out.print("\nNom du joueur : ");
            String nom = scanner.nextLine();
            System.out.print("Couleur du joueur : ");
            String couleur = scanner.nextLine();

            this.listeJoueurs[idJoueur] = new Joueur(idJoueur, nom, couleur, nombreMurs, 0);
            nombreJoueur--;
            idJoueur++;
        }

        System.out.println("\nCréation du jeu avec un plateau de " + width + " x " + height);
        this.plateau = new Plateau(width, height);

        this.pointsJoueur = new HashMap<>();
        this.emplacementJoueur = new Integer[][]{
                {width / 2, height - 1},
                {width / 2, 0},
                {width - 1, height / 2},
                {0, height / 2},
        };

        int position = 0;
        this.idJoueurActuel = 0;
        for (Joueur joueur : this.listeJoueurs) {
            joueur.setPion(this.plateau.getEmplacementCasePion(this.emplacementJoueur[position][0], this.emplacementJoueur[position][1]));
            this.pointsJoueur.put(joueur, 0);
            position++;
        }
    }

    public String getNomPartie() {
        return nomPartie;
    }

    /**
     * Récupère la liste des mouvements possible d'une case pion.
     *
     * @param x
     * @param y
     * @return
     */
    public List<int[]> listeMouvementsPion(int x, int y) {

        List<int[]> possibilites = new ArrayList<>();

        if (testCase(x, y, Val.__PION__)) {

            int[][] vec = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            for (int[] i : vec) {

                int vecX = i[0];
                int vecY = i[1];

                if (testCase(x + vecX, y + vecY, Val.CASEMURS)) {
                    continue;
                }
                if (testCase(x + 2 * vecX, y + 2 * vecY, Val.__PION__)) {
                    if (!testCase(x + 3 * vecX, y + 3 * vecY, Val.CASEMURS)) {
                        consitionAjoutPossibilite(possibilites, x + 4 * vecX, y + 4 * vecY);
                    } else {
                        if (vecX == 0) {
                            if (testCase(x - 1, y + 2 * vecY, Val.CASEMURS)) {
                                consitionAjoutPossibilite(possibilites, x - 2, y + 2 * vecY);
                            }
                            if (testCase(x + 1, y + 2 * vecY, Val.CASEMURS)) {
                                consitionAjoutPossibilite(possibilites, x + 2, y + 2 * vecY);
                            }
                        } else {
                            if (testCase(x + 2 * vecX, y - 1, Val.CASEMURS)) {
                                consitionAjoutPossibilite(possibilites, x + 2 * vecX, y - 2);
                            }
                            if (testCase(x + 2 * vecX, y + 1, Val.CASEMURS)) {
                                consitionAjoutPossibilite(possibilites, x + 2 * vecX, y + 2);
                            }
                        }

                    }
                } else {
                    ajoutPossibilite(possibilites, x + 2 * vecX, y + 2 * vecY);
                }
            }
        }
        return possibilites;
    }

    private void ajoutPossibilite(List<int[]> possibilites, int x, int y) {
        if (testEmplacementSurPlateau(x, y)) {
            possibilites.add(new int[]{x, y});
        }
    }

    private void consitionAjoutPossibilite(List<int[]> possibilites, int x, int y) {
        if (testCase(x, y, Val.CASEPION)) {
            ajoutPossibilite(possibilites, x, y);
        }
    }

    public boolean testEmplacementSurPlateau(int x, int y) {
        return 0 <= x && x < this.plateau.getWidth() && 0 <= y && y < this.plateau.getHeight();
    }

    public boolean testCase(int x, int y, Val type) {
        Emplacement laCase = this.plateau.getEmplacement(x, y);
        if (laCase != null) {
            return laCase.getValeur() == type;
        }
        return false;
    }

    public Joueur getJoueurActuel() {
        return this.listeJoueurs[this.idJoueurActuel];
    }

    /**
     * Démarrage du jeu.
     */
    public void start() {

        while (!finPartie()) {

            // Informe l'utilisateur de l'état de la partie.
            System.out.println(this.plateau.toString(false));
            Joueur joueurActuel = getJoueurActuel();
            System.out.println("C'est à '" + joueurActuel.getNom() + "' de jouer.");
            System.out.println("Votre pion est situé à : " + joueurActuel.getCoordsString());

            // Demande le prochain coup à l'utilisateur.
            List<int[]> listeMouvementsPossibles = listeMouvementsPion(joueurActuel.getX(), joueurActuel.getY());

            System.out.println("\nVoici les coups possible par votre pion :");
            int nbr = 0;
            for (int[] position : listeMouvementsPossibles) {
                System.out.println(nbr + ")   -  X = " + position[0] + ", Y = " + position[1]);
                nbr++;
            }

            int choix = 0;

            do {
                System.out.print("\nQuel est votre choix ?");
                try {
                    choix = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                }
            } while (choix < 0 || choix >= nbr);

            // Converti son choix en action.
            int[] newPosition = listeMouvementsPossibles.get(choix);
            System.out.println("\nLe joueur '" + joueurActuel.getNom() + "' déplace son pion de la case à : \n    X = " + joueurActuel.getX() + " ->  X = " + newPosition[0] + "\n    Y = " + joueurActuel.getX() + " ->   Y = " + newPosition[1]);

            // Change l'emplacement du pion et l'état de l'ancient emplacement.
            this.plateau.getEmplacement(joueurActuel.getX(), joueurActuel.getY()).setValeur(Val.CASEPION);
            joueurActuel.setPion(this.plateau.getEmplacement(newPosition[0], newPosition[1]));
            this.idJoueurActuel = (this.idJoueurActuel + 1) % this.listeJoueurs.length;

            // Enregistre l'état actuel de la partie.
            sauvegarde();
        }
    }

    public boolean finPartie() {
        for (Joueur j : listeJoueurs) {
            if ((j.getId() == 1 && j.getY() == 16)
                    || (j.getId() == 2 && j.getY() == 0)
                    || (j.getId() == 3 && j.getX() == 0)
                    || (j.getId() == 4 && j.getX() == 16)) {
                Log.info("FinDePartie", j.getNom() + " gagne, x:" + j.getX() + " y:" + j.getY());
                return true;
            }
        }
        return false;
    }
    private void sauvegarde() {
        gestionSauvegardes.enregistrement(this.nomPartie, this.plateau, this.pointsJoueur, this.idJoueurActuel);
    }

    /**
     * Charge une partie si elle existe.
     *
     * @param nomFichier
     * @return
     */
    private boolean chargement(String nomFichier) {

        if (gestionSauvegardes.testSauvegardeExiste(nomFichier)) {
            Object[] data = gestionSauvegardes.chargement(nomFichier);
            this.plateau = (Plateau) data[0];
            this.pointsJoueur = (HashMap) data[1];
            this.idJoueurActuel = (int) data[2];
            Joueur[] listeTemporaire = new Joueur[this.pointsJoueur.keySet().size()];
            for (Joueur joueurSave : this.pointsJoueur.keySet()) {
                listeTemporaire[joueurSave.getId()] = joueurSave;
            }
            this.listeJoueurs = listeTemporaire;
            return true;
        }
        return false;
    }

    public int dijkstra(int x,int y,int idjoueur){
        int i=0;
        ArrayList<String> P=new ArrayList<String>();
        ArrayList<Integer> FileX=new ArrayList<Integer>();
        ArrayList<Integer> FileY=new ArrayList<Integer>();
        FileX.add(x);
        FileY.add(y);
        while (!(FileX.isEmpty())) {
            int x_courant=FileX.get(FileX.size()-1);
            FileX.remove(FileX.size()-1);
            int y_courant=FileY.get(FileY.size()-1);
            FileY.remove(FileY.size()-1);
            if ((idjoueur==1 && y_courant==16) || (idjoueur==2 && y_courant==0)||(idjoueur==3 && x_courant==0)||(idjoueur==4 && x_courant==16)){
                return i;
            }
            i++;
            for (int[]coup:listeMouvementsPion(x_courant,y_courant)){
                if (!P.contains(x_courant+" "+y_courant)) {
                    FileX.add(coup[0]);
                    FileY.add(coup[1]);
                    P.add(x_courant+" "+y_courant);
                }
            }

        }

        return -1;
    }
    private boolean exist_recursif (int x,int y,int idjoueur,ArrayList<String>noeuds_vus){
        if ((idjoueur==1 && y==16) || (idjoueur==2 && y==0)||(idjoueur==3 && x==0)||(idjoueur==4 && x==16)){
            return true;
        }
        noeuds_vus.add(x+" "+y);
        boolean b = false;
        for (int[] coup:listeMouvementsPion(x,y) ) {
            if (noeuds_vus.contains(x+" "+y)){
                b = b||exist_recursif(coup[0], coup[1], idjoueur, noeuds_vus);
            }
        }
        return b;
    }
    public boolean exist_chemin(int x,int y,int idjoueur){
        //parcours en profondeur
        return exist_recursif(x,y,idjoueur,new ArrayList<String>());
    }
}