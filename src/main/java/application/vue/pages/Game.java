package application.vue.pages;


import application.controleur.Plateau;
import application.modele.Calculs;
import application.modele.Emplacement;
import application.modele.GestionSauvegardes;
import application.modele.Joueur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game extends Parent {

    private Calculs calculs;
    private Plateau plateau;
    private String nomPartie;
    private Joueur[] listeJoueurs;
    private int idJoueurActuel;
    private HashMap<Joueur, Integer> pointsJoueur;
    private Integer[][] emplacementJoueur;
    private int valeur = 0;
    private GestionSauvegardes gestionSauvegardes;
    private final int[][] listeLigneWin = new int[][]{{1, 0}, {1, 16}, {0, 0}, {0, 16}};
    private VBox boiteText;

    private ArrayList<String> listeText;
    private ArrayList<Label> listeLigne;
    public Game(){
        this.listeText = new ArrayList<>();
        this.listeLigne =  new ArrayList<>();
        GestionSauvegardes gestionSauvegardes = new GestionSauvegardes();
        this.nomPartie = "test";
        Object[] listeData = gestionSauvegardes.chargement(this.nomPartie);
        this.plateau = (Plateau) listeData[0];
        this.pointsJoueur = (HashMap<Joueur, Integer>) listeData[1];
        this.idJoueurActuel = (int) listeData[2];
        this.listeJoueurs = new Joueur[4];
        int nbr = 0;
        for(Joueur joueur : this.pointsJoueur.keySet()){
            listeJoueurs[nbr] = joueur;
            nbr++;
        }
        this.calculs = new Calculs(this.plateau);
        showPlateau();
        showActions();


    }

    private void showActions(){

        int width = 1280/2;
        int height = 960/2;

        VBox boiteLabel = new VBox();
        Label label = new Label("Historique des coups : ");
        boiteLabel.getChildren().add(label);
        boiteLabel.setAlignment(Pos.CENTER);
        boiteLabel.setLayoutX(width*2/3);
        boiteLabel.setLayoutY(0);
        boiteLabel.setStyle("-fx-pref-width: " + width/3 + "; -fx-pref-height: " + 50 + "; -fx-border-width:1; -fx-border-color:#000000");

        getChildren().add(boiteLabel);

        this.boiteText = new VBox();
        this.boiteText.setLayoutX(width*2/3);
        this.boiteText.setLayoutY(50);
        this.boiteText.setStyle("-fx-pref-width: " + width/3 + "; -fx-pref-height: " + (height-50) + "; -fx-border-width:1; -fx-border-color:#000000");

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

                Button buttonNext = new Button();
                int finalX = x;
                int finalY = y;

                buttonNext.setOnAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        writeText(emplacement.toString() + " X : " + finalX + ", Y : " + finalY);


                    }
                });
                buttonNext.setLayoutX(positionX);
                buttonNext.setLayoutY(positionY);
                buttonNext.setStyle("-fx-pref-width: " + w + "; -fx-pref-height: " + h + "; -fx-background-color: " + couleur + "; -fx-border-width:1; -fx-border-color:#000000");

                positionX += w;

                getChildren().addAll(buttonNext);
            }

            positionY += y % 2 == 1 ? height / 2 : height;
        }

    }

    }
