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
        vBox.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-padding: 10px;");
        vBox.setAlignment(Pos.CENTER);

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

        stackPane.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-padding: 10px;");

        // VBox root
        VBox root = new VBox(vBox, stackPane);
        VBox.setMargin(stackPane, new Insets(50, 0, 0, 0)); // Ajustez cela selon vos besoins
        root.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-padding: 10px;");

        getChildren().add(root);

        // Set preferred size for nameGame
        nameGame.setMaxWidth(200);

        root.setPrefWidth(400);
    }
    public void initializeComponents() {
        this.nameGame = new TextField();
        this.labelGame = new Label("Nom de la partie : ");
    }

    public void setController(NewGameController controller) {
        this.controller = controller;
    }
}
