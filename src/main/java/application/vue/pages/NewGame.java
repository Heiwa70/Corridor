package application.vue.pages;

import application.controleur.vue.NewGameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NewGame extends Parent {

    private NewGameController controller;
    private TextField nameGame;
    private Label labelGame;

    public NewGame() {
        initializeComponents();
        setController(new NewGameController());

        // VBox
        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelGame, nameGame);

        // StackPane
        StackPane stackPane = new StackPane();

        // Add Label to StackPane
        Label label = new Label("I'm a Label");
        label.setStyle("-fx-background-color: yellow");
        label.setPadding(new Insets(5, 5, 5, 5));
        stackPane.getChildren().add(label);

        // Add Button to StackPane
        Button button = new Button("I'm a Button");
        button.setStyle("-fx-background-color: cyan");
        button.setPadding(new Insets(5, 5, 5, 5));
        stackPane.getChildren().add(button);

        // Add CheckBox to StackPane
        CheckBox checkBox = new CheckBox("I'm a CheckBox");
        checkBox.setOpacity(1);
        checkBox.setStyle("-fx-background-color: olive");
        checkBox.setPadding(new Insets(5, 5, 5, 5));
        stackPane.getChildren().add(checkBox);

        // VBox root
        VBox root = new VBox(vBox, stackPane);
        getChildren().add(root);

        // Set preferred size for nameGame
        nameGame.setPrefWidth(200);
    }

    public void initializeComponents() {
        this.nameGame = new TextField();
        this.labelGame = new Label("Nom de la partie : ");
    }

    public void setController(NewGameController controller) {
        this.controller = controller;
    }
}
