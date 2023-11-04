package application.controleur;

import application.modele.Emplacement;
import application.modele.Val;

public class Plateau {
    private Emplacement[][] listeToutesLesCases;

    public Plateau(int width, int height) {
        this.listeToutesLesCases = new Emplacement[height * 2 - 1][width * 2 - 1];
        initialialisationPlateau(width, height);
    }

    private void initialialisationPlateau(int width, int height) {

        for (int y = 0; y < height * 2 - 1; y++) {
            for (int x = 0; x < width * 2 - 1; x++) {
                this.listeToutesLesCases[y][x] = new Emplacement(x, y, y % 2 == 0 ? (x % 2 == 0 ? Val.CASEPION : Val.__MURS__) : (x % 2 == 0 ? Val.__MURS__ : Val.__VIDE__));
            }
        }
    }

    public Emplacement getEmplacementCasePion(int x, int y) {
        if (0 <= x && x < getWidth() && 0 <= y && y < getHeight()) {
            return this.listeToutesLesCases[y * 2][x * 2];
        }
        return null;
    }

    public Emplacement getEmplacement(int x, int y) {
        if (0 <= x && x < getWidth() && 0 <= y && y < getHeight()) {
            return this.listeToutesLesCases[y][x];
        }
        return null;
    }

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

    public String toString(boolean vueValeur) {
        StringBuilder plateauEnText = new StringBuilder();
        int y = 0;
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

        for (Emplacement[] ligne : this.listeToutesLesCases) {
            if (!vueValeur) {
                plateauEnText.append("    " + (("" + y).length() == 1 ? y + " " : y) + "    ");
            }
            for (Emplacement emplacement : ligne) {

                String valeur = emplacement.toString();
                if (!vueValeur) {
                    valeur = valeur.equals("__PION__") ? "O" : valeur.equals("CASEPION") ? " " : valeur.equals("__MURS__") ? "=" : "X";
                }
                plateauEnText.append("|   " + valeur + "   |");
            }
            plateauEnText.append("\n");
            y++;
        }
        return plateauEnText.toString();
    }
}
