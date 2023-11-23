/**
 * Classe NewGameController écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.controleur.vue;

import application.vue.pages.Game;
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
        Scene scene = this.scene;
        scene.setRoot(home);
    }

    public void goToGame(String nomGame, Object[] data) {
        Scene scene = this.scene;
        Game game = new Game(scene,nomGame, data);
        scene.setRoot(game);
    }
}