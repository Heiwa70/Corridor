package application.modele;

public class Wall {
    Int_couple lowcase;
    Int_couple highcase;
    boolean vertival;

    public Wall(Int_couple lowcase, Int_couple highcase, boolean vertival) {
        this.lowcase = lowcase;
        this.highcase = highcase;
        this.vertival = vertival;
    }

    public Int_couple getLowcase() {
        return lowcase;
    }

    public void setLowcase(Int_couple lowcase) {
        this.lowcase = lowcase;
    }

    public Int_couple getHighcase() {
        return highcase;
    }

    public void setHighcase(Int_couple highcase) {
        this.highcase = highcase;
    }

    public boolean isVertival() {
        return vertival;
    }

    public void setVertival(boolean vertival) {
        this.vertival = vertival;
    }
}
