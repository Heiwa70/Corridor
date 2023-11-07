package application.vue.pages;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LoadGame extends VBox {

    public LoadGame() {
        Label label = new Label("Partie sauvegardée");
        getChildren().addAll(label);

        setAlignment(Pos.CENTER);


    }
}
