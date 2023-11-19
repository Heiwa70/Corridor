package application.vue.pages;

import application.controleur.Plateau;
import application.controleur.vue.LoadGameController;
import application.controleur.vue.NewGameController;
import application.modele.Emplacement;
import application.modele.GestionSauvegardes;
import application.modele.Val;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class LoadGame extends Parent {

    private int width;
    private int height;
    private LoadGameController controller;

    public LoadGame(Scene scene) {

        this.width = (int)scene.getWidth();
        this.height = (int)scene.getHeight();

        this.controller = (new LoadGameController(scene));
        listeSauvegardes();
    }

    public void listeSauvegardes() {
        String cheminDossier = "src//main//ressources//sauvegardes//";

        File dossier = new File(cheminDossier);
        File[] fichiers = new File[0];

        if (dossier.isDirectory()) {
            fichiers = dossier.listFiles();
        }
        if (fichiers.length > 0) {
            String couleur = "#ffffff";

            int nbrSaveUneLigne = 3;
            int pasCol = this.width / (nbrSaveUneLigne * 2 + 1);
            int pasLigne = pasCol;
            int x = pasCol;
            int y = pasLigne;
            VBox menuHaut = new VBox();
            VBox text = new VBox();
            VBox bouton = new VBox();

            menuHaut.setLayoutX(0);
            menuHaut.setLayoutY(0);
            menuHaut.setStyle("-fx-pref-width: " + this.width + "; -fx-pref-height: " + this.height / 10 + "; -fx-background-color: " + couleur + "; -fx-border-width:1; -fx-border-color:#000000");
            Label nomMenuHaut = new Label("Liste des sauvegardes : ");

            text.getChildren().add(nomMenuHaut);
            text.setAlignment(Pos.CENTER);
            menuHaut.getChildren().add(text);


            Button backButton = createBackButton();

            menuHaut.getChildren().add(backButton);

            VBox menuBas = new VBox();
            text = new VBox();
            bouton = new VBox();
            menuBas.setLayoutX(0);
            menuBas.setLayoutY(this.height * 4 / 5);
            menuBas.setStyle("-fx-pref-width: " + this.width + "; -fx-pref-height: " + this.height / 5 + "; -fx-background-color: " + couleur + "; -fx-border-width:1; -fx-border-color:#000000");
            Label nomMenuBas = new Label("Nom de la sauvegarde chargé : ");
            text.getChildren().add(nomMenuBas);
            text.setAlignment(Pos.CENTER);
            menuBas.getChildren().add(text);

            Button buttonBas = new Button("Charger");
            buttonBas.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    System.out.println("Charger partie.");
                }
            });
            bouton.getChildren().add(buttonBas);
            bouton.setAlignment(Pos.CENTER_RIGHT);
            menuBas.getChildren().add(bouton);


            int nbr = 0;
            for (File fichier : fichiers) {
                if(nbr>5){
                    continue;
                }
                nbr++;
                String nomFichier = fichier.getName().split("\\.")[0];

                VBox vBox = new VBox();
                Label nomSauvegarde = new Label(nomFichier);

                vBox.getChildren().addAll(nomSauvegarde);
                vBox.setAlignment(Pos.CENTER);
                vBox.setLayoutX(x);
                vBox.setLayoutY(y);
                vBox.setStyle("-fx-pref-width: " + pasCol + "; -fx-pref-height: " + pasLigne + "; -fx-background-color: " + couleur + "; -fx-border-width:1; -fx-border-color:#000000");
                Button button = new Button("Selectionner");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        nomMenuBas.setText("Nom de la sauvegarde chargé : " + nomFichier);
                        buttonBas.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                GestionSauvegardes gestionSauvegardes = new GestionSauvegardes();
                                Object[] listeDonnees = gestionSauvegardes.chargement(nomFichier);
                                controller.goToGame(nomFichier, listeDonnees);
                            }
                        });
                    }
                });
                button.setStyle(
                        "-fx-pref-width: " + pasCol + "; -fx-pref-height: " + pasLigne/4 +
                                "; -fx-background-color: " + couleur +
                                "; -fx-border-width:1; -fx-border-color:#000000"
                );
                vBox.getChildren().addAll(new Region(), button);
                VBox.setVgrow(vBox.getChildren().get(0), Priority.ALWAYS);
                VBox.setMargin(button, new Insets(pasLigne *3/ 4-2, 0, 0, 0));
                getChildren().add(vBox);

                x += pasCol * 2;
                if (x > this.width - pasCol) {
                    x = pasCol;
                    y += pasLigne * 3 / 2;
                }
            }
            getChildren().add(menuHaut);
            getChildren().add(menuBas);
        }
    }

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
