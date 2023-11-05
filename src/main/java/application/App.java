package application;

import application.vue.pages.Home;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

       /* var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();*/

        Scene homeView = new Scene(new Home(40), 1280, 960);
        stage.setScene(homeView);
        stage.setTitle("Quoridor");
        stage.getIcons().add(new Image("file:src/main/ressources/pictures/icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}