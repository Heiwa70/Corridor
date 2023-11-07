package application.vue.pages;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class NewGame extends VBox{

    public NewGame() {
        Label label = new Label("Nouvelle partie");

        getChildren().addAll(label);
        setAlignment(Pos.CENTER);
    }
}
