/**
 * Classe main écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.vue;

import application.controleur.Jeu;
import application.modele.Log;

/**
 * La classe Main permet de démarrer le projet en utilisant le terminal comme IHM.
 */
public class Main {
    public static void main(String[] args) {

        Log.info("Main", "Début du programme.");
        Log.info("Main", "Création d'un nouveau jeu.");

        Jeu jeu = new Jeu();

        Log.info("Main", "Chargement de la sauvegarde 'test'.");

        jeu = new Jeu("test");

        Log.info("Main", "Fin du programme.");
    }
}
