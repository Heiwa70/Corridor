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
        generation("vierge");
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
        Joueur j1= this.liste_joueur.get(1);
        Joueur j2=  this.liste_joueur.get(2);
        ArrayList<Joueur> l=new ArrayList<Joueur>();
        l.add(j1);
        l.add(j2);
        Joueur[] l1= l.toArray(new Joueur[0]);
        ArrayList<String> coup=calculs.liste_coup_mur(j1.getX(),j1.getY(), l1);
        System.out.println(coup.size());

    }
    public void use_min_max(){
        Joueur j1= this.liste_joueur.get(1);
        Joueur j2=  this.liste_joueur.get(2);
        ArrayList<Joueur> l=new ArrayList<Joueur>();
        l.add(j1);
        l.add(j2);
        Joueur[] l1= l.toArray(new Joueur[0]);
        calculs.use_min_max(l1,1);
    }

}

