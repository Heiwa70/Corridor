package application.modele;

import application.controleur.Plateau;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class Calculssanstest {

    private Calculs calculs;
    private GestionSauvegardes gestionSauvegardes;
    private Plateau plateau;
    private HashMap<Integer, Joueur> liste_joueur = new HashMap<>();
    private HashMap<Joueur, Integer> pointJoueur;

    public Calculssanstest() {
        this.gestionSauvegardes = new GestionSauvegardes("src//test//java//ressources//sauvegardes//");
        generation("test3");
        this.calculs = new Calculs(this.plateau);
        use_min_max();
        tearDown();
    }

    public void tearDown() {
        this.plateau = null;
        this.gestionSauvegardes = null;
        this.calculs = null;
    }

    private void generation(String nomSauvegarde) {
        Object[] data = this.gestionSauvegardes.chargement(nomSauvegarde);
        this.plateau = (Plateau) data[0];
        this.pointJoueur = (HashMap<Joueur, Integer>) data[1];
        for(Joueur joueur : this.pointJoueur.keySet()){
            this.liste_joueur.put(joueur.getId(), joueur);
        }
    }
    public void liste_coup_mur(){
        ArrayList<String> coup=calculs.liste_coup_mur(this.liste_joueur.get(1).getX(),this.liste_joueur.get(1).getY(), this.liste_joueur);
        System.out.println(coup.size());

    }
    public static <K, V> Map<V, K> invertMap(Map<K, V> originalMap) {
        Map<V, K> invertedMap = new HashMap<>();
        for (Map.Entry<K, V> entry : originalMap.entrySet()) {
            invertedMap.put(entry.getValue(), entry.getKey());
        }
        return invertedMap;
    }
    public void use_min_max(){
        calculs.use_min_max(this.liste_joueur, 1,6);



        gestionSauvegardes.enregistrement("Partie_bot",plateau, (HashMap<Joueur, Integer>) invertMap(liste_joueur),2);

    }

    public void use_filtre_mur(){
        ArrayList<String> r=calculs.filtreliste_coup_mur(calculs.liste_coup_mur(this.liste_joueur.get(1).getX(),this.liste_joueur.get(1).getY(),this.liste_joueur),this.liste_joueur,1);
        System.out.println(r);
    }
}