/**
 * Classe Home écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.vue.pages;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import application.controleur.vue.HomeController;

/**
 * La classe Game regroupe l'ensemble du code pour le menu du jeu.
 */

public class Home extends VBox {

    private Button newGame = createButton("Nouvelle partie");
    private Button loadGame = createButton("Parties sauvegardées");
    private Button exit = createButton("Quitter");
    private Button back = createButton("Retour");
    private HomeController controller;
    private Scene scene;


    /**
     * Constructeur pour créer le menu principal.
     *
     * @param spacing double
     */
    public Home(double spacing) {
        super(spacing);
        initializeComponents();
    }

    /**
     * Change la scène.
     *
     * @param scene
     */
    public void setCustomScene(Scene scene) {
        this.scene = scene;
        System.out.println("setCustomScene : " + scene);
    }

    public void setController(HomeController controller) {

        if (controller != null) {
            this.controller = controller;
        }

        // Passez la référence à la scène au contrôleur
        this.controller.SetScene(this.getScene());
    }

    /**
     * Affecte les boutons du menu à la scène.
     */
    private void initializeComponents() {
        this.setAlignment(Pos.CENTER);
        setButtonStyle(newGame);
        setButtonStyle(loadGame);
        setButtonStyle(exit);
        setBackButtonStyle(back);
        getChildren().addAll(newGame, loadGame, exit);
        if (this.controller == null) {
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