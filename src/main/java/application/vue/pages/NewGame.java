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

        // Charger le fichier CSS
       // getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

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

        // Ajouter des marges
        VBox.setMargin(playersRow, new Insets(30, 30, 30, 30)); // Ajustez les marges selon vos besoins
        VBox.setMargin(nameGame, new Insets(20, 0, 0, 0));
        VBox.setMargin(labelGame, new Insets(20, 0, 0, 0));

        vBox.getChildren().addAll(labelGame, nameGame, playersRow);

        return vBox;
    }

    private StackPane createStackPane() {
        StackPane stackPane = new StackPane();

        // Add Button to StackPane
        Button button = new Button("Créer la partie");
        button.getStyleClass().add("createButton");

        // Effet au survol avec transition
        button.setOnMouseEntered((MouseEvent e) -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });

        // Retour à la normale après le survol avec transition
        button.setOnMouseExited((MouseEvent e) -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });

        // Effet au clic
        button.setOnMousePressed(e -> button.getStyleClass().add("createButtonPressed"));

        // Retour à la normale après le clic
        button.setOnMouseReleased(e -> button.getStyleClass().remove("createButtonPressed"));

        stackPane.getChildren().add(button);

        return stackPane;
    }

    private HBox createPlayersRow() {
        HBox hbox = new HBox(10); // Espacement entre les carrés
        hbox.setAlignment(Pos.CENTER);

        for (int i = 0; i < 4; i++) {
            StackPane square = createSquare();
            ComboBox<String> typeComboBox = createComboBox("Type", "Joueur1", "Joueur2", "Joueur3", "Joueur4", "IA");
            ComboBox<String> colorComboBox = createComboBox("Couleur", "Rouge", "Bleu", "Vert", "Jaune", "Violet");

            typeComboBox.getStyleClass().add("comboBox");
            colorComboBox.getStyleClass().add("comboBox");

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

        // Ajouter l'étiquette "+" au centre
        Label plusLabel = new Label("+");
        plusLabel.getStyleClass().add("plusLabel");

        square.getStyleClass().add("square");
        square.getChildren().add(plusLabel);
        StackPane.setAlignment(plusLabel, Pos.CENTER);

        return square;
    }

    private void applyGameStyle() {
        // Style pour le TextField
        nameGame.getStyleClass().add("nameGame");

        // Style pour le Label
        labelGame.getStyleClass().add("labelGame");
    }

    public void setController(NewGameController controller) {
        this.controller = controller;
    }
}
