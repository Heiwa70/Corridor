package application.controleur.vue;

import application.vue.pages.Game;
import application.vue.pages.Home;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class LoadGameController extends VBox {

        private Scene scene;

        public LoadGameController(Scene scene) {
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
