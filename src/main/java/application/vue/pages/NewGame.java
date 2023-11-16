package application.vue.pages;

import application.controleur.vue.NewGameController;
import application.modele.Joueur;
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

import java.util.ArrayList;

public class NewGame extends Parent {

    private NewGameController controller;
    private TextField nameGame;
    private Label labelGame;
    private ArrayList<Joueur> listJoueurs = new ArrayList<>();
    private ArrayList<String> joueurs = new ArrayList<>();
    private ArrayList<String> couleurs = new ArrayList<>();

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

        button.setOnAction(e -> {
            // Code pour naviguer vers la page du jeu (Game)
            for(int i = 0; i < joueurs.size(); i++){
                listJoueurs.add(new Joueur(i,joueurs.get(i),couleurs.get(i),10,0));
            }

            for (Joueur joueurs : listJoueurs) {
                System.out.println(joueurs.toString());
            }
            //Game gamePage = new Game();
            //getScene().setRoot(gamePage);
        });

        stackPane.getChildren().add(button);

        return stackPane;
    }

    private HBox createPlayersRow() {
        HBox hbox = new HBox(10); // Espacement entre les carrés


        ObservableList<String> TypeJoueurs = FXCollections.observableArrayList("Joueur1", "Joueur2", "Joueur3", "Joueur4", "IA");
        ObservableList<String> Couleurs = FXCollections.observableArrayList("Rouge", "Bleu", "Vert", "Jaune", "Violet");


        for (int i = 0; i < 4; i++) {

            StackPane square = createSquare();
            // ComboBox<String> typeComboBox = createComboBox("Type", "Joueur1", "Joueur2", "Joueur3", "Joueur4", "IA");
            //ComboBox<String> colorComboBox = createComboBox("Couleur", "Rouge", "Bleu", "Vert", "Jaune", "Violet");

            // Ajouter les listes globales aux listes déroulantes

            ComboBox<String> typeComboBox = new ComboBox<>(TypeJoueurs);
            ComboBox<String> colorComboBox = new ComboBox<>(Couleurs);

            // Ajout des écouteurs


            typeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    ObservableList TypeTemp = FXCollections.observableArrayList(newValue);
                    ComboBox<String> Temp = new ComboBox<String>(TypeTemp);

                    this.joueurs.add(newValue); // Ajout du type de joueur dans la liste globale

                    typeComboBox.setItems(TypeTemp);
                }
            });

            colorComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    ObservableList ColorTemp = FXCollections.observableArrayList(newValue);
                    ComboBox<String> Temp = new ComboBox<String>(ColorTemp);

                    this.couleurs.add(newValue); // Ajout de la couleur dans la liste globale

                    colorComboBox.setItems(ColorTemp);
                }
            });





            typeComboBox.getStyleClass().add("comboBox");
            colorComboBox.getStyleClass().add("comboBox");

            // Marge en haut des listes déroulantes
            VBox.setMargin(typeComboBox, new Insets(10, 0, 0, 0));
            VBox.setMargin(colorComboBox, new Insets(10, 0, 0, 0));


            VBox squareContainer = new VBox(square, typeComboBox, colorComboBox);

            //ajoute une bordure rouge autour des carrés directement dans le code
            squareContainer.setStyle("-fx-border-color: red;");

            //centrer horizontalement tout les elements à l'interieur de squareContainer
            squareContainer.setAlignment(Pos.CENTER);

            //squareContainer.setAlignment(Pos.BOTTOM_LEFT);

            hbox.getChildren().add(squareContainer);
        }

        return hbox;
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
