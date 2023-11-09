package application.modele;

import application.controleur.Plateau;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculsTest {

    private Calculs calculs;
    private GestionSauvegardes gestionSauvegardes;

    @Before
    public void setUp() throws Exception {
        this.calculs = new Calculs(new Plateau(9, 9));
        this.gestionSauvegardes = new GestionSauvegardes();
    }

    @After
    public void tearDown() throws Exception {
        this.calculs = null;
    }

    @Test
    public void listeMouvementsPion() {

    }

    @Test
    public void testEmplacementSurPlateau() {
    }

    @Test
    public void testCase() {
    }

    @Test
    public void dijkstra() {
    }

    @Test
    public void exist_chemin() {
    }

  //  private void generation(String )
}