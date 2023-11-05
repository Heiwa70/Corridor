package application.vue.pages;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

public class Home extends VBox {

    Button newGame = createButton("Nouvelle partie");
    Button loadGame = createButton("Partie sauvegardée");
    Button exit = createButton("Quitter");

    // Constructeur par défaut de la classe Home. Avec initialisation des composants.
    public Home(double spacing) {
        super(spacing);
        initializeComponents();
    }

    // Initialisation des composants de la classe Home.
    private void initializeComponents() {
        this.setAlignment(Pos.CENTER);

        setButtonStyle(newGame);
        setButtonStyle(loadGame);
        setButtonStyle(exit);

        exit.setOnAction(e -> confirmExit());

        getChildren().addAll(newGame, loadGame, exit);
    }

    // Ajout d'un composant de type Button à la classe Home.
    private Button createButton(String text) {
        return new Button(text);
    }

    // Modification du style par défaut d'un composant de type Button.
    private void setButtonStyle(Button button) {
        button.setStyle("-fx-text-fill: BLACK; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 10px;");
        button.setCursor(Cursor.HAND);
    }

    // Affichage d'une fenêtre de confirmation avant de quitter le jeu.
    private void confirmExit() {
        ButtonType result = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment quitter le jeu ?").showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            Platform.exit();
        }
    }
}
