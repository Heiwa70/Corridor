package application.modele;

import application.controleur.Plateau;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Calculssanstest {

    private static Calculs calculs;
    private static GestionSauvegardes gestionSauvegardes;
    private static Plateau plateau;
    private static Set<Joueur> liste_joueur;


    public static void main(String[] args) {
        gestionSauvegardes = new GestionSauvegardes("src//test//java//ressources//sauvegardes//");
        generation("test3");
        calculs = new Calculs(plateau);
        use_min_max();
        //use_filtre_mur();
    }

    
    public void listeMouvementsPion() {

        List<String> verifListPion1 = new ArrayList<>();
        verifListPion1.add("10, 4");
        verifListPion1.add("6, 4");
        verifListPion1.add("8, 2");

        List<String> verifListPion2 = new ArrayList<>();
        verifListPion2.add("10, 8");
        verifListPion2.add("12, 10");

        List<int[]> listeChoixPion1 = calculs.listeMouvementsPion(8, 4);
        assertTrue(verifListPion1.size() == listeChoixPion1.size());
        for (int[] coords : listeChoixPion1) {
            assertTrue(verifListPion1.contains(coords[0] + ", " + coords[1]));
        }

        List<int[]> listeChoixPion2 = calculs.listeMouvementsPion(12, 8);
        assertTrue(verifListPion2.size() == listeChoixPion2.size());
        for (int[] coords : listeChoixPion2) {
            assertTrue(verifListPion2.contains(coords[0] + ", " + coords[1]));
        }
    }


    public void testEmplacementSurPlateau() {
        assertTrue(calculs.testEmplacementSurPlateau(5, 5));
        assertFalse(calculs.testEmplacementSurPlateau(-1, -1));
        assertFalse(calculs.testEmplacementSurPlateau(-1, 0));
        assertFalse(calculs.testEmplacementSurPlateau(0, -1));
        assertFalse(calculs.testEmplacementSurPlateau(100, 100));
        assertFalse(calculs.testEmplacementSurPlateau(100, 0));
        assertFalse(calculs.testEmplacementSurPlateau(0, 100));
    }

 
    public void testCase() {
        assertTrue(calculs.testCase(0, 0, Val.CASEPION));
        assertFalse(calculs.testCase(0, 0, Val.CASEMURS));
    }


    public void dijkstra() {

        assertTrue(calculs.dijkstra(8, 4, 2) == 8);
        assertTrue(calculs.dijkstra(12, 8, 2) == -1);
    }

   
    public void exist_chemin() {
        Log.info("Calculs test", "exist_chemin 9x9x4*10");
        double somm = 0;
        int nbr = 100;
        for (int i = 0; i < nbr; i++) {
            long tempsDebut = System.nanoTime();
            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    for (int id = 1; id <= 4; id++) {
                        calculs.exist_chemin(x, y, id);
                    }
                }
            }
            somm+=System.nanoTime() - tempsDebut;
        }
        Log.info("Calculs test", "Fin exist_chemin 9x9x4*10 : "+somm/(nbr*1000)+"us");
        assertTrue(calculs.exist_chemin(8, 4, 2));
        assertFalse(calculs.exist_chemin(12, 8, 2));
    }

    private static void generation(String nomSauvegarde) {
        Object[] data = gestionSauvegardes.chargement(nomSauvegarde);
        plateau = (Plateau) data[0];
        Set<Joueur> a = ((HashMap<Joueur, Integer>) data[1]).keySet();
        liste_joueur=a;
        System.out.println(a.toArray()[0]);
    }
   
    public void liste_coup_mur(){
        //calculs.liste_coup_mur()
        Joueur j1= (Joueur) liste_joueur.toArray()[0];
        Joueur j2= (Joueur) liste_joueur.toArray()[1];
        ArrayList<Joueur> l=new ArrayList<Joueur>();
        l.add(j1);
        l.add(j2);
        Joueur[] l1= l.toArray(new Joueur[0]);
        ArrayList<String> coup=calculs.liste_coup_mur(j1.getX(),j1.getY(), l1);
        System.out.println(coup.size());

    }
  
    public static void use_min_max(){
        Joueur j1= (Joueur) liste_joueur.toArray()[0];
        Joueur j2= (Joueur) liste_joueur.toArray()[1];
        ArrayList<Joueur> l=new ArrayList<Joueur>();
        l.add(j1);
        l.add(j2);
        Joueur[] l1= l.toArray(new Joueur[0]);
        calculs.use_min_max(l1, 2,7);

    }
    public static void use_filtre_mur(){
        Joueur j1= (Joueur) liste_joueur.toArray()[0];
        Joueur j2= (Joueur) liste_joueur.toArray()[1];
        ArrayList<Joueur> l=new ArrayList<Joueur>();
        l.add(j1);
        l.add(j2);
        Joueur[] l1= l.toArray(new Joueur[0]);
        ArrayList<String> r=calculs.filtreliste_coup_mur(calculs.liste_coup_mur(j1.getX(),j1.getY(),l1),l1,1);
        System.out.println(r);
    }
}