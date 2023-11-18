package application.vue.pages;


import application.controleur.Plateau;
import application.modele.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private ArrayList<String> listeText;
    private ArrayList<Label> listeLigne;
    private Button couleur;
    private int width;
    private int height;
    public Game(){

        this.width = 1280/2;
        this.height = 960/2;
        this.gestionSauvegardes = new GestionSauvegardes();

        this.listeText = new ArrayList<>();
        this.listeLigne =  new ArrayList<>();
        this.matriceBouton = new ArrayList<>();
        GestionSauvegardes gestionSauvegardes = new GestionSauvegardes();
        this.nomPartie = "test";
        Object[] listeData = gestionSauvegardes.chargement(this.nomPartie);
        this.plateau = (Plateau) listeData[0];
        this.pointsJoueur = (HashMap<Joueur, Integer>) listeData[1];
        this.idJoueurActuel = (int) listeData[2];
        for(Joueur joueur : this.pointsJoueur.keySet()){
            this.liste_joueur.put(joueur.getId(), joueur);
        }
        this.calculs = new Calculs(this.plateau);

        showPlateau();
        showActions();
        showDonneesJoueur();

        startGame();
    }

    private void showDonneesJoueur(){
        VBox boiteDonneesJoueur = new VBox();
        this.donneesJoueur = new Label();
        boiteDonneesJoueur.getChildren().add(this.donneesJoueur);
        boiteDonneesJoueur.setAlignment(Pos.CENTER);
        boiteDonneesJoueur.setLayoutX(0);
        boiteDonneesJoueur.setLayoutY(this.height-100);
        boiteDonneesJoueur.setStyle("-fx-pref-width: " + this.width*2/3 + "; -fx-pref-height: " + 100 + "; -fx-border-width:1; -fx-border-color:#000000");

        this.couleur = new Button();
        this.couleur.setLayoutX(0);
        this.couleur.setLayoutY(0);
        this.couleur.setStyle("-fx-pref-width: " + 50 + "; -fx-pref-height: " + 50 + "; -fx-border-width:1; -fx-border-color:#000000");
        boiteDonneesJoueur.getChildren().add(this.couleur);
        getChildren().add(boiteDonneesJoueur);
    }


    private void showActions(){

        VBox boiteLabel = new VBox();
        Label label = new Label("Historique des coups : ");
        boiteLabel.getChildren().add(label);
        boiteLabel.setAlignment(Pos.CENTER);
        boiteLabel.setLayoutX(this.width*2/3);
        boiteLabel.setLayoutY(0);
        boiteLabel.setStyle("-fx-pref-width: " + this.width/3 + "; -fx-pref-height: " + 50 + "; -fx-border-width:1; -fx-border-color:#000000");

        getChildren().add(boiteLabel);

        this.boiteText = new VBox();
        this.boiteText.setLayoutX(this.width*2/3);
        this.boiteText.setLayoutY(50);
        this.boiteText.setStyle("-fx-pref-width: " + this.width/3 + "; -fx-pref-height: " + (this.height-50) + "; -fx-border-width:1; -fx-border-color:#000000");

        this.boiteText.addEventFilter(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY()>0?1:-1;

            actualiseValeur((int)delta);

            event.consume();
        });

        for(int i =0; i<20; i++){
            Label ligne = new Label();
            this.listeLigne.add(ligne);
            this.boiteText.getChildren().add(ligne);
        }
        getChildren().addAll(this.boiteText);
    }

    private void actualiseValeur(int delta){
        if(this.valeur + delta<this.listeText.size()-this.listeLigne.size()+1 && this.valeur + delta>=0){
            this.valeur += delta;
        }
        if(this.valeur<0){
            this.valeur = 0;
        }
        int y = 0;
        for(Label ligne : this.listeLigne){

            if(this.valeur+y<this.listeText.size()){
                ligne.setText(this.listeText.get(this.valeur+y));
            }
            y++;
        }

    }

    private void writeText(String text){
        this.listeText.add(text);
        actualiseValeur(1);

    }

    private void showPlateau() {

        int sizeW = 30;
        int sizeH = 30;
        int width = sizeW;
        int height = sizeH;
        int positionX = 0;
        int positionY = 0;
        String couleur = "#ffffff";
        for (int y = 0; y < this.plateau.getHeight(); y++) {
            height = sizeH;
            positionX = 0;
            ArrayList<Button> ligneButton = new ArrayList<>();
            for (int x = 0; x < this.plateau.getWidth(); x++) {
                Emplacement emplacement = this.plateau.getEmplacement(x, y);
                String valeur = emplacement.toString();
                width = sizeW;
                int w = width;
                int h = height;
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

                for(int idJoueur : this.liste_joueur.keySet()){
                    Joueur joueur = this.liste_joueur.get(idJoueur);
                    if(joueur.getX()==x && joueur.getY()==y){
                        couleur = joueur.getCouleur();
                    }
                }

                Button buttonNext = new Button();
                int finalX = x;
                int finalY = y;


                buttonNext.setLayoutX(positionX);
                buttonNext.setLayoutY(positionY);
                buttonNext.setStyle("-fx-pref-width: " + w + "; -fx-pref-height: " + h + "; -fx-background-color: " + couleur + "; -fx-border-width:1; -fx-border-color:#000000");
                ligneButton.add(buttonNext);
                positionX += w;

                getChildren().addAll(buttonNext);
            }
            this.matriceBouton.add(ligneButton);
            positionY += y % 2 == 1 ? height / 2 : height;
        }
    }

    private void changeCouleurBouton(Button bouton, String couleur){

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

            
            if(this.idJoueurActuel<1){
                this.idJoueurActuel = 1;
            }
            
            // Informe l'utilisateur de l'état de la partie.
            Joueur joueurActuel = this.liste_joueur.get(this.idJoueurActuel);
            String text = "C'est à '" + joueurActuel.getNom() + "' de jouer.\n"+
                    "Votre pion est situé à : " + joueurActuel.getCoordsString()+".\n"+
                    "Nombre de murs qu'il vous reste : "+joueurActuel.getNbrMurs();
            this.donneesJoueur.setText(text);
            this.couleur.setStyle("-fx-pref-width: " + 50 + "; -fx-pref-height: " + 50 + "; -fx-border-width:1; -fx-border-color:#000000; -fx-background-color:"+joueurActuel.getCouleur());

            // Demande le prochain coup à l'utilisateur.
            List<int[]> listeMouvementsPossibles = this.calculs.listeMouvementsPion(joueurActuel.getX(), joueurActuel.getY());


            System.out.println("\nVoici les coups possible par votre pion :");
            for (int[] position : listeMouvementsPossibles) {
                changeCouleurBouton(this.matriceBouton.get(position[1]).get(position[0]), "#AAFFAA");
                this.matriceBouton.get(position[1]).get(position[0]).setOnAction(event -> {
                    // Utilisation de Platform.runLater pour les modifications d'interface utilisateur
                    Platform.runLater(() -> {
                        // Utilisation de writeText ou de System.out.println ?
                        writeText("\nLe joueur '" + joueurActuel.getNom() +
                                "' pion : \n    X = " + joueurActuel.getX() +
                                " ->  X = " + position[0] + "\n    Y = " +
                                joueurActuel.getX() + " ->   Y = " + position[1]
                        );

                        this.plateau.getEmplacement(joueurActuel.getX(), joueurActuel.getY()).setValeur(Val.CASEPION);

                        changeCouleurBouton(this.matriceBouton.get(joueurActuel.getY()).get(joueurActuel.getX()), "#FFFFFF");
                        joueurActuel.setPion(this.plateau.getEmplacement(position[0], position[1]));


//                        sauvegarde();
                        for (int[] post : listeMouvementsPossibles) {
                            if(post[0] == position[0] && post[1] == position[1]){
                                changeCouleurBouton(this.matriceBouton.get(post[1]).get(post[0]), joueurActuel.getCouleur());
                            }else{
                                changeCouleurBouton(this.matriceBouton.get(post[1]).get(post[0]), "#FFFFFF");
                            }
                            this.matriceBouton.get(post[1]).get(post[0]).setOnAction(null);
                        }
                        if(!finPartie()) {
                            startGame();
                        }
                    });
                });
            }

    }

    public boolean finPartie() {

        Joueur joueur = this.liste_joueur.get(this.idJoueurActuel);
        boolean val = false;
        if (this.listeLigneWin[this.idJoueurActuel-1][0] == 0 ?
                joueur.getX() == this.listeLigneWin[this.idJoueurActuel-1][1] : joueur.getY() == this.listeLigneWin[this.idJoueurActuel-1][1]
        ) {
            Log.info("FinDePartie", joueur.getNom() + " gagne, x:" + joueur.getX() + " y:" + joueur.getY());
            System.out.println(joueur.getNom() + " gagne, x:" + joueur.getX() + " y:" + joueur.getY());
            this.pointsJoueur.put(joueur, this.pointsJoueur.get(joueur) + 1);

            val= true;
        }

        this.idJoueurActuel = (this.idJoueurActuel + 1) % (this.liste_joueur.size()+1);
        if(this.idJoueurActuel<1){
            this.idJoueurActuel = 1;
        }
        return val;
    }
    private void sauvegarde() {
        gestionSauvegardes.enregistrement(this.nomPartie, this.plateau, this.pointsJoueur, this.idJoueurActuel);
    }
}
