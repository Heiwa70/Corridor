/**
 * Classe Emplacement écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.modele;

/**
 * La classe Emplacement correspond à chaque case du plateau.
 */
public class Emplacement {
    private int x;
    private int y;
    private Val valeur;

    /**
     * Constructeur de Emplacement initialise les coordonnées et sa valeur.
     *
     * @param x      int, Position en x.
     * @param y      int, Position en y.
     * @param valeur Val, Valeur d'une énumération.
     */
    public Emplacement(int x, int y, Val valeur) {
        /*
        Log.info(
                "Emplacement",
                "Création de l'emplacement de coodonnées X = "+x+", Y = "+y+" et de valeur : "+valeur+"."
        );
        */

        setX(x);
        setY(y);
        setValeur(valeur);
    }

    public int getX() {
        return x;
    }

    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }

    public Val getValeur() {
        return valeur;
    }

    public void setValeur(Val valeur) {
//        Log.info(
//                "Emplacement",
//                "Valeur de l'emplacement X = " + getX() + ", Y = " + getY() + " : " + getValeur() + " -> " + valeur + "."
//        );
        this.valeur = valeur;
    }

    public String toStringCoords() {
        return getX() + " : " + getY() + " : " + getValeur();
    }
    public String toString() {
        return ""+getValeur();
    }
}
