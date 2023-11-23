/**
 * Classe LoadGame écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.vue.pages;

import application.controleur.vue.LoadGameController;
import application.modele.GestionSauvegardes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * La classe LoadGame regroupe l'ensemble du code pour l'affichage des sauvegardes du jeu.
 */

public class LoadGame extends Parent {

    private int width;
    private int height;
    private LoadGameController controller;

    /**
     * Constructeur qui initialise la vue.
     *
     * @param scene Scene
     */
    public LoadGame(Scene scene) {

        this.width = (int) scene.getWidth();
        this.height = (int) scene.getHeight();

        this.controller = (new LoadGameController(scene));
        listeSauvegardes();
    }

    /**
     * Elle permet de charger les sauvegardes et les afficher dans une page javafx.
     */
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

            // Il affiche le menu du haut qui permet de quitter la page.

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

            // Il affiche le menu du bas qui permet de charger la partie sélectionnée.

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

            // Pour chaque sauvegarde, il affiche un rectangle sélectionnable pour charger la partie sélectionné.
            int nbr = 0;
            for (File fichier : fichiers) {
                if (nbr > 5) {
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
                Button delete = new Button("X");
                delete.setStyle("-fx-cursor: HAND;-fx-color-label-visible: white;-fx-pref-width: " + pasCol / 4 + "; -fx-pref-height: " + pasLigne / 4 + "; -fx-background-color: #ff0000; -fx-border-width:1; -fx-border-color:#000000");

                // Change dynamiquement le texte et la fonction du menu bas.
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

                // Permet de supprimer une sauvegarde.
                delete.setOnAction(event -> {
                    // Créer une boîte de dialogue de confirmation
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation de suppression");
                    alert.setHeaderText("Supprimer la partie");
                    alert.setContentText("Voulez-vous vraiment supprimer la partie " + nomFichier + "?");

                    // Ajouter les boutons OK et Annuler
                    alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

                    // Récupérer le résultat de la boîte de dialogue
                    alert.showAndWait().ifPresent(result -> {
                        if (result == ButtonType.OK) {
                            // Logique pour supprimer la sauvegarde
                            System.out.println("Suppression de la partie " + nomFichier);

                            // Chemin du fichier à supprimer
                            String cheminFichier = "src/main/ressources/sauvegardes/" + nomFichier + ".save";
                            Path path = Paths.get(cheminFichier);

                            try {
                                // Supprimer le fichier
                                Files.deleteIfExists(path);
                                System.out.println("Fichier supprimé avec succès");

                                // Actualiser la vue en reconstruisant la liste des sauvegardes
                                getChildren().clear();  // Supprimer tous les éléments actuels
                                listeSauvegardes();     // Re-construire la liste des sauvegardes MERCI MAX
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.err.println("Erreur lors de la suppression du fichier");
                            }
                        }
                    });
                });


                button.setStyle(
                        "-fx-pref-width: " + pasCol + "; -fx-pref-height: " + pasLigne / 4 +
                                "; -fx-background-color: " + couleur +
                                "; -fx-border-width:1; -fx-border-color:#000000"
                );
                vBox.getChildren().addAll(new Region(), button, delete);
                VBox.setVgrow(vBox.getChildren().get(0), Priority.ALWAYS);
                VBox.setMargin(button, new Insets(pasLigne * 3 / 4 - 2, 0, 0, 0));
                getChildren().add(vBox);

                x += pasCol * 2;
                if (x > this.width - pasCol) {
                    x = pasCol;
                    y += pasLigne * 3 / 2;
                }
            }

            // Ajoute les tout à la page.
            getChildren().add(menuHaut);
            getChildren().add(menuBas);
        }
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
