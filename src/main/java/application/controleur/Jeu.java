package application.controleur;

import application.modele.Emplacement;
import application.modele.GestionSauvegardes;
import application.modele.Joueur;
import application.modele.Val;

import java.util.*;

public class Jeu {

    private Plateau plateau;
    private Joueur[] listeJoueurs;
    private int idJoueurActuel;
    private HashMap<Joueur, Integer> pointsJoueur;
    private Integer[][] emplacementJoueur;
    private Scanner scanner;
    private GestionSauvegardes gestionSauvegardes;

    public Jeu(int taillePlateauWidth, int taillePlateauHeight, Joueur[] listeJoueur) {
        gestionSauvegardes = new GestionSauvegardes();
        this.plateau = new Plateau(taillePlateauWidth, taillePlateauHeight);
        this.listeJoueurs = listeJoueur;
        this.pointsJoueur = new HashMap<>();
        this.emplacementJoueur = new Integer[][]{
                {(taillePlateauWidth) / 2, taillePlateauHeight - 1},
                {(taillePlateauWidth) / 2, 0},
                {taillePlateauWidth - 1, (taillePlateauHeight) / 2},
                {0, (taillePlateauHeight) / 2},
        };

        initialisation();
        this.scanner = new Scanner(System.in);
    }

    public Jeu(String nomSauvegarde) {
        gestionSauvegardes = new GestionSauvegardes();
        chargement(nomSauvegarde);
        this.scanner = new Scanner(System.in);
    }

    private void initialisation() {
        int position = 0;
        this.idJoueurActuel = 0;
        for (Joueur joueur : this.listeJoueurs) {
            joueur.setPion(this.plateau.getEmplacementCasePion(this.emplacementJoueur[position][0], this.emplacementJoueur[position][1]));
            this.pointsJoueur.put(joueur, 0);
            position++;
        }
    }

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

    private void consitionAjoutPossibilite(List<int[]> possibilites, int x, int y){
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

    public void start() {

        while (!finPartie()) {

            System.out.println(this.plateau.toString(false));
            Joueur joueurActuel = getJoueurActuel();
            System.out.println("C'est à '" + joueurActuel.getNom() + "' de jouer.");
            System.out.println("Votre pion est situé à : " + joueurActuel.getCoordsString());
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

            int[] newPosition = listeMouvementsPossibles.get(choix);

            System.out.println("\nLe joueur '" + joueurActuel.getNom() + "' déplace son pion de la case à : \n    X = " + joueurActuel.getX() + " ->  X = " + newPosition[0] + "\n    Y = " + joueurActuel.getX() + " ->   Y = " + newPosition[1]);
            this.plateau.getEmplacement(joueurActuel.getX(), joueurActuel.getY()).setValeur(Val.CASEPION);
            joueurActuel.setPion(this.plateau.getEmplacement(newPosition[0], newPosition[1]));
            this.idJoueurActuel = (this.idJoueurActuel + 1) % this.listeJoueurs.length;
        }
    }

    public boolean finPartie() {
        return false;
    }

    public void sauvegarde(String nomFichier) {
        gestionSauvegardes.enregistrement(nomFichier, this.plateau, this.pointsJoueur, this.idJoueurActuel);
    }

    private void chargement(String nomFichier) {

        Object[] data = gestionSauvegardes.chargement(nomFichier);
        this.plateau = (Plateau) data[0];
        this.pointsJoueur = (HashMap) data[1];
        this.idJoueurActuel = (int) data[2];
        Joueur[] listeTemporaire = new Joueur[this.pointsJoueur.keySet().size()];
        for (Joueur joueurSave : this.pointsJoueur.keySet()) {
            listeTemporaire[joueurSave.getId()] = joueurSave;
        }
        this.listeJoueurs = listeTemporaire;
    }
}