package application.modele;

public class Emplacement {
    private int x;
    private int y;
    private boolean occupation;

    public Emplacement(int x, int y){
        setX(x);
        setY(y);
        this.occupation = false;
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

    public boolean getOccupation() {
        return occupation;
    }

    public void setOccupation(boolean occupation) {
        this.occupation = occupation;
    }
}
