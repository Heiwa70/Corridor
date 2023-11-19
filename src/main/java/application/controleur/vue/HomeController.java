package application.controleur.vue;

import application.vue.pages.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class HomeController {

    private Home homeView; // Référence à la vue
    private Scene scene;

    public HomeController() {
    }

    public void setHomeView(Home homeView) {
        this.homeView = homeView;
    }



    public void handleNewGameButtonClick(Scene sceneTest) {

        // Evenement du bouton "Nouvelle partie"
        if (homeView != null && homeView.getScene() == null) {
            NewGame newGame = new NewGame(sceneTest);
            sceneTest.setRoot(newGame);
        } else if (homeView != null) {
            NewGame newGame = new NewGame(homeView.getScene());
            Scene scene = homeView.getScene();
            scene.setRoot(newGame);
        }
        else {
            System.out.println("homeView is null");
        }


    }

    public void PrintTest(Scene SceneText){
        System.out.println("test");

    }

    public void handleLoadGameButtonClick() {
        // Evenement du bouton "Partie sauvegardée"
        LoadGame loadGame = new LoadGame();

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

    public void SetScene(Scene scene){
        //homeView.setCustomScene(scene);
        this.scene = scene;
    }
}
