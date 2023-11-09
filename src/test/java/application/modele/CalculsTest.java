package application.modele;

import application.controleur.Plateau;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
public class CalculsTest {

    private Calculs calculs;
    private GestionSauvegardes gestionSauvegardes;
    private Plateau plateau;

    @Before
    public void setUp() throws Exception {
        this.gestionSauvegardes = new GestionSauvegardes("src//test//java//ressources//sauvegardes//");
        generation("test");
        this.calculs = new Calculs(this.plateau);
    }

    @After
    public void tearDown() throws Exception {
        this.plateau = null;
        this.gestionSauvegardes = null;
        this.calculs = null;
    }

    @Test
    public void listeMouvementsPion() {

        List<String> verifListPion1 = new ArrayList<>();
        verifListPion1.add("10, 4");
        verifListPion1.add("6, 4");
        verifListPion1.add("8, 2");

        List<String> verifListPion2 = new ArrayList<>();
        verifListPion2.add("10, 8");
        verifListPion2.add("12, 10");

        List<int[]> listeChoixPion1 = this.calculs.listeMouvementsPion(8, 4);
        assertTrue(verifListPion1.size()==listeChoixPion1.size());
        for(int[] coords : listeChoixPion1){
            assertTrue(verifListPion1.contains(coords[0]+", "+coords[1]));
        }

        List<int[]> listeChoixPion2 = this.calculs.listeMouvementsPion(12, 8);
        assertTrue(verifListPion2.size()==listeChoixPion2.size());
        for(int[] coords : listeChoixPion2){
            assertTrue(verifListPion2.contains(coords[0]+", "+coords[1]));
        }
    }

    @Test
    public void testEmplacementSurPlateau() {
        assertTrue(this.calculs.testEmplacementSurPlateau(5,5));
        assertFalse(this.calculs.testEmplacementSurPlateau(-1,-1));
        assertFalse(this.calculs.testEmplacementSurPlateau(-1,0));
        assertFalse(this.calculs.testEmplacementSurPlateau(0,-1));
        assertFalse(this.calculs.testEmplacementSurPlateau(100,100));
        assertFalse(this.calculs.testEmplacementSurPlateau(100,0));
        assertFalse(this.calculs.testEmplacementSurPlateau(0,100));
    }

    @Test
    public void testCase() {
        assertTrue(this.calculs.testCase(0, 0, Val.CASEPION));
        assertFalse(this.calculs.testCase(0, 0, Val.CASEMURS));
    }

    @Test
    public void dijkstra() {
        int a,b;
        a = this.calculs.dijkstra(8, 4, 1);
        b = this.calculs.dijkstra(12, 8, 1);
        int c;
    }

    @Test
    public void exist_chemin() {
        HashSet<String> noeuds_vus = new HashSet<>();
        HashSet<String> noeuds_vus2 = new HashSet<>();
        boolean a,b;
        a = this.calculs.exist_chemin(8, 4, 1);
        b = this.calculs.exist_chemin(12, 8, 2);
        int c;
    }

    private void generation(String nomSauvegarde){
        Object[] data = this.gestionSauvegardes.chargement(nomSauvegarde);
        this.plateau = (Plateau) data[0];
    }
}