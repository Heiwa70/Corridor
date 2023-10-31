package application.controleur;

import application.modele.Emplacement;

import java.util.ArrayList;

public class Plateau {
    private int width, height;
    public final int VALCASEPION = 1, VALPION = 2, VALCASEMURS =1111111, VALMURS=0, VALVIDE=9999999;
    private ArrayList<ArrayList<Emplacement>> listeToutesLesCases;

    public Plateau(int width, int height){
        this.listeToutesLesCases = new ArrayList<>();
        setWidth(width);
        setHeight(height);
        initialialisationPlateau();
    }

    private void initialialisationPlateau() {

        for (int y = 0; y < this.height*2-1; y++) {
            ArrayList<Emplacement> ligne = new ArrayList<>();
            for (int x = 0; x < this.width*2-1; x++) {
                ligne.add(new Emplacement(x, y,y%2==0? (x%2==0?this.VALCASEPION:this.VALMURS) : (x%2==0?this.VALMURS:this.VALVIDE)));
            }
            this.listeToutesLesCases.add(ligne);
        }
    }

    public Emplacement getEmplacementCasePion(int x, int y){
        if(0<=x && x<this.width*2-1 && 0<=y && y<this.height*2-1){
            return this.listeToutesLesCases.get(y*2).get(x*2);
        }
        return null;
    }

    public String getTypeEmplacement(int x, int y){
        String resultat = "";
        switch(this.listeToutesLesCases.get(y).get(x).getValeur()){
            case VALCASEPION:
                resultat = "case pion";
                break;
            case VALPION:
                resultat = "pion";
                break;
            case VALCASEMURS:
                resultat = "case murs";
                break;
            case VALMURS:
                resultat = "murs";
            break;
            case VALVIDE:
                resultat = "case interdite";
                break;
            default:
                resultat = "NULL";
                break;
        }
        return resultat;
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

    public String toString(){
        StringBuilder plateauEnText = new StringBuilder();
        for(ArrayList<Emplacement> ligne : this.listeToutesLesCases){
            for(Emplacement emplacement : ligne){
                String valeur = emplacement.toString();
                plateauEnText.append(emplacement.getValeur()!=this.VALVIDE?"|   "+valeur+"   |":"|"+valeur+"|");
            }
            plateauEnText.append("\n");
        }
        return plateauEnText.toString();
    }
}
