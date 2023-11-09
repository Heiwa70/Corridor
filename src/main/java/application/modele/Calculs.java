package application.modele;

import application.controleur.Plateau;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Calculs {

    private Plateau plateau;
    private int width;
    private int height;
    private final int[] listeFin = {0, 0, 16, 0, 16};

    public Calculs(Plateau plateau) {
        setPlateau(plateau);
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
        this.width = this.plateau.getWidth();
        this.height = this.plateau.getHeight();
    }

    /**
     * Récupère la liste des mouvements possible d'une case pion.
     *
     * @param x
     * @param y
     * @return
     */
    public List<int[]> listeMouvementsPion(int x, int y, boolean mode) {
        if (mode) {
            if (testCase(x, y, Val.__PION__)) {
                return listeMouvementsPion(x, y);
            }
        } else {
            return listeMouvementsPion(x, y);
        }
        return null;
    }

    public List<int[]> listeMouvementsPion(int x, int y) {

        List<int[]> possibilites = new ArrayList<>();

        int[][] vec = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int vecX = 0;
        int vecY = 0;
        for (int[] i : vec) {

            vecX = i[0];
            vecY = i[1];

            if (testCase(x + vecX, y + vecY, Val.__MURS__)) {
                continue;
            }
            if (testCase(x + 2 * vecX, y + 2 * vecY, Val.__PION__)) {
                if (!testCase(x + 3 * vecX, y + 3 * vecY, Val.__MURS__)) {
                    consitionAjoutPossibilite(possibilites, x + 4 * vecX, y + 4 * vecY);
                } else {
                    if (vecX == 0) {
                        y=y + 2 * vecY;
                        if (!testCase(x - 1, y , Val.__MURS__)) {
                            consitionAjoutPossibilite(possibilites, x - 2, y);
                        }
                        if (!testCase(x + 1, y, Val.__MURS__)) {
                            consitionAjoutPossibilite(possibilites, x + 2, y);
                        }
                    } else {
                        x = x + 2 * vecX;
                        if (!testCase(x, y - 1, Val.__MURS__)) {
                            consitionAjoutPossibilite(possibilites, x, y - 2);
                        }
                        if (!testCase(x, y + 1, Val.__MURS__)) {
                            consitionAjoutPossibilite(possibilites, x, y + 2);
                        }
                    }
                }
            } else {
                y=y + 2 * vecY;
                x = x + 2 * vecX;
                if (testEmplacementSurPlateau(x, y)) {
                    possibilites.add(new int[]{x, y});
                }
            }
        }

        return possibilites;
    }

    private void consitionAjoutPossibilite(List<int[]> possibilites, int x, int y) {
        if (testCase(x, y, Val.CASEPION)) {
            possibilites.add(new int[]{x, y});
        }
    }

    public boolean testEmplacementSurPlateau(int x, int y) {
        if(0 > x){
            return false;
        }else if(x >= this.width){
            return false;
        }else if(0 > y){
            return false;
        }else if(y >= this.height){
            return false;
        }
        return true;
    }

    public boolean testCase(int x, int y, Val type) {
        if(testEmplacementSurPlateau(x,y)){
            Emplacement laCase = this.plateau.getEmplacement(x, y);
            if (laCase != null) {
                return laCase.getValeur() == type;
            }
        }
        return false;
    }

    public int dijkstra(int x, int y, int idjoueur) {
        Log.info("Calculs", "Recherche distance chemin joueur : " + idjoueur + " , x = " + x + ", y = " + y);
        int x_courant = 0;
        int y_courant = 0;
        int pronfondeurCourant = 0;
        HashSet<String> P = new HashSet<String>();
        LinkedList<Integer> FileX = new LinkedList<Integer>();
        LinkedList<Integer> FileY = new LinkedList<Integer>();
        LinkedList<Integer> FileProfondeur = new LinkedList<Integer>();

        FileX.add(x);
        FileY.add(y);
        FileProfondeur.add(0);
        while (!(FileX.isEmpty())) {
            x_courant = FileX.removeFirst();
            y_courant = FileY.removeFirst();
            pronfondeurCourant = FileProfondeur.removeFirst();
            if ((idjoueur == 1 && y_courant == 0) || (idjoueur == 2 && y_courant == 16) || (idjoueur == 3 && x_courant == 0) || (idjoueur == 4 && x_courant == 16)) {
                Log.info("Calculs", "Nombre de coups : " + pronfondeurCourant);
                return pronfondeurCourant;
            }
            boolean valide = false;
            for (int[] coup : listeMouvementsPion(x_courant, y_courant)) {
                if (!P.contains(x_courant + " " + y_courant)) {
                    FileX.addLast(coup[0]);
                    FileY.addLast(coup[1]);
                    FileProfondeur.addLast(pronfondeurCourant + 1);
                    valide = true;
                }
            }
            if (valide) {
                P.add(x_courant + " " + y_courant);
            }
        }
        Log.info("Calculs", "Chemin introuvable.");
        return -1;
    }


    private boolean exist_recursif(int x, int y, int idjoueur, HashSet<String> noeuds_vus) {
        if ((idjoueur < 3 ? y == this.listeFin[idjoueur] : x == this.listeFin[idjoueur]) ){
            return true;
        }
        noeuds_vus.add(x + " " + y);
        for (int[] coup : listeMouvementsPion(x, y)) {
            if (!noeuds_vus.contains(coup[0] + " " + coup[1])) {
                if (exist_recursif(coup[0], coup[1], idjoueur, noeuds_vus)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean exist_chemin(int x, int y, int idjoueur) {
        //parcours en profondeur
        if (exist_recursif(x, y, idjoueur, new HashSet<String>())) {
            Log.info("Calculs", "Chemin qui existe.");
            return true;
        }
        ;
        Log.info("Calculs", "Chemin introuvable.");
        return false;
    }
}
