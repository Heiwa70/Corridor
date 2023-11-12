package application.vue.pages;

import application.controleur.vue.NewGameController;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
      //  root.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-padding: 10px;");

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

        // HBox with player squares
        HBox playersRow = createPlayersRow();
        playersRow.setAlignment(Pos.CENTER);

        // Ajouter des marges à playersRow
        VBox.setMargin(playersRow, new Insets(30, 30, 30, 30)); // Ajustez les marges selon vos besoins

        // Label and TextField
        vBox.getChildren().addAll(labelGame, nameGame, playersRow);
       // vBox.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-padding: 10px;");

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
                "-fx-background-color: #45a049;" +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-padding: 10px 20px;" +
                        "-fx-font-weight: bold;"
        ));

        // Retour à la normale après le clic
        button.setOnMouseReleased(e -> button.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-padding: 10px 20px;" +
                        "-fx-font-weight: bold;"
        ));

        stackPane.getChildren().add(button);

       // stackPane.setStyle("-fx-border-color: blue; -fx-border-width: 2px; -fx-padding: 10px;");
        return stackPane;
    }

    private HBox createPlayersRow() {
        HBox hbox = new HBox(10); // Espacement entre les carrés
        hbox.setAlignment(Pos.CENTER);

        for (int i = 0; i < 4; i++) {
            StackPane square = createSquare();
            ComboBox<String> typeComboBox = createComboBox("Type", "Joueur1", "Joueur2", "Joueur3", "Joueur4", "IA");
            ComboBox<String> colorComboBox = createComboBox("Couleur", "Rouge", "Bleu", "Vert", "Jaune", "Violet");

            typeComboBox.setMinWidth(80);
            typeComboBox.setMaxWidth(80);

            colorComboBox.setMinWidth(80);
            colorComboBox.setMaxWidth(80);

            typeComboBox.setStyle(
                    "-fx-background-color: #f1ede9; " +
                            "-fx-text-fill: black; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: 'Arial';" +
                            "-fx-border-radius: 5px; " +
                            "-fx-padding: 5px 5px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-cursor: hand;"
            );

            colorComboBox.setStyle(
                    "-fx-background-color: #f1ede9; " +
                            "-fx-text-fill: black; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: 'Arial';" +
                            "-fx-border-radius: 5px; " +
                            "-fx-padding: 5px 5px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-cursor: hand;"
            );

            // Marge en haut des listes déroulantes
            VBox.setMargin(typeComboBox, new Insets(10, 0, 0, 0));
            VBox.setMargin(colorComboBox, new Insets(10, 0, 0, 0));

            VBox squareContainer = new VBox(square, typeComboBox, colorComboBox);
            squareContainer.setAlignment(Pos.BOTTOM_LEFT);

            hbox.getChildren().add(squareContainer);
        }

        return hbox;
    }
    private ComboBox<String> createComboBox(String label, String... options) {
        Label comboBoxLabel = new Label(label);
        ComboBox<String> comboBox = new ComboBox<>();

        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList(options);
        comboBox.setItems(comboBoxOptions);

        VBox comboBoxContainer = new VBox(comboBoxLabel, comboBox);
        comboBoxContainer.setAlignment(Pos.BOTTOM_LEFT);

        return comboBox;
    }

    private StackPane createSquare() {
        StackPane square = new StackPane();

        // Taille du carré PAS QU'ILS BOUGENT !!!!
        square.setMinSize(80, 80);
        square.setMaxSize(80, 80);

        // Styles pour rendre invisible la partie dépassant des bords
        square.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-background-clip: padding-box; " +
                        "-fx-background-insets: 20px, 20px, 20px, 20px; " + // Ajustez la taille des marges
                        "-fx-border-radius: 10px; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-color: #333333; " +
                        "-fx-cursor: hand;"
        );

        // Ajouter l'étiquette "+" au centre
        Label plusLabel = new Label("+");
        plusLabel.setStyle("-fx-text-fill: black; -fx-font-size: 24px; -fx-font-weight: bold;");

        square.getChildren().add(plusLabel);
        StackPane.setAlignment(plusLabel, Pos.CENTER);

        return square;
    }


    private void applyGameStyle() {
        // Style pour le TextField
        nameGame.setStyle(
                "-fx-background-color: #f1ede9; " +
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
