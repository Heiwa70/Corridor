package application.modele;

import application.controleur.Plateau;

import java.util.ArrayList;
import java.util.List;

public abstract class Calculs {

    protected Plateau plateau;

    /**
     * Récupère la liste des mouvements possible d'une case pion.
     *
     * @param x
     * @param y
     * @return
     */
    public List<int[]> listeMouvementsPion(int x, int y) {

        List<int[]> possibilites = new ArrayList<>();

        if (testCase(x, y, Val.__PION__)) {

            int[][] vec = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            for (int[] i : vec) {

                int vecX = i[0];
                int vecY = i[1];

                if (testCase(x + vecX, y + vecY, Val.CASEMURS)) {
                    continue;
                }
                if (testCase(x + 2 * vecX, y + 2 * vecY, Val.__PION__)) {
                    if (!testCase(x + 3 * vecX, y + 3 * vecY, Val.CASEMURS)) {
                        consitionAjoutPossibilite(possibilites, x + 4 * vecX, y + 4 * vecY);
                    } else {
                        if (vecX == 0) {
                            if (testCase(x - 1, y + 2 * vecY, Val.CASEMURS)) {
                                consitionAjoutPossibilite(possibilites, x - 2, y + 2 * vecY);
                            }
                            if (testCase(x + 1, y + 2 * vecY, Val.CASEMURS)) {
                                consitionAjoutPossibilite(possibilites, x + 2, y + 2 * vecY);
                            }
                        } else {
                            if (testCase(x + 2 * vecX, y - 1, Val.CASEMURS)) {
                                consitionAjoutPossibilite(possibilites, x + 2 * vecX, y - 2);
                            }
                            if (testCase(x + 2 * vecX, y + 1, Val.CASEMURS)) {
                                consitionAjoutPossibilite(possibilites, x + 2 * vecX, y + 2);
                            }
                        }

                    }
                } else {
                    ajoutPossibilite(possibilites, x + 2 * vecX, y + 2 * vecY);
                }
            }
        }
        return possibilites;
    }

    private void ajoutPossibilite(List<int[]> possibilites, int x, int y) {
        if (testEmplacementSurPlateau(x, y)) {
            possibilites.add(new int[]{x, y});
        }
    }

    private void consitionAjoutPossibilite(List<int[]> possibilites, int x, int y) {
        if (testCase(x, y, Val.CASEPION)) {
            ajoutPossibilite(possibilites, x, y);
        }
    }

    public boolean testEmplacementSurPlateau(int x, int y) {
        return 0 <= x && x < this.plateau.getWidth() && 0 <= y && y < this.plateau.getHeight();
    }

    public boolean testCase(int x, int y, Val type) {
        Emplacement laCase = this.plateau.getEmplacement(x, y);
        if (laCase != null) {
            return laCase.getValeur() == type;
        }
        return false;
    }

    public int dijkstra(int x, int y, int idjoueur) {
        int i = 0;
        ArrayList<String> P = new ArrayList<String>();
        ArrayList<Integer> FileX = new ArrayList<Integer>();
        ArrayList<Integer> FileY = new ArrayList<Integer>();
        FileX.add(x);
        FileY.add(y);
        while (!(FileX.isEmpty())) {
            int x_courant = FileX.get(FileX.size() - 1);
            FileX.remove(FileX.size() - 1);
            int y_courant = FileY.get(FileY.size() - 1);
            FileY.remove(FileY.size() - 1);
            if ((idjoueur == 1 && y_courant == 16) || (idjoueur == 2 && y_courant == 0) || (idjoueur == 3 && x_courant == 0) || (idjoueur == 4 && x_courant == 16)) {
                return i;
            }
            i++;
            for (int[] coup : listeMouvementsPion(x_courant, y_courant)) {
                if (!P.contains(x_courant + " " + y_courant)) {
                    FileX.add(coup[0]);
                    FileY.add(coup[1]);
                    P.add(x_courant + " " + y_courant);
                }
            }
        }
        return -1;
    }

    private boolean exist_recursif(int x, int y, int idjoueur, ArrayList<String> noeuds_vus) {
        if ((idjoueur == 1 && y == 16) || (idjoueur == 2 && y == 0) || (idjoueur == 3 && x == 0) || (idjoueur == 4 && x == 16)) {
            return true;
        }
        noeuds_vus.add(x + " " + y);
        boolean b = false;
        for (int[] coup : listeMouvementsPion(x, y)) {
            if (noeuds_vus.contains(x + " " + y)) {
                b = b || exist_recursif(coup[0], coup[1], idjoueur, noeuds_vus);
            }
        }
        return b;
    }

    public boolean exist_chemin(int x, int y, int idjoueur) {
        //parcours en profondeur
        return exist_recursif(x, y, idjoueur, new ArrayList<String>());
    }
}
