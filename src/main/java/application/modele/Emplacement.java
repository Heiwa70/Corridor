package application.modele;

public class Emplacement {
    private int x;
    private int y;
    private int valeur;

    public Emplacement(int x, int y, int valeur){
        setX(x);
        setY(y);
        setValeur(valeur);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public String toString(){
        return ""+getValeur();
    }
}
