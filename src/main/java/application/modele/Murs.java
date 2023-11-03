/**
 * Classe Murs écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.modele;

import java.util.ArrayList;

/**
 * La classe Murs permet de gérerl'emplacement des murs sur le plateau.
 */
public class Murs {
    private ArrayList<Emplacement> casesPrisent;

    /**
     * Constructeur de la classe Murs.
     */
    public Murs() {
        this.casesPrisent = new ArrayList<>();
    }

    public void setPosition(Emplacement case1, Emplacement case2) {
        this.casesPrisent.add(case1);
        case1.setValeur(Val.__MURS__);
        this.casesPrisent.add(case2);
        case2.setValeur(Val.__MURS__);
    }
}