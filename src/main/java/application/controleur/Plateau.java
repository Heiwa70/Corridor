/**
 * Classe Plateau écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.controleur;

import application.modele.Emplacement;
import application.modele.Log;
import application.modele.Val;

/**
 * La classe Plateau est une matrice d'emplacement qui contient les cases pion, murs et vides.
 */
public class Plateau {
    private Emplacement[][] listeToutesLesCases;

    /**
     * Le constructeur initialise le plateau en prenant en paramètre la taille du nombre de cases où les pions peuvent se placer.
     * Attention, après cette étape, toutes les coordonnées de la matrice des emplacements correspondront au plateau
     * contenant les cases pions, murs et vides et non plus juste les cases pions.
     *
     * @param width int, Nombre de cases pions sur une ligne.
     * @param height int, Nombre de cases pions sur une colonne.
     */
    public Plateau(int width, int height) {
        Log.info("Plateau", "Création d'un plateau "+width+"x"+height+".");
        this.listeToutesLesCases = new Emplacement[height * 2 - 1][width * 2 - 1];
        initialialisationPlateau(width, height);
    }

    /**
     * Initialise l'état de chaque emplacement du plateau en fonction des objets (pions, murs) qui peuvent être posés.
     *
     * @param width int, Nombre de cases pions sur une ligne.
     * @param height int, Nombre de cases pions sur une colonne.
     */
    private void initialialisationPlateau(int width, int height) {
        Log.info("Plateau", "Initialisation de l'état de chaque emplacement du plateau.");
        for (int y = 0; y < height * 2 - 1; y++) {
            for (int x = 0; x < width * 2 - 1; x++) {
                this.listeToutesLesCases[y][x] = new Emplacement(x, y,
                        y % 2 == 0 ? (x % 2 == 0 ? Val.CASEPION : Val.CASEMURS) : (x % 2 == 0 ? Val.CASEMURS : Val.__VIDE__)
                );
            }
        }
    }

    /**
     * Récupération de l'emplacement d'un pion.
     *
     * @param x int.
     * @param y int.
     * @return Emplacement, Emplacement d'un pion.
     */
    public Emplacement getEmplacementCasePion(int x, int y) {
        if (0 <= x && x < getWidth() && 0 <= y && y < getHeight()) {
            return this.listeToutesLesCases[y * 2][x * 2];
        }
        return null;
    }

    /**
     * Récupération d'un emplacement.
     *
     * @param x int.
     * @param y int.
     * @return Emplacement.
     */
    public Emplacement getEmplacement(int x, int y) {
        return this.listeToutesLesCases[y][x];
    }

    /**
     * Traduction en text de la valeur de l'emplacement.
     *
     * @param x int.
     * @param y int.
     * @return String, Nom de l'emplacement.
     */
    public String getTypeEmplacement(int x, int y) {
        String resultat = "";
        switch (getEmplacement(x, y).getValeur()) {
            case CASEPION:
                resultat = "case pion";
                break;
            case __PION__:
                resultat = "pion";
                break;
            case CASEMURS:
                resultat = "case murs";
                break;
            case __MURS__:
                resultat = "murs";
                break;
            case __VIDE__:
                resultat = "case interdite";
                break;
            default:
                resultat = "NULL";
                break;
        }
        return resultat;
    }

    public int getWidth() {
        return this.listeToutesLesCases[0].length;
    }

    public int getHeight() {
        return this.listeToutesLesCases.length;
    }

    /**
     * Convetion de plateau en String.
     *
     * @param vueValeur boolean, True pour récupérer les valeurs, False pour récupérer une correspondance visuelle.
     * @return String, Le plateau mais en String.
     */
    public String toString(boolean vueValeur) {

        StringBuilder plateauEnText = new StringBuilder();
        int y = 0;

        // Ajout d'une ligne indiquant l'id de chaque colonne.
        if (!vueValeur) {
            int x = 0;
            plateauEnText.append("          ");
            for (Emplacement emplacement : this.listeToutesLesCases[0]) {
                if (y == 0) {
                    plateauEnText.append("    " + (("" + x).length() == 1 ? x + " " : x) + "   ");
                }
                x++;
            }
            plateauEnText.append("\n");
        }

        // Conversion de chaque valeur en texte.
        for (Emplacement[] ligne : this.listeToutesLesCases) {

            // Ajout d'un id pour chaque ligne.
            if (!vueValeur) {
                plateauEnText.append("    " + (("" + y).length() == 1 ? y + " " : y) + "    ");
            }

            // Convertion de chaque ligne.
            for (Emplacement emplacement : ligne) {
                String valeur = emplacement.toString();

                // Convertion des valeurs en symboles.
                if (!vueValeur) {
                    valeur = valeur.equals("__PION__") ? "O" : valeur.equals("CASEPION") ? " " : valeur.equals("CASEMURS") ? "=" : "X";
                }
                plateauEnText.append("|   " + valeur + "   |");
            }
            plateauEnText.append("\n");
            y++;
        }
        return plateauEnText.toString();
    }
}
