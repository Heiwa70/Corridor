package application.controleur;

import application.modele.Emplacement;

import java.util.ArrayList;

public class Plateau {
    public final int VALCASEPION = 1, VALPION = 2, VALCASEMURS =1111111, VALMURS=0, VALVIDE=9999999;
    private ArrayList<ArrayList<Emplacement>> listeToutesLesCases;

    public Plateau(int width, int height){
        this.listeToutesLesCases = new ArrayList<>();
        initialialisationPlateau(width, height);
    }

    private void initialialisationPlateau(int width, int height) {

        for (int y = 0; y < height*2-1; y++) {
            ArrayList<Emplacement> ligne = new ArrayList<>();
            for (int x = 0; x < width*2-1; x++) {
                ligne.add(new Emplacement(x, y,y%2==0? (x%2==0?this.VALCASEPION:this.VALMURS) : (x%2==0?this.VALMURS:this.VALVIDE)));
            }
            this.listeToutesLesCases.add(ligne);
        }
    }

    public Emplacement getEmplacementCasePion(int x, int y){
        if(0<=x && x<getWidth() && 0<=y && y<getHeight()){
            return this.listeToutesLesCases.get(y*2).get(x*2);
        }
        return null;
    }

    public Emplacement getEmplacement(int x, int y){
        if(0<=x && x<getWidth()  && 0<=y && y<getHeight() ){
            return this.listeToutesLesCases.get(y).get(x);
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
        return this.listeToutesLesCases.get(0).size();
    }

    public int getHeight() {
        return this.listeToutesLesCases.size();
    }

    public String toString(boolean vueValeur){
        StringBuilder plateauEnText = new StringBuilder();
        int y = 0;
        if(!vueValeur){
            int x = 0;

            plateauEnText.append("          ");
            for(Emplacement emplacement : this.listeToutesLesCases.get(0)) {
                if (y == 0) {
                    plateauEnText.append("    "+((""+x).length()==1?x+" ":x)+"   ");
                }
                x++;
            }
            plateauEnText.append("\n");
        }


        for(ArrayList<Emplacement> ligne : this.listeToutesLesCases){
            if(!vueValeur) {
                plateauEnText.append("    " + (("" + y).length() == 1 ? y + " " : y) + "    ");
            }
            for(Emplacement emplacement : ligne){

                String valeur = emplacement.toString();
                if(vueValeur){
                    plateauEnText.append(emplacement.getValeur()!=this.VALVIDE?"|   "+valeur+"   |":"|"+valeur+"|");
                }else{
                    valeur = valeur.equals("2")?"O":valeur.equals("1")?" ":valeur.equals("0")?"=":valeur;
                    plateauEnText.append(emplacement.getValeur()!=this.VALVIDE?"|   "+valeur+"   |":"|"+valeur+"|");
                }
             }
            plateauEnText.append("\n");
            y++;
        }
        return plateauEnText.toString();
    }
}
