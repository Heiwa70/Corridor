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
import java.util.List;

public class GestionSauvegardes {

    private String path;
    private HashMap<String, Val> conversionCase;

    public GestionSauvegardes() {
        this.path = "src//main//ressources//sauvegardes//";
        this.conversionCase = new HashMap<>();
        this.conversionCase.put("CASEPION", Val.CASEPION);
        this.conversionCase.put("__PION__", Val.__PION__);
        this.conversionCase.put("CASEMURS", Val.CASEMURS);
        this.conversionCase.put("__MURS__", Val.__MURS__);
        this.conversionCase.put("__VIDE__", Val.__VIDE__);
    }

    public boolean enregistrement(String nom, Plateau plateau, HashMap<Joueur, Integer> pointsJoueur, int idJoueurActuel) {

        StringBuilder text = new StringBuilder(plateau.toString(true));

        for (Joueur joueur : pointsJoueur.keySet()) {
            text.append("\n").append(joueur.toString()).append(" : ").append(pointsJoueur.get(joueur));
        }

        File file = new File(path + "//" + nom + ".save");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            fw.write(text.toString()+"\n"+idJoueurActuel);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap<Plateau, HashMap<Joueur, Integer>> chargement(String nom, int idJoueurActuel) {

        HashMap<Joueur, Integer> pointsJoueur = new HashMap<>();
        Plateau plateau;

        ArrayList<ArrayList<String>> matrice = new ArrayList<>();
        boolean modeLectureJoueurs = false;
        HashMap<Plateau, HashMap<Joueur, Integer>> retour = new HashMap<>();

        try {
            Path chemin = Paths.get(path + nom + ".save");
            List<String> text = Files.readAllLines(chemin);
            ArrayList<Integer[]> coordsPions = new ArrayList<>();
            for (String ligne : text) {

                if (ligne.equals("")) {
                    modeLectureJoueurs = true;
                }
                else if (modeLectureJoueurs) {
                    if(ligne.length()==1){
                        idJoueurActuel = Integer.parseInt(ligne);
                    }
                    String[] donnees = ligne.split(" : ");
                    Joueur joueur = new Joueur(Integer.parseInt(donnees[0]), donnees[1], donnees[2],Integer.parseInt(donnees[5]), Integer.parseInt(donnees[6]));
                    pointsJoueur.put(joueur, Integer.parseInt(donnees[4]));
                    coordsPions.add(new Integer[]{Integer.parseInt(donnees[3]), Integer.parseInt(donnees[4])});
                } else {

                    ligne = ligne.replace(" ", "");
                    ligne = ligne.replace("||", ",");
                    ligne = ligne.replace("|", "");
                    ArrayList<String> ligneMatrice = new ArrayList<>();
                    for (String valeur : ligne.split(",")) {
                        ligneMatrice.add(valeur);
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
                        emplacement.setValeur(this.conversionCase.get(matrice.get(y).get(x)));
                    }
                }
            }

            for(Joueur joueur : pointsJoueur.keySet()){
                joueur.setPion(plateau.getEmplacement(coordsPions.get(joueur.getId())[0], coordsPions.get(joueur.getId())[1]));
            }

            retour.put(plateau, pointsJoueur);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retour;
    }
}