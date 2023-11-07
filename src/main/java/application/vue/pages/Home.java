package application.vue.pages;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import application.controleur.vue.HomeController;

public class Home extends VBox {

    private Button newGame = createButton("Nouvelle partie");
    private Button loadGame = createButton("Partie sauvegardée");
    private Button exit = createButton("Quitter");
    private HomeController controller;

    public Home(double spacing) {
        super(spacing);
        initializeComponents();
    }

    public void setController(HomeController controller) {
        this.controller = controller;
    }

    private void initializeComponents() {
        this.setAlignment(Pos.CENTER);
        setButtonStyle(newGame);
        setButtonStyle(loadGame);
        setButtonStyle(exit);
        getChildren().addAll(newGame, loadGame, exit);

        // Ajout des gestionnaires d'événements du contrôleur
        newGame.setOnAction(e -> controller.handleNewGameButtonClick());
        loadGame.setOnAction(e -> controller.handleLoadGameButtonClick());
        exit.setOnAction(e -> controller.handleExitButtonClick());
    }

    private Button createButton(String text) {
        return new Button(text);
    }

    private void setButtonStyle(Button button) {
        button.setStyle("-fx-text-fill: BLACK; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 10px;");
        button.setCursor(Cursor.HAND);
    }
}



// Affichage d'une fenêtre de confirmation avant de quitter le jeu.
    //private void confirmExit() {
        //ButtonType result = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment quitter le jeu ?").showAndWait().orElse(ButtonType.CANCEL);
        //if (result == ButtonType.OK) {
        //    Platform.exit();
      //  }
    //}