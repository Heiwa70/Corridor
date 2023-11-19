package application.controleur.vue;

import application.vue.pages.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class HomeController {

    private Home homeView; // Référence à la vue

    public HomeController() {
    }

    public void setHomeView(Home homeView) {
        this.homeView = homeView;
    }

    public void configure() {
        homeView.setController(this);
    }

    public void handleNewGameButtonClick() {
         // Evenement du bouton "Nouvelle partie"
        NewGame newGame = new NewGame();

        // Chargez la vue "Nouvelle partie" dans la scène actuelle
        Scene scene = homeView.getScene();
        scene.setRoot(newGame);


    }


    public void handleLoadGameButtonClick() {
        // Evenement du bouton "Partie sauvegardée"
        Game loadGame = new Game();

        // Chargez la vue "Partie sauvegardée" dans la scène actuelle
        Scene scene = homeView.getScene();
        scene.setRoot(loadGame);
    }

    public void handleExitButtonClick() {
        // Evenement du bouton "Quitter"
        // Affichage d'une fenêtre de confirmation avant de quitter le jeu.

        ButtonType result = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment quitter le jeu ?").showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            Platform.exit();
        }

    }

    public void handleBackButtonClick() {
        // Evenement du bouton "Retour"
        // Chargez la vue "Accueil" dans la scène actuelle

        Home home = new Home(40);
        Scene scene = homeView.getScene();
        scene.setRoot(home);
    }
}
