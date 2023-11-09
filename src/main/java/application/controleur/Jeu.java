/**
 * Classe Jeu écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.controleur;

import application.modele.*;

import java.util.*;

/**
 * La classe Jeu permet de jouer sur le terminal le jeu du Quoridor.
 */
public class Jeu {

    private Calculs calculs;
    private Plateau plateau;
    private String nomPartie;
    private Joueur[] listeJoueurs;
    private int idJoueurActuel;
    private HashMap<Joueur, Integer> pointsJoueur;
    private Integer[][] emplacementJoueur;
    private Scanner scanner;
    private GestionSauvegardes gestionSauvegardes;
    private final int[][] listeLigneWin = new int[][]{{1, 0}, {1, 16}, {0, 0}, {0, 16}};

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

        int idJoueur = 1;

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
        this.calculs = new Calculs(this.plateau);
    }

    public String getNomPartie() {
        return nomPartie;
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
            List<int[]> listeMouvementsPossibles = this.calculs.listeMouvementsPion(joueurActuel.getX(), joueurActuel.getY());

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
            System.out.println(
                    "\nLe joueur '" + joueurActuel.getNom() +
                            "' déplace son pion de la case à : \n    X = " + joueurActuel.getX() +
                            " ->  X = " + newPosition[0] + "\n    Y = " +
                            joueurActuel.getX() + " ->   Y = " + newPosition[1]
            );

            // Change l'emplacement du pion et l'état de l'ancient emplacement.
            this.plateau.getEmplacement(joueurActuel.getX(), joueurActuel.getY()).setValeur(Val.CASEPION);
            joueurActuel.setPion(this.plateau.getEmplacement(newPosition[0], newPosition[1]));

            this.idJoueurActuel = (this.idJoueurActuel + 1) % this.listeJoueurs.length;
            // Enregistre l'état actuel de la partie.
            sauvegarde();
        }
    }

    public boolean finPartie() {
        int idJoueur = Math.abs(this.idJoueurActuel - 1) % this.listeJoueurs.length;

        Joueur joueur = this.listeJoueurs[idJoueur];

        if (this.listeLigneWin[idJoueur][0] == 0 ?
                joueur.getX() == this.listeLigneWin[idJoueur][1] : joueur.getY() == this.listeLigneWin[idJoueur][1]
        ) {
            Log.info("FinDePartie", joueur.getNom() + " gagne, x:" + joueur.getX() + " y:" + joueur.getY());
            System.out.println(joueur.getNom() + " gagne, x:" + joueur.getX() + " y:" + joueur.getY());
            this.pointsJoueur.put(joueur, this.pointsJoueur.get(joueur) + 1);
            return true;
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
}