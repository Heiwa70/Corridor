/**
 * Classe GameController écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.controleur.vue;

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
