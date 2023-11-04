package application.vue;

import application.controleur.Jeu;
import application.modele.Log;

public class Main {
    public static void main(String[] args) {

        Log.info("Main", "start");


        Jeu jeu = new Jeu();
        jeu = new Jeu("test");

    }
}
