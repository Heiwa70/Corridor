package application.vue.pages;

import application.controleur.vue.NewGameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.animation.ScaleTransition;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class NewGame extends Parent {

    private NewGameController controller;
    private TextField nameGame;
    private Label labelGame;

    public NewGame() {
        initializeComponents();
        setController(new NewGameController());

        // VBox
        VBox vBox = createVBox();
        vBox.setAlignment(Pos.CENTER);

        // StackPane
        StackPane stackPane = createStackPane();

        // VBox root
        VBox root = new VBox(vBox, stackPane);
        VBox.setMargin(stackPane, new Insets(50, 0, 0, 0));
        root.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-padding: 10px;");

        getChildren().add(root);

        applyGameStyle();

        root.setPrefWidth(1280 / 2);
    }

    private void initializeComponents() {
        this.nameGame = new TextField();
        this.labelGame = new Label("Nom de la partie : ");
    }

    private VBox createVBox() {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelGame, nameGame);
        vBox.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-padding: 10px;");
        return vBox;
    }

    private StackPane createStackPane() {
        StackPane stackPane = new StackPane();

        // Add Button to StackPane
        Button button = new Button("Créer la partie");

        // Styles normaux
        button.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-padding: 10px 20px;" +
                        "-fx-font-weight: bold;"
        );

        // Effet au survol avec transition
        button.setOnMouseEntered((MouseEvent e) -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
            button.setStyle(
                    "-fx-background-color: #29bf65; " +
                            "-fx-cursor: hand; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: 'Arial'; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-padding: 10px 20px;" +
                            "-fx-font-weight: bold;"
                    );
        });

// Retour à la normale après le survol avec transition
        button.setOnMouseExited((MouseEvent e) -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
            button.setStyle(
                    "-fx-background-color: #4CAF50; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: 'Arial'; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-padding: 10px 20px;" +
                            "-fx-font-weight: bold;"
            );
        });


        // Effet au clic
        button.setOnMousePressed(e -> button.setStyle(
                "-fx-background-color: #45a049;"+
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-padding: 10px 20px;" +
                        "-fx-font-weight: bold;"
        ));

        // Retour à la normale après le clic
        button.setOnMouseReleased(e -> button.setStyle(
                "-fx-background-color: #4CAF50; "+
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-padding: 10px 20px;" +
                        "-fx-font-weight: bold;"
        ));

        stackPane.getChildren().add(button);

        stackPane.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-padding: 10px;");
        return stackPane;
    }


    private void applyGameStyle() {
        // Style pour le TextField
        nameGame.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-size: 20px; " +
                        "-fx-font-family: 'Arial';"
        );

        // Style pour le Label
        labelGame.setStyle(
                "-fx-text-fill: black; " +
                        "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-family: 'Arial';"
        );
    }

    public void setController(NewGameController controller) {
        this.controller = controller;
    }
}
