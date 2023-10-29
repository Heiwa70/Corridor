package application.modele;

import java.util.ArrayList;

public class Plateau {
    private int width, height;
    private ArrayList<ArrayList<Emplacement>> listeCasesPion;
    private ArrayList<ArrayList<Emplacement>> listeEmplacementsHorizontale;
    private ArrayList<ArrayList<Emplacement>> listeEmplacementsVerticale;

    public Plateau(int width, int height){
        setWidth(width);
        setHeight(height);
        initialialisationPlateau();
    }

    private void initialialisationPlateau() {
        remplissage(this.listeCasesPion, this.width, this.height);
        remplissage(this.listeEmplacementsHorizontale, this.width, this.height-1);
        remplissage(this.listeEmplacementsVerticale, this.width-1, this.height);
    }

    private void remplissage(ArrayList<ArrayList<Emplacement>> liste2D, int width, int height) {
        if(liste2D.size()!=0){
            liste2D.clear();
        }
        for (int y = 0; y < height; y++) {
            ArrayList<Emplacement> ligne = new ArrayList<>();
            for (int x = 0; x < width; x++) {
                ligne.add(new Emplacement(x, y));
            }
            liste2D.add(ligne);
        }
    }

    public Emplacement recupererEmplacementCasePion(int x, int y){
        return this.listeCasesPion.get(y).get(x);
    }

    public Emplacement recupererEmplacementHorizontalMurs(int x, int y){
        return this.listeEmplacementsHorizontale.get(y).get(x);
    }

    public Emplacement recupererEmplacementVerticaleMurs(int x, int y){
        return this.listeEmplacementsVerticale.get(y).get(x);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
