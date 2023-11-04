/**
 * Classe Murs écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.modele;

/**
 * La classe Murs permet de gérerl'emplacement des murs sur le plateau.
 */
public class Murs {
    private Emplacement[] casesPrisent;

    /**
     * Constructeur de la classe Murs.
     */
    public Murs() {
        Log.info("Murs", "Création du murs : " + this.toString() + ".");
        this.casesPrisent = new Emplacement[2];
    }

    public void setPosition(Emplacement case1, Emplacement case2) {
        Log.info("Murs",
                "Affectation du murs '" + this.toString() + "', aux emplacements (" +
                        case1.toString() + ", " + case2.toString() + ")."
        );
        this.casesPrisent[0] = case1;
        case1.setValeur(Val.__MURS__);
        this.casesPrisent[2] = case2;
        case2.setValeur(Val.__MURS__);
    }
}