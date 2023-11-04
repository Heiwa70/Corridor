package application.modele;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PionTest {

    private Pion pion;
    private final String couleur = "bleu";
    private final Emplacement emplacement = new Emplacement(0,0,Val.__VIDE__);


    @Before
    public void setUp() throws Exception {
        this.pion = new Pion(this.couleur, this.emplacement);
    }

    @After
    public void tearDown() throws Exception {
        this.pion = null;
    }

    @Test
    public void getCouleur() {
        assertSame(this.pion.getCouleur(), this.couleur);
        assertNotSame("autre", this.pion.getCouleur());
    }

    @Test
    public void setCouleur() {
        assertSame(this.pion.getCouleur(), this.couleur);
        assertNotSame("autre", this.pion.getCouleur());
        this.pion.setCouleur("autre");
        assertNotSame(this.pion.getCouleur(), this.couleur);
        assertSame("autre", this.pion.getCouleur());
    }

    @Test
    public void getEmplacement() {
        assertSame(this.pion.getEmplacement(), this.emplacement);
        assertNotSame(new Emplacement(0,0,Val.__PION__), this.pion.getEmplacement());
    }

    @Test
    public void setEmplacement() {
        Emplacement autre = new Emplacement(0,0,Val.__PION__);
        assertEquals(this.pion.getEmplacement(), this.emplacement);
        assertNotEquals(autre, this.pion.getEmplacement());
        this.pion.setCouleur("autre");
        assertNotEquals(this.pion.getEmplacement(), this.emplacement);
        assertEquals(autre, this.pion.getEmplacement());
    }

    @Test
    public void testToString() {
        String text = "0 : 0 : __VIDE__";
        assertEquals(this.pion.toString(), text);
        assertNotEquals("1 : 0 : __VIDE__", this.pion.toString());
    }
}