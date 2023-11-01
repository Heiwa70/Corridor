package application.modele;

import application.controleur.Plateau;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class GestionSauvegardes {

    private final String path = "src//main//ressources//sauvegardes//";

    public GestionSauvegardes() {

    }

    public boolean enregistrement(String nom, Plateau plateau, HashMap<Joueur, Integer> pointsJoueur) {

        StringBuilder text = new StringBuilder(plateau.toString());

        for (Joueur joueur : pointsJoueur.keySet()) {
            text.append("\n").append(joueur.toString()).append(" : ").append(pointsJoueur.get(joueur));
        }

        File file = new File(path + "//" + nom + ".save");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            fw.write(text.toString());
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap<Plateau, HashMap<Joueur, Integer>> chargement(String nom) {


        HashMap<Joueur, Integer> pointsJoueur = new HashMap<>();
        Plateau plateau;

        ArrayList<ArrayList<Integer>> matrice = new ArrayList<>();
        boolean modeLectureJoueurs = false;
        HashMap<Plateau, HashMap<Joueur, Integer>> retour = new HashMap<>();


        try {
            Path chemin = Paths.get(path + nom + ".save");
            String text = Files.readAllLines(chemin).get(0);
            for (String ligne : text.split("\n")) {

                if (ligne.isEmpty()) {
                    modeLectureJoueurs = true;
                }
                if (modeLectureJoueurs) {
                    String[] donnees = ligne.split(" : ");
                    Joueur joueur = new Joueur(donnees[0], donnees[1], Integer.parseInt(donnees[2]), Integer.parseInt(donnees[3]));
                    pointsJoueur.put(joueur, Integer.parseInt(donnees[4]));
                } else {

                    ligne.replace(" ", "");
                    ligne.replace("||", ",");
                    ArrayList<Integer> ligneMatrice = new ArrayList<>();
                    for (String valeur : ligne.split(",")) {
                        ligneMatrice.add(Integer.parseInt(valeur));
                    }
                    matrice.add(ligneMatrice);
                }
            }

            int width = matrice.get(0).size(), height = matrice.size();

            plateau = new Plateau((width + 1) / 2, (height + 1) / 2);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Emplacement emplacement = plateau.getEmplacement(x, y);
                    if (emplacement != null) {
                        emplacement.setValeur(matrice.get(y).get(x));
                    }
                }
            }
            retour.put(plateau, pointsJoueur);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retour;
    }
}