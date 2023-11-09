package application.vue.pages;

import application.controleur.vue.NewGameController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class NewGame extends VBox{

    NewGameController controller;
    private TextField nameGame;
    private Label labelGame;


    public NewGame() {
        initializeComponents();
        setController(new NewGameController());

        getChildren().addAll(labelGame, nameGame);
    }

    public void initializeComponents() {
        this.nameGame = new TextField();
        this.labelGame = new Label("Nom de la partie : ");
        nameGame.setPrefSize(50, 50);
    }

    public void setController(NewGameController controller) {
        this.controller = controller;
    }


}
