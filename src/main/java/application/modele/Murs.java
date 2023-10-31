package application.modele;

import java.util.ArrayList;

public class Murs {
    private ArrayList<Emplacement> casesPrisent;

    public Murs(){
        this.casesPrisent = new ArrayList<>();
    }

    public void setPosition(Emplacement case1, Emplacement case2){

        this.casesPrisent.add(case1);
        this.casesPrisent.add(case2);
    }
}