package application.vue;

import application.controleur.Jeu;
import application.modele.Joueur;
import application.modele.Log;

import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args){

        Log.info("Main", "start");

        Scanner scanner = new Scanner(System.in);
        int nombreJoueur = 2;
        int nombreMurs = 10;
        int width = 9;
        int height = 9;

        do {
			System.out.print("\nNombre de joueurs entre 2-4 : ");
			try {
				nombreJoueur = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
			}
		} while (nombreJoueur>4 || nombreJoueur<2);

        ArrayList<Joueur> listeJoueur = new ArrayList<>();

        int idJoueur = 0;

        while(nombreJoueur>0){

            System.out.print("\nNom du joueur : ");
			String nom = scanner.nextLine();
            System.out.print("Couleur du joueur : ");
			String couleur = scanner.nextLine();

            listeJoueur.add(new Joueur(idJoueur, nom, couleur, nombreMurs, 0));
            nombreJoueur--;
            idJoueur++;
        }

        System.out.println("\nCréation du jeu avec un plateau de "+width+" x "+height);

        Jeu jeu = new Jeu(width, height, listeJoueur);
        jeu.start();
        jeu.sauvegarde("test");

        jeu = new Jeu("test");

        jeu.start();

        scanner.close();
    }
}
