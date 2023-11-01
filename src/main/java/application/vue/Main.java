package application.vue;

import application.controleur.Plateau;
import application.modele.Log;

public class Main {
    public static void main(String[] args){
        Plateau plateau = new Plateau(9,9);
        System.out.println(plateau.toString());
        Log.info("main","test");
    }
}
