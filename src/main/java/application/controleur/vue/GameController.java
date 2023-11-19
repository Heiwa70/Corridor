package application.controleur.vue;

// On pourra changer les noms ect
// je prefere juste avoir un controller pour chaque vue

import application.vue.pages.Home;
import application.vue.pages.LoadGame;
import javafx.scene.Scene;

public class GameController {
    private Scene scene;

    public GameController(Scene scene) {
        this.scene = scene;
    }

    public void goToLoadGame() {
        Scene scene = this.scene;
        LoadGame loadGame = new LoadGame(scene);
        scene.setRoot(loadGame);
    }

    public void goToHome() {
        Home home = new Home(40);
        Scene scene = this.scene;
        scene.setRoot(home);
    }
}
