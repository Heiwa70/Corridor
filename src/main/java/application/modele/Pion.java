package application.modele;

public class Pion {
    private String couleur;
    private Emplacement emplacement;

    public Pion(String couleur, Emplacement emplacement){
        setCouleur(couleur);
        setEmplacement(emplacement);
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Emplacement getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    public String toString(){
        return this.emplacement.toString();
    }
}
