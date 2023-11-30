/**
 * Classe Game écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.vue.pages;


import application.controleur.Plateau;
import application.controleur.vue.GameController;
import application.controleur.vue.LoadGameController;
import application.modele.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * La classe Game regroupe l'ensemble du code pour le visuel du jeu.
 */

public class Game extends Parent {

    private Calculs calculs;
    private Plateau plateau;
    private String nomPartie;
    private HashMap<Integer, Joueur> liste_joueur = new HashMap<>();
    private int idJoueurActuel;
    private ArrayList<ArrayList<Button>> matriceBouton;
    private Label donneesJoueur;
    private HashMap<Joueur, Integer> pointsJoueur;
    private int valeur = 0;
    private GestionSauvegardes gestionSauvegardes;
    private final int[][] listeLigneWin = new int[][]{{1, 0}, {1, 16}, {0, 0}, {0, 16}};
    private VBox boiteText;
    private boolean val = true;
    private ArrayList<String> listeText;
    private ArrayList<Label> listeLigne;
    private Button couleur;
    private int width;
    private int height;
    private int[] coordsMurs2 = {0, 0};
    private int[] coordsCentre = {0, 0};

    private GameController controller;

    /**
     * Initialise le jeu.
     * @param scene Scene
     * @param nomGame String
     * @param listeData Object[]
     */
    public Game(Scene scene, String nomGame, Object[] listeData) {

        this.width = (int) scene.getWidth();
        this.height = (int) scene.getHeight();
        this.gestionSauvegardes = new GestionSauvegardes();
        this.controller = (new GameController(scene));

        this.listeText = new ArrayList<>();
        this.listeLigne = new ArrayList<>();
        this.matriceBouton = new ArrayList<>();
        this.nomPartie = nomGame;
        this.plateau = (Plateau) listeData[0];
        this.pointsJoueur = (HashMap<Joueur, Integer>) listeData[1];
        this.idJoueurActuel = (int) listeData[2];
        for (Joueur joueur : this.pointsJoueur.keySet()) {
            this.liste_joueur.put(joueur.getId(), joueur);
        }
        this.calculs = new Calculs(this.plateau);

        showPlateau();
        showActions();
        showDonneesJoueur();
        Button backButton = createBackButton();

        getChildren().add(backButton);
        startGame();
    }

    /**
     * Affiche les informations du joueur actuel.
     */
    private void showDonneesJoueur() {
        VBox boiteDonneesJoueur = new VBox();
        this.donneesJoueur = new Label();
        boiteDonneesJoueur.getChildren().add(this.donneesJoueur);
        boiteDonneesJoueur.setAlignment(Pos.CENTER);
        boiteDonneesJoueur.setLayoutX(0);
        boiteDonneesJoueur.setLayoutY(this.height - 80);
        boiteDonneesJoueur.setStyle("-fx-pref-width: " + this.width * 2 / 3 + "; -fx-pref-height: " + 80 + "; -fx-border-width:1; -fx-border-color:#000000");

        this.couleur = new Button();
        this.couleur.setLayoutX(0);
        this.couleur.setLayoutY(this.height - 80);
        this.couleur.setStyle("-fx-pref-width: " + 50 + "; -fx-pref-height: " + 50 + "; -fx-border-width:1; -fx-border-color:#000000");
        getChildren().add(this.couleur);
        getChildren().add(boiteDonneesJoueur);
    }


    /**
     * Affiche l'historique des actions des joueurs.
     */
    private void showActions() {

        VBox boiteLabel = new VBox();
        Label label = new Label("Historique des coups : ");
        boiteLabel.getChildren().add(label);
        boiteLabel.setAlignment(Pos.CENTER);
        boiteLabel.setLayoutX(this.width * 2 / 3);
        boiteLabel.setLayoutY(0);
        boiteLabel.setStyle("-fx-pref-width: " + this.width / 3 + "; -fx-pref-height: " + 50 + "; -fx-border-width:1; -fx-border-color:#000000");

        getChildren().add(boiteLabel);

        this.boiteText = new VBox();
        this.boiteText.setLayoutX(this.width * 2 / 3);
        this.boiteText.setLayoutY(50);
        this.boiteText.setStyle("-fx-pref-width: " + this.width / 3 + "; -fx-pref-height: " + (this.height - 50) + "; -fx-border-width:1; -fx-border-color:#000000");

        this.boiteText.addEventFilter(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY() > 0 ? 1 : -1;

            actualiseValeur((int) delta);

            event.consume();
        });

        for (int i = 0; i < 20; i++) {
            Label ligne = new Label();
            this.listeLigne.add(ligne);
            this.boiteText.getChildren().add(ligne);
        }
        getChildren().addAll(this.boiteText);
    }

    /**
     * Permet de scroll de haut en bas les historiques des actions.
     * @param delta int, valeur de la molette de la sourie.
     */
    private void actualiseValeur(int delta) {
        if (this.valeur + delta < this.listeText.size() - this.listeLigne.size() + 1 && this.valeur + delta >= 0) {
            this.valeur += delta;
        }
        if (this.valeur < 0) {
            this.valeur = 0;
        }
        int y = 0;
        for (Label ligne : this.listeLigne) {

            if (this.valeur + y < this.listeText.size()) {
                ligne.setText(this.listeText.get(this.valeur + y));
            }
            y++;
        }
    }

    /**
     * Ajoute un texte dans l'historique.
     * @param text String
     */
    private void writeText(String text) {
        this.listeText.add(text);
        actualiseValeur(1);

    }

    /**
     * Affiche le plateau.
     */
    private void showPlateau() {

        int sizeW = (this.width * 2 / 3) / 14;
        int sizeH = sizeW;
        int width = sizeW;
        int height = sizeH;
        int positionX = 30;
        int positionY = 0;
        String couleur = "#ffffff";
        for (int y = 0; y < this.plateau.getHeight(); y++) {
            height = sizeH;
            positionX = 30;
            ArrayList<Button> ligneButton = new ArrayList<>();
            for (int x = 0; x < this.plateau.getWidth(); x++) {
                Emplacement emplacement = this.plateau.getEmplacement(x, y);
                String valeur = emplacement.toString();
                width = sizeW;
                int w = width;
                int h = height;
                Button buttonNext = new Button();

                // Change la couleur, la taille et les fonctions des cases du plateau.
                switch (valeur) {
                    case "CASEPION":
                        couleur = "#FFFFFF";
                        break;
                    case "__PION__":
                        couleur = "#0000ff";
                        break;

                    case "CASEMURS":
                        w = x % 2 == 1 ? width / 2 : width;
                        h = y % 2 == 1 ? height / 2 : height;
                        couleur = "#AAAAAA";
                        int finalX = x;
                        int finalY = y;

                        // Change de couleur si le joueur peut pauser un mur.
                        buttonNext.setOnMouseEntered(event -> {
                            Platform.runLater(() -> {
                                Joueur joueurActuel = this.liste_joueur.get(this.idJoueurActuel);
                                if (joueurActuel.getNbrMurs() > 0) {

                                    Emplacement emplacementMurs1 = this.plateau.getEmplacement(finalX, finalY);
                                    Emplacement emplacementMurs2 = null;
                                    int xx = finalX;
                                    int yy = finalY;
                                    if (finalY % 2 == 1) {
                                        emplacementMurs2 = this.plateau.getEmplacement(finalX + 2, finalY);
                                        if (emplacementMurs2 == null) {
                                            emplacementMurs2 = this.plateau.getEmplacement(finalX - 2, finalY);
                                            xx = finalX - 2;
                                        } else {
                                            xx = finalX + 2;
                                        }
                                    } else if (finalX % 2 == 1) {
                                        emplacementMurs2 = this.plateau.getEmplacement(finalX, finalY + 2);
                                        if (emplacementMurs2 == null) {
                                            emplacementMurs2 = this.plateau.getEmplacement(finalX, finalY - 2);
                                            yy = finalY - 2;
                                        } else {
                                            yy = finalY + 2;
                                        }
                                    }

                                    int centreX = 0;
                                    int centreY = 0;

                                    if (finalX - xx != 0) {
                                        centreX = finalX - xx > 1 ? finalX - 1 : finalX + 1;
                                    } else {
                                        centreX = finalX;
                                    }
                                    if (finalY - yy != 0) {
                                        centreY = finalY - yy > 1 ? finalY - 1 : finalY + 1;
                                    } else {
                                        centreY = finalY;
                                    }

                                    // Vérifie si les cases occupées par un future mur sont disponibles.
                                    if (emplacementMurs2 != null && this.plateau.getEmplacement(centreX, centreY).getValeur() != Val._OCCUPE_) {

                                        if (emplacementMurs1.getValeur() == Val.CASEMURS && emplacementMurs2.getValeur() == Val.CASEMURS) {
                                            emplacementMurs1.setValeur(Val.__MURS__);
                                            emplacementMurs2.setValeur(Val.__MURS__);

                                            this.val = true;
                                            for (Joueur joueur : this.liste_joueur.values()) {
                                                if (!this.calculs.exist_chemin(joueur.getX(), joueur.getY(), joueur.getId())) {
                                                    this.val = false;
                                                }
                                            }
                                            if (this.val) {
                                                changeCouleurBouton(buttonNext, "#FF9900");
                                                changeCouleurBouton(this.matriceBouton.get(yy).get(xx), "#FF9900");
                                                this.coordsMurs2[0] = xx;
                                                this.coordsMurs2[1] = yy;
                                                this.coordsCentre[0] = centreX;
                                                this.coordsCentre[1] = centreY;

                                            } else {
                                                changeCouleurBouton(buttonNext, "#AAAAAA");
                                                changeCouleurBouton(this.matriceBouton.get(yy).get(xx), "#AAAAAA");
                                                emplacementMurs1.setValeur(Val.CASEMURS);
                                                emplacementMurs2.setValeur(Val.CASEMURS);
                                            }
                                        }
                                    }
                                }
                            });
                        });

                        // Efface la couleur si la sourie quitte le bouton.
                        buttonNext.setOnMouseExited(event -> {
                            Platform.runLater(() -> {
                                Emplacement emplacementMurs1 = this.plateau.getEmplacement(finalX, finalY);

                                emplacementMurs1.setValeur(Val.CASEMURS);
                                changeCouleurBouton(buttonNext, "#AAAAAA");
                                if (this.coordsMurs2[0] != 0 && this.coordsMurs2[1] != 0) {
                                    this.plateau.getEmplacement(this.coordsMurs2[0], this.coordsMurs2[1]).setValeur(Val.CASEMURS);
                                    changeCouleurBouton(this.matriceBouton.get(this.coordsMurs2[1]).get(this.coordsMurs2[0]), "#AAAAAA");
                                }
                                this.coordsMurs2[0] = 0;
                                this.coordsMurs2[1] = 0;
                                this.coordsCentre[0] = 0;
                                this.coordsCentre[1] = 0;
                            });
                        });

                        // Met en place le mur.
                        buttonNext.setOnAction(event -> {
                            Platform.runLater(() -> {
                                if (!(this.coordsMurs2[0] == 0 && this.coordsMurs2[1] == 0)) {
                                    Emplacement caseGauche = this.plateau.getEmplacement(finalX, finalY);
                                    Emplacement caseMilieu = this.plateau.getEmplacement(this.coordsCentre[0], this.coordsCentre[1]);
                                    Emplacement caseDroite = this.plateau.getEmplacement(this.coordsMurs2[0], this.coordsMurs2[1]);
                                    this.liste_joueur.get(idJoueurActuel).setMur(caseGauche, caseMilieu, caseDroite);
                                    changeCouleurBouton(buttonNext, "#FF0000");
                                    changeCouleurBouton(this.matriceBouton.get(this.coordsMurs2[1]).get(this.coordsMurs2[0]), "#FF0000");
                                    buttonNext.setOnMouseExited(null);
                                    this.matriceBouton.get(this.coordsMurs2[1]).get(this.coordsMurs2[0]).setOnMouseExited(null);
                                    Joueur joueurActuel = this.liste_joueur.get(this.idJoueurActuel);
                                    String text = "C'est à '" + joueurActuel.getNom() + "' de jouer.\n" +
                                            "Votre pion est situé à : " + joueurActuel.getCoordsString() + ".\n" +
                                            "Nombre de murs qu'il vous reste : " + joueurActuel.getNbrMurs();
                                    this.donneesJoueur.setText(text);
                                    writeText(joueurActuel.getNom() + ", Murs en " + finalX + ", " + finalY);
                                    for (int i = 0; i < this.matriceBouton.size(); i++) {
                                        for (int j = 0; j < this.matriceBouton.get(0).size(); j++) {
                                            if (this.plateau.getEmplacement(j, i).getValeur() == Val.CASEPION) {
                                                changeCouleurBouton(this.matriceBouton.get(i).get(j), "#FFFFFF");
                                                this.matriceBouton.get(i).get(j).setOnAction(null);
                                            }
                                        }
                                    }

                                    if (!finPartie()) {
                                        this.idJoueurActuel = (this.idJoueurActuel + 1) % (this.liste_joueur.size() + 1);
                                        if (this.idJoueurActuel < 1) {
                                            this.idJoueurActuel = 1;
                                        }
                                        startGame();
                                    }
                                }
                                this.coordsMurs2[0] = 0;
                                this.coordsMurs2[1] = 0;
                                this.coordsCentre[0] = 0;
                                this.coordsCentre[1] = 0;
                            });
                        });
                        break;
                    case "__MURS__":
                        w = x % 2 == 1 ? width / 2 : width;
                        h = y % 2 == 1 ? height / 2 : height;
                        couleur = "#FF0000";
                        break;
                    case "__VIDE__":
                        w = width / 2;
                        h = height / 2;
                        couleur = "#000000";
                        break;
                    case "_OCCUPE_":
                        couleur = "#000000";
                        w = width / 2;
                        h = height / 2;
                        break;
                    default:
                        couleur = "#ffffff";
                        break;
                }

                for (int idJoueur : this.liste_joueur.keySet()) {
                    Joueur joueur = this.liste_joueur.get(idJoueur);
                    if (joueur.getX() == x && joueur.getY() == y) {
                        couleur = joueur.getCouleur();
                    }
                }

                // Affecte les boutons au plateau.
                buttonNext.setLayoutX(positionX);
                buttonNext.setLayoutY(positionY);
                buttonNext.setStyle("-fx-pref-width: " + w + "; -fx-pref-height: " + h + "; -fx-background-color: " + couleur + "; -fx-border-width:1; -fx-border-color:#000000");
                ligneButton.add(buttonNext);
                positionX += w;

                getChildren().add(buttonNext);
            }
            this.matriceBouton.add(ligneButton);
            positionY += y % 2 == 1 ? height / 2 : height;
        }
    }

    /**
     * Change le style du bouton d'entrée.
     * @param bouton Button
     * @param couleur String
     */
    private void changeCouleurBouton(Button bouton, String couleur) {

        String styleActuel = bouton.getStyle();

        int debutCouleurFond = styleActuel.indexOf("-fx-background-color:");
        int finCouleurFond = styleActuel.indexOf(";", debutCouleurFond);

        String nouveauStyle = styleActuel.substring(0, debutCouleurFond) +
                "-fx-background-color: " + couleur +
                styleActuel.substring(finCouleurFond);
        bouton.setStyle(nouveauStyle);
    }

    /**
     * Démarrage du jeu.
     *
     * @return
     */
    public void startGame() {


        if (this.idJoueurActuel < 1) {
            this.idJoueurActuel = 1;
        }

        // Informe l'utilisateur de l'état de la partie.
        Joueur joueurActuel = this.liste_joueur.get(this.idJoueurActuel);
        String text = "C'est à '" + joueurActuel.getNom() + "' de jouer.\n" +
                "Votre pion est situé à : " + joueurActuel.getCoordsString() + ".\n" +
                "Nombre de murs qu'il vous reste : " + joueurActuel.getNbrMurs();
        this.donneesJoueur.setText(text);
        this.couleur.setStyle("-fx-pref-width: " + 50 + "; -fx-pref-height: " + 50 + "; -fx-border-width:1; -fx-border-color:#000000; -fx-background-color:" + joueurActuel.getCouleur());

        // Si le joueur est une IA, elle joue et actualise le plateau.
        if (joueurActuel.getNom().contains("IA")) {
            calculs.use_min_max(liste_joueur, joueurActuel.getId(), 2*Integer.parseInt(joueurActuel.getNom().split(" ")[1]));

            this.matriceBouton.clear();
            showPlateau();
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            pause.setOnFinished(event -> {
                if (!finPartie()) {
                    // Passe au joueur suivant.
                    this.idJoueurActuel = (this.idJoueurActuel + 1) % (this.liste_joueur.size() + 1);
                    if (this.idJoueurActuel < 1) {
                        this.idJoueurActuel = 1;
                    }
                    sauvegarde();
                    startGame();
                }else{
                    pageFinPartie();
                }

            });
            pause.play();


        } else {
            // Demande le prochain coup à l'utilisateur.
            ArrayList<Integer[]> listeMouvementsPossibles = this.calculs.listeMouvementsPion(joueurActuel.getX(), joueurActuel.getY(), joueurActuel.getId());

            this.calculs.exist_chemin(joueurActuel.getX(), joueurActuel.getX(), this.idJoueurActuel);
            // Change les cases pions vide pour montrer à l'utilisateur les coups qu'il peut jouer.
            for (Integer[] position : listeMouvementsPossibles) {
                changeCouleurBouton(this.matriceBouton.get(position[1]).get(position[0]), "#AAFFAA");
                this.matriceBouton.get(position[1]).get(position[0]).setOnAction(event -> {
                    Platform.runLater(() -> {
                        writeText(joueurActuel.getNom() + ", pion : " + position[0] + ", " + position[1]);
                        this.plateau.getEmplacement(joueurActuel.getX(), joueurActuel.getY()).setValeur(Val.CASEPION);

                        changeCouleurBouton(this.matriceBouton.get(joueurActuel.getY()).get(joueurActuel.getX()), "#FFFFFF");
                        joueurActuel.setPion(this.plateau.getEmplacement(position[0], position[1]));

                        for (Integer[] post : listeMouvementsPossibles) {
                            if (post[0] == position[0] && post[1] == position[1]) {
                                changeCouleurBouton(this.matriceBouton.get(post[1]).get(post[0]), joueurActuel.getCouleur());
                            } else {
                                changeCouleurBouton(this.matriceBouton.get(post[1]).get(post[0]), "#FFFFFF");
                            }
                            this.matriceBouton.get(post[1]).get(post[0]).setOnAction(null);
                        }
                        if (!finPartie()) {
                            // Passe au joueur suivant.
                            this.idJoueurActuel = (this.idJoueurActuel + 1) % (this.liste_joueur.size() + 1);
                            if (this.idJoueurActuel < 1) {
                                this.idJoueurActuel = 1;
                            }
                            sauvegarde();
                            startGame();
                        }else{
                            pageFinPartie();
                        }

                    });
                });
            }
        }
    }

    /**
     * Vérifie les conditions de fin d'une partie.
     * @return boolean
     */
    public boolean finPartie() {

        Joueur joueur = this.liste_joueur.get(this.idJoueurActuel);
        boolean val = false;

        // Vérifie les conditions de fin.
        if (this.listeLigneWin[this.idJoueurActuel - 1][0] == 0 ?
                joueur.getX() == this.listeLigneWin[this.idJoueurActuel - 1][1] : joueur.getY() == this.listeLigneWin[this.idJoueurActuel - 1][1]
        ) {
            Log.info("FinDePartie", joueur.getNom() + " gagne, x:" + joueur.getX() + " y:" + joueur.getY());
            System.out.println(joueur.getNom() + " gagne, x:" + joueur.getX() + " y:" + joueur.getY());
            this.pointsJoueur.put(joueur, this.pointsJoueur.get(joueur) + 1);

            // Réinitialise les cases murs.
            val = true;
            for (int i = 0; i < this.matriceBouton.size(); i++) {
                for (int j = 0; j < this.matriceBouton.get(0).size(); j++) {
                    if (this.plateau.getEmplacement(j, i).getValeur() == Val.CASEMURS) {
                        changeCouleurBouton(this.matriceBouton.get(i).get(j), "#FFFFFF");
                        this.matriceBouton.get(i).get(j).setOnAction(null);
                        this.matriceBouton.get(i).get(j).setOnMouseExited(null);
                        this.matriceBouton.get(i).get(j).setOnMouseEntered(null);
                    }
                }
            }
        }

        return val;
    }

    /**
     * Affiche la page de fin de partie.
     */
    private void pageFinPartie(){
        VBox page = new VBox();
        Label text = new Label(this.liste_joueur.get(this.idJoueurActuel).getNom()+" a gagné !!! \n Félicitation !!!");

        Button backButton = new Button("Quitter");
        backButton.setFont(Font.font("Arial", 14));

        page.setAlignment(Pos.CENTER);

        backButton.setOnAction(e -> {
            //code un retour à la premiere page (home)
            // Chemin du fichier à supprimer
            String cheminFichier = "src/main/ressources/sauvegardes/" + this.nomPartie + ".save";
            Path path = Paths.get(cheminFichier);

            try {
                // Supprimer le fichier
                Files.deleteIfExists(path);
                // Actualiser la vue en reconstruisant la liste des sauvegardes
                getChildren().clear();  // Supprimer tous les éléments actuels
            } catch (Exception ee) {
                Log.error("Game","Erreur lors de la suppression du fichier");
            }
            controller.goToHome();
        });
        backButton.setStyle("-fx-cursor: hand;-fx-pref-width: 100; -fx-pref-height: 50; -fx-background-color: #FFFFFF; -fx-border-width:1; -fx-border-color:#000000");
        page.setLayoutX(this.width/6);
        page.setLayoutY(this.height/6);
        page.setStyle("-fx-pref-width: "+this.width*4/6+"; -fx-pref-height: "+this.height*4/6+"; -fx-background-color: #FFFFFF; -fx-border-width:1; -fx-border-color:#000000");

        page.getChildren().addAll(text, backButton);
        getChildren().add(page);
    }

    /**
     * Enregistre la partie actuelle.
     */
    private void sauvegarde() {
        gestionSauvegardes.enregistrement(this.nomPartie, this.plateau, this.pointsJoueur, this.idJoueurActuel);
    }

    /**
     * Gère le bouton de retour vers le menu principal.
     * @return Button
     */
    public Button createBackButton() {
        Button backButton = new Button();
        backButton.setFont(Font.font("Arial", 14));

        // Créer une flèche pointant vers la gauche avec un Polygon
        Polygon arrow = new Polygon(10, 0, 0, 5, 10, 10);
        arrow.setStyle("-fx-fill: #000000;"); // Couleur de la flèche

        // Créer une HBox pour contenir la flèche et aligner au centre
        HBox hbox = new HBox(arrow);
        hbox.setAlignment(Pos.CENTER);

        // Ajouter la HBox (avec la flèche centrée) comme contenu graphique du bouton
        backButton.setGraphic(hbox);

        backButton.setOnAction(e -> {
            //code un retour à la premiere page (home)
            controller.goToHome();
        });
        backButton.setStyle("-fx-cursor: hand");

        return backButton;
    }
}

