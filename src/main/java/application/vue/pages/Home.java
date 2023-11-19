package application.vue.pages;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import application.controleur.vue.HomeController;

public class Home extends VBox {

    private Button newGame = createButton("Nouvelle partie");
    private Button loadGame = createButton("(game)Partie sauvegardée");
    private Button exit = createButton("Quitter");
    private Button back = createButton("Retour");
    private HomeController controller;
    private Scene scene;

    public Home(double spacing ){
        super(spacing);
        initializeComponents();
    }
    public void setCustomScene(Scene scene) {
        this.scene = scene;
        System.out.println("setCustomScene : "+scene);
    }

    public void setController(HomeController controller) {

        if (controller != null) {
            this.controller = controller;
        }

        // Passez la référence à la scène au contrôleur
        this.controller.SetScene(this.getScene());
    }

    private void initializeComponents() {
        this.setAlignment(Pos.CENTER);
        setButtonStyle(newGame);
        setButtonStyle(loadGame);
        setButtonStyle(exit);
        setBackButtonStyle(back);
        getChildren().addAll(newGame, loadGame, exit);
        if(this.controller == null){
            setController(new HomeController());
        }

        this.controller.setHomeView(this);




        // Ajout des gestionnaires d'événements du contrôleur
        newGame.setOnAction(e -> this.controller.handleNewGameButtonClick(this.scene)); // il est null au deuxieme appel
        loadGame.setOnAction(e -> this.controller.handleLoadGameButtonClick());
        exit.setOnAction(e -> this.controller.handleExitButtonClick());

    }

    private Button createButton(String text) {
        return new Button(text);
    }

    private void setButtonStyle(Button button) {
        button.setStyle("-fx-text-fill: BLACK; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 10px;");
        button.setCursor(Cursor.HAND);
    }

    private void setBackButtonStyle(Button button) {
        //Créer un style pour les boutons mais bon je sais pas encore en faire un beau :/

    }
}



// Affichage d'une fenêtre de confirmation avant de quitter le jeu.
    //private void confirmExit() {
        //ButtonType result = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment quitter le jeu ?").showAndWait().orElse(ButtonType.CANCEL);
        //if (result == ButtonType.OK) {
        //    Platform.exit();
      //  }
    //}