package application.controleur.vue;

import application.vue.pages.Home;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class NewGameController extends VBox {

    private Scene scene;

    public NewGameController(Scene scene) {
        this.scene = scene;
        initializeNewGameView();
    }

    private void initializeNewGameView() {
        // Ajoutez ici le code pour initialiser la vue NewGame
    }
    public void setCustomScene(Scene scene) {
        this.scene = scene;
    }

    public void goToHome() {
        Home home = new Home(40);
        System.out.println("New game scene =  : "+this.scene);
        home.setCustomScene(this.scene);
        Scene scene = this.scene;
        scene.setRoot(home);
    }


}