/**
 * Classe GestionSauvegardes écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

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

/**
 * La classe GestionSauvegardes permet de suavegarder et de charger un partie.
 */
public class GestionSauvegardes {

    private String path;
    private HashMap<String, Val> conversionCase;

    /**
     * Le constructeur de GestionSauvegardes initialise le chemin du dossier de sauvegarde et
     * un dictionnaire de données pour pouvoir traduire les valeurs des emplacements du plateau d'un string vers
     * une valeur de l'énumération Val.
     */
    public GestionSauvegardes() {
        Log.info("GestionSauvegardes", "Instanciation du gestionnaire de sauvegarde.");
        this.path = "src//main//ressources//sauvegardes//";
        this.conversionCase = new HashMap<>();
        this.conversionCase.put("CASEPION", Val.CASEPION);
        this.conversionCase.put("__PION__", Val.__PION__);
        this.conversionCase.put("CASEMURS", Val.CASEMURS);
        this.conversionCase.put("__MURS__", Val.__MURS__);
        this.conversionCase.put("__VIDE__", Val.__VIDE__);
    }

    /**
     * Cette fonction permet de voir si le nom de la sauvegarde en paramètre correspond à un fichier de sauvegarde.
     *
     * @param nomSauvegarde String, Nom de la sauvegarde.
     * @return Boolean, Résultat du test.
     */
    public boolean testSauvegardeExiste(String nomSauvegarde) {
        Log.info("GestionSauvegardes", "Vérification de l'éxistance de la sauvegarde '"+nomSauvegarde+"'.");
        File file = new File(path + "//" + nomSauvegarde + ".save");
        if (file.exists()) {
            Log.info("GestionSauvegardes", "Cette sauvegarde existe.");
            return true;
        } else {
            Log.info("GestionSauvegardes", "Cette sauvegarde n'existe pas.");
            return false;
        }
    }

    /**
     * La fonction enregistrement converti toutes les données nécessaires au jeu en String puis les enregistre dans un fichier.
     *
     * @param nom String, Nom de la partie.
     * @param plateau Plateau, Plateau de la partie.
     * @param pointsJoueur HashMap<Joueur, int> Dictionnaire des points de chaques joueurs.
     * @param idJoueurActuel int, Id du joueur qui jouera au tour prochain.
     * @return boolean, Si l'enregistrement c'est bien effectué.
     */
    public boolean enregistrement(String nom, Plateau plateau, HashMap<Joueur, Integer> pointsJoueur, int idJoueurActuel) {

        Log.info("GestionSauvegardes", "Début de l'enregistrement de la partie '"+nom+"'.");
        Log.info("GestionSauvegardes", "Convertion du plateau en String.");
        StringBuilder text = new StringBuilder(plateau.toString(true));

        Log.info("GestionSauvegardes", "Conversion des données de chaque joueur en String.");
        for (Joueur joueur : pointsJoueur.keySet()) {
            text.append("\n").append(joueur.toString()).append(" : ").append(pointsJoueur.get(joueur));
        }

        Log.info("GestionSauvegardes", "Initialisation du fichier de sauvegarde.");
        File file = new File(path + "//" + nom + ".save");

        try {
            // Création du fichier s'il n'existe pas.
            if (!file.exists()) {
                Log.info("GestionSauvegardes", "Création du fichier de sauvegarde.");
                file.createNewFile();
            }
            // Ouverture, écriture de la partie et fermeture du fichier.
            Log.info("GestionSauvegardes", "Ouverture du fichier.");
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
            Log.info("GestionSauvegardes", "Ecriture des données.");
            fw.write(text.toString() + "\n" + idJoueurActuel);
            Log.info("GestionSauvegardes", "Fermeture du fichier.");
            fw.close();

        } catch (IOException e) {
            // Erreur lors de la création ou de l'étape écriture.
            Log.error("GestionSauvegardes", "Erreur lors de la création ou de l'étape écriture.");
            e.printStackTrace();
            return false;
        }
        Log.info("GestionSauvegardes", "Fin de la sauvegarde.");
        return true;
    }

    /**
     * La fonction chargement permet de charger une partie depuis un fichier de sauvegarde.
     *
     * @param nom String, Nom de la sauvegarde.
     * @return Object[], Liste contenant le plateau, les disctionnaire<joueur, points> et l'id du joueur qui devra jouer.
     */
    public Object[] chargement(String nom) {

        Log.info("GestionSauvegardes", "Chargement de la sauvegarde '"+nom+"'.");

        HashMap<Joueur, Integer> pointsJoueur = new HashMap<>();
        Plateau plateau;

        ArrayList<ArrayList<String>> matrice = new ArrayList<>();
        boolean modeLectureJoueurs = false;
        int idJoueurActuel = 0;

        try {
            Log.info("GestionSauvegardes", "Lecture de la sauvegarde.");
            Path chemin = Paths.get(path + nom + ".save");
            List<String> text = Files.readAllLines(chemin);
            ArrayList<Integer[]> coordsPions = new ArrayList<>();

            // Décomposition du text en ligne et analyse de chaque lignes.
            Log.info("GestionSauvegardes", "Lecture du plateau.");
            for (String ligne : text) {

                //
                if (ligne.equals("")) {
                    Log.info("GestionSauvegardes", "Lecture des joueurs.");
                    modeLectureJoueurs = true;
                } else if (modeLectureJoueurs) {
                    if (ligne.length() <= 2) {
                        Log.info("GestionSauvegardes", "Récupération de l'id du prochain joueur.");
                        idJoueurActuel = Integer.parseInt(ligne);
                        break;
                    }
                    // Récupération des données du joueurs
                    String[] donnees = ligne.split(" : ");

                    // Création du joueur correspondant.
                    Joueur joueur = new Joueur(Integer.parseInt(donnees[0]), donnees[1], donnees[2], Integer.parseInt(donnees[5]), Integer.parseInt(donnees[6]));
                    // Affectation de ses points.
                    pointsJoueur.put(joueur, Integer.parseInt(donnees[4]));
                    // Garde en mémoire les coordonnées de son pion pour lui affecter un emplacement.
                    coordsPions.add(new Integer[]{Integer.parseInt(donnees[3]), Integer.parseInt(donnees[4])});
                } else {

                    // Suppression des données superflux du plateau.
                    ligne = ligne.replace(" ", "");
                    ligne = ligne.replace("||", ",");
                    ligne = ligne.replace("|", "");
                    ArrayList<String> ligneMatrice = new ArrayList<>();
                    // Ajout des états contenu dans la ligne dans une liste.
                    for (String valeur : ligne.split(",")) {
                        ligneMatrice.add(valeur);
                    }
                    // Ajout de la liste à la matrice d'état.
                    matrice.add(ligneMatrice);
                }
            }
            Log.info("GestionSauvegardes", "Fin de la lecture.");
            Log.info("GestionSauvegardes", "Début traduction.");
            int width = matrice.get(0).size(), height = matrice.size();

            plateau = new Plateau((width + 1) / 2, (height + 1) / 2);
            Log.info("GestionSauvegardes", "Affectation des état de la matrice d'état à un nouveau plateau.");
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Emplacement emplacement = plateau.getEmplacement(x, y);
                    if (emplacement != null) {
                        emplacement.setValeur(this.conversionCase.get(matrice.get(y).get(x)));
                    }
                }
            }
            Log.info("GestionSauvegardes", "Affectation de l'emplacement du pion au joueur.");
            for (Joueur joueur : pointsJoueur.keySet()) {
                joueur.setPion(plateau.getEmplacement(coordsPions.get(joueur.getId())[0], coordsPions.get(joueur.getId())[1]));
            }
            Log.info("GestionSauvegardes", "Fin du chargement de la sauvegarde.");
            return new Object[]{plateau, pointsJoueur, idJoueurActuel};
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.error("GestionSauvegardes", "Erreur lors du chargement de la sauvegarde.");
        return null;
    }
}