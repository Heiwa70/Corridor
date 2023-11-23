/**
 * Classe Murs écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.modele;

/**
 * La classe Murs permet de gérer l'emplacement des murs sur le plateau.
 */
public class Murs {
    private Emplacement[] casesPrisent;

    public Emplacement[] getCasesPrisent() {
        return casesPrisent;
    }

    /**
     * Constructeur de la classe Murs.
     * Il initialise sa liste d'emplacement où il sera posé.
     */
    public Murs() {
        this.casesPrisent = new Emplacement[3];
    }

    /**
     * Affecte 3 emplacements au murs et change leur l'état.
     * @param casegauche Emplacement
     * @param casemilieu Emplacement
     * @param casedroite Emplacement
     */
    public void setPosition(Emplacement casegauche, Emplacement casemilieu, Emplacement casedroite) {

        this.casesPrisent[0] = casegauche;
        casegauche.setValeur(Val.__MURS__);
        this.casesPrisent[1] = casemilieu;
        casemilieu.setValeur(Val._OCCUPE_);
        this.casesPrisent[2] = casedroite;
        casedroite.setValeur(Val.__MURS__);

    }

    /**
     * Enlève les 3 emplacements murs et réinitialise leur état.
     * @param casegauche
     * @param casemilieu
     * @param casedroite
     */
    public void undosetPosition(Emplacement casegauche, Emplacement casemilieu, Emplacement casedroite) {

        casegauche.setValeur(Val.CASEMURS);
        casemilieu.setValeur(Val.__VIDE__);
        casedroite.setValeur(Val.CASEMURS);
    }
}