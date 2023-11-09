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
        this.casesPrisent = new Emplacement[3];
    }

    public void setPosition(Emplacement casegauche, Emplacement casemilieu,Emplacement casedroite) {
        Log.info("Murs",
                "Affectation du murs '" + this.toString() + "', aux emplacements (" +
                        casegauche.toStringCoords() + ", " + casedroite.toStringCoords() + ")."
        );
        this.casesPrisent[0] = casegauche;
        casegauche.setValeur(Val.__MURS__);
        this.casesPrisent[1]=casemilieu;
        casemilieu.setValeur(Val._OCCUPE_);
        this.casesPrisent[2] = casedroite;
        casedroite.setValeur(Val.__MURS__);

    }
}