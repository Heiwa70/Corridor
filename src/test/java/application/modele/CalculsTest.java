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
        assertTrue(verifListPion1.size() == listeChoixPion1.size());
        for (int[] coords : listeChoixPion1) {
            assertTrue(verifListPion1.contains(coords[0] + ", " + coords[1]));
        }

        List<int[]> listeChoixPion2 = this.calculs.listeMouvementsPion(12, 8);
        assertTrue(verifListPion2.size() == listeChoixPion2.size());
        for (int[] coords : listeChoixPion2) {
            assertTrue(verifListPion2.contains(coords[0] + ", " + coords[1]));
        }
    }

    @Test
    public void testEmplacementSurPlateau() {
        assertTrue(this.calculs.testEmplacementSurPlateau(5, 5));
        assertFalse(this.calculs.testEmplacementSurPlateau(-1, -1));
        assertFalse(this.calculs.testEmplacementSurPlateau(-1, 0));
        assertFalse(this.calculs.testEmplacementSurPlateau(0, -1));
        assertFalse(this.calculs.testEmplacementSurPlateau(100, 100));
        assertFalse(this.calculs.testEmplacementSurPlateau(100, 0));
        assertFalse(this.calculs.testEmplacementSurPlateau(0, 100));
    }

    @Test
    public void testCase() {
        assertTrue(this.calculs.testCase(0, 0, Val.CASEPION));
        assertFalse(this.calculs.testCase(0, 0, Val.CASEMURS));
    }

    @Test
    public void dijkstra() {

        assertTrue(this.calculs.dijkstra(8, 4, 2) == 8);
        assertTrue(this.calculs.dijkstra(12, 8, 2) == -1);
    }

    @Test
    public void exist_chemin() {
        Log.info("Calculs test", "exist_chemin 9x9x4*10");
        double somm = 0;
        int nbr = 100;
        for (int i = 0; i < nbr; i++) {
            long tempsDebut = System.nanoTime();
            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    for (int id = 1; id <= 4; id++) {
                        this.calculs.exist_chemin(x, y, id);
                    }
                }
            }
            somm+=System.nanoTime() - tempsDebut;
        }
        Log.info("Calculs test", "Fin exist_chemin 9x9x4*10 : "+somm/(nbr*1000)+"us");
        assertTrue(this.calculs.exist_chemin(8, 4, 2));
        assertFalse(this.calculs.exist_chemin(12, 8, 2));
    }

    private void generation(String nomSauvegarde) {
        Object[] data = this.gestionSauvegardes.chargement(nomSauvegarde);
        this.plateau = (Plateau) data[0];
    }
}