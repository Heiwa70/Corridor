package application;

import application.controleur.vue.HomeController;
import application.vue.pages.Home;
import application.vue.pages.NewGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
//pour test

    private static final int height = 1280/2;
    private static final int width = 960/2;

    @Override
    public void start(Stage stage) {
        Home homeView = new Home(40);
        //NewGame newGame = new NewGame();
        HomeController controller = new HomeController();
        homeView.setController(controller);
        controller.setHomeView(homeView);


        Scene scene = new Scene(homeView, height, width);
        controller.SetScene(scene);
        scene.getStylesheets().add("file:src/main/java/application/vue/style.css");
        stage.setScene(scene);
        stage.setTitle("Quoridor");
        stage.getIcons().add(new Image("file:src/main/ressources/pictures/icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
