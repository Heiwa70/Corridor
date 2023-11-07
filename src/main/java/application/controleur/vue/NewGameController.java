package application.controleur.vue;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NewGameController extends VBox {

    public NewGameController() {
        Label label = new Label("Nouvelle partie");

        getChildren().addAll(label);
        setAlignment(Pos.CENTER);
    }
}
