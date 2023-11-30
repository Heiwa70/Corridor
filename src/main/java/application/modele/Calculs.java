/**
 * Classe Calculs écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.modele;

import application.controleur.Plateau;

import java.util.*;

/**
 * La classe Calculs permet de trouver et tester les actions des joueurs et du bot.
 */
public class Calculs {

    private Plateau plateau;
    private int width;
    private int height;
    private int[] listeFin;
    private int[][][] listeVecteurs;
    private int idJoueurActuel;
    private StringBuilder coordinates;

    /**
     * Le constructeur initialise les cases de fin pour chaque joueur.
     *
     * @param plateau Plateau
     */
    public Calculs(Plateau plateau) {
        setPlateau(plateau);
        this.listeFin = new int[]{0, 0, 16, 0, 16};
        this.listeVecteurs = new int[][][]{
                {{0, -1}, {1, 0}, {-1, 0}, {0, 1}},
                {{0, 1}, {-1, 0}, {1, 0}, {0, -1}},

                {{1, 0}, {0, 1}, {0, -1}, {-1, 0}},
                {{-1, 0}, {0, -1}, {0, 1}, {1, 0}}
        };
        this.idJoueurActuel = 1;
        this.coordinates = new StringBuilder();
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
     * Récupère la liste des mouvements possible d'une case pion pour un joueur précis.
     *
     * @param x int
     * @param y int
     * @return List<int [ ]>
     */
    public ArrayList<Integer[]> listeMouvementsPion(int x, int y, int idJoueur) {
        this.idJoueurActuel = idJoueur;
        return listeMouvementsPion(x, y);
    }

    /**
     * Récupère la liste des mouvements possible d'une case pion.
     *
     * @param x int
     * @param y int
     * @return List<int [ ]>
     */
    public ArrayList<Integer[]> listeMouvementsPion(int x, int y) {

        ArrayList<Integer[]>possibilites = new ArrayList<>();
        boolean b;
        int xx, yy;

        int[][] vec = this.listeVecteurs[this.idJoueurActuel - 1];
        for (int[] i : vec) {

            int vecX = i[0];
            int vecY = i[1];

            if (testCase(x + vecX, y + vecY, Val.__MURS__)) {
                continue;
            } else if (testCase(x + 2 * vecX, y + 2 * vecY, Val.__PION__)) {
                if (!testCase(x + 3 * vecX, y + 3 * vecY, Val.__MURS__) && testEmplacementSurPlateau(x + 3 * vecX, y + 3 * vecY)) {
                    consitionAjoutPossibilite(possibilites, x + 4 * vecX, y + 4 * vecY);
                } else {
                    if (vecX == 0) {
                        if (!testCase(x - 1, y + 2 * vecY, Val.__MURS__)) {
                            consitionAjoutPossibilite(possibilites, x - 2, y + 2 * vecY);
                        }
                        if (!testCase(x + 1, y + 2 * vecY, Val.__MURS__)) {
                            consitionAjoutPossibilite(possibilites, x + 2, y + 2 * vecY);
                        }
                    } else {
                        if (!testCase(x + 2 * vecX, y - 1, Val.__MURS__)) {
                            consitionAjoutPossibilite(possibilites, x + 2 * vecX, y - 2);
                        }
                        if (!testCase(x + 2 * vecX, y + 1, Val.__MURS__)) {
                            consitionAjoutPossibilite(possibilites, x + 2 * vecX, y + 2);
                        }
                    }
                }
            } else {
                xx = x + 2 * vecX;
                yy = y + 2 * vecY;
                if (testEmplacementSurPlateau(xx, yy)) {
                    if (testCase(xx, yy, Val.CASEPION)) {
                        possibilites.add(new Integer[]{xx, yy});
                    }
                }

            }
        }

        return possibilites;
    }

    /**
     * Vérifie si on peut ajouter le coups de déplacement.
     * @param possibilites List<int[]>
     * @param x int
     * @param y int
     */
    private void consitionAjoutPossibilite(ArrayList<Integer[]> possibilites, int x, int y) {
        if (testCase(x, y, Val.CASEPION)) {
            possibilites.add(new Integer[]{x, y});
        }
    }

    /**
     * Vérifie si les coordonnées ne dépacent pas les cases du plateau
     * @param x int
     * @param y int
     * @return Boolean
     */
    public boolean testEmplacementSurPlateau(int x, int y) {
        if (0 > x) {
            return false;
        } else if (x >= this.width) {
            return false;
        } else if (0 > y) {
            return false;
        } else if (y >= this.height) {
            return false;
        }
        return true;
    }

    /**
     * Vérifie la valeur de l'emplacement
     * @param x int
     * @param y int
     * @param type Val
     * @return boolean
     */
    public boolean testCase(int x, int y, Val type) {
        if (testEmplacementSurPlateau(x, y)) {
            Emplacement laCase = this.plateau.getEmplacement(x, y);
            if (laCase != null) {
                return laCase.getValeur() == type;
            }
        }
        return false;
    }
    /**
     * calcul le plus court chemin entre un joueur et une de ses cases gagnantes (retourne -1 s'il n'existe pas de chemin)
     * @param x int
     * @param y int
     * @param idjoueur int
     * @return int
     */
    public int dijkstra(int x, int y, int idjoueur) {
        //Log.info("Calculs", "Recherche distance chemin joueur : " + idjoueur + " , x = " + x + ", y = " + y);
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
                //Log.info("Calculs", "Nombre de coups : " + pronfondeurCourant);
                return pronfondeurCourant;
            }
            boolean valide = false;
            for (Integer[] coup : listeMouvementsPion(x_courant, y_courant, idjoueur)) {
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
        //Log.info("Calculs", "Chemin introuvable.");
        return -1;
    }

    /**
     * retourne l'existence d'un chemin entre un joueur et l'une de ses cases d'arrivées
     * @param x int
     * @param y int
     * @param noeuds_vus HashSet<String>
     * @return boolean
     */
    private boolean exist_recursif(int x, int y, HashSet<String> noeuds_vus) {
        if ((this.idJoueurActuel < 3 ? y == this.listeFin[this.idJoueurActuel] : x == this.listeFin[this.idJoueurActuel])) {
            return true;
        }

        this.coordinates.setLength(0);
        this.coordinates.append(x).append(" ").append(y);

        noeuds_vus.add(this.coordinates.toString());

        for (Integer[] coup : listeMouvementsPion(x, y)) {
            this.coordinates.setLength(0); // Réinitialiser le StringBuilder
            this.coordinates.append(coup[0]).append(" ").append(coup[1]);

            if (!noeuds_vus.contains(this.coordinates.toString())) {
                if (exist_recursif(coup[0], coup[1], noeuds_vus)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * retourne l'existence d'un chemin entre un joueur et l'une de ses cases d'arrivées
     * @param x int
     * @param y int
     * @param idJoueur int
     * @return boolean
     */
    public boolean exist_chemin(int x, int y, int idJoueur) {
        //parcours en profondeur
        this.idJoueurActuel = idJoueur;
        if (exist_recursif(x, y, new HashSet<String>())) {
//            //Log.info("Calculs", "Chemin qui existe.");
            return true;
        }

//        //Log.info("Calculs", "Chemin introuvable.");
        return false;
    }
    /**
     * retourne la liste de tout les coups légaux de mur d'un joueur
     * @param x int
     * @param y int
     * @param listeJoueurs HashMap<Integer,Joueur>
     * @param idJoueur int
     * @return ArrayList<String>
     */
    public ArrayList<Integer[]> liste_coup_mur(int x, int y, HashMap<Integer, Joueur> listeJoueurs, int idJoueur) {
        //Log.info("Calculs", "Debut generation mur");
        ArrayList<Integer[]> possibilite = new ArrayList<>();
        if (listeJoueurs.get(idJoueur).testSetMur()) {
            for (int i = 1; i < plateau.getHeight() - 1; i = i + 2) {
                for (int j = 0; j < plateau.getWidth() - 2; j++) {
                    //mur vertical
                    if (plateau.getEmplacement(i, j).getValeur() == Val.CASEMURS
                            && plateau.getEmplacement(i, j + 1).getValeur() == Val.__VIDE__
                            && plateau.getEmplacement(i, j + 2).getValeur() == Val.CASEMURS) {
                        plateau.getEmplacement(i, j).setValeur(Val.__MURS__);
                        plateau.getEmplacement(i, j + 1).setValeur(Val._OCCUPE_);
                        plateau.getEmplacement(i, j + 2).setValeur(Val.__MURS__);
                        boolean b = true;
                        for (Joueur joueur : listeJoueurs.values()) {
                            if (!exist_chemin(joueur.getX(), joueur.getY(), joueur.getId())) {
                                b = false;
                            }
                        }
                        if (b) {
                            possibilite.add(new Integer[]{i, j, i, (j + 1), i, (j + 2)});
                        }
                        plateau.getEmplacement(i, j).setValeur(Val.CASEMURS);
                        plateau.getEmplacement(i, j + 1).setValeur(Val.__VIDE__);
                        plateau.getEmplacement(i, j + 2).setValeur(Val.CASEMURS);
                    }
                    //mur horizontal
                    if (plateau.getEmplacement(j, i).getValeur() == Val.CASEMURS
                            && plateau.getEmplacement(j + 1, i).getValeur() == Val.__VIDE__
                            && plateau.getEmplacement(j + 2, i).getValeur() == Val.CASEMURS) {
                        plateau.getEmplacement(j, i).setValeur(Val.__MURS__);
                        plateau.getEmplacement(j + 1, i).setValeur(Val._OCCUPE_);
                        plateau.getEmplacement(j + 2, i).setValeur(Val.__MURS__);
                        boolean b = true;
                        for (Joueur joueur : listeJoueurs.values()) {
                            if (!exist_chemin(joueur.getX(), joueur.getY(), joueur.getId())) {
                                b = false;
                            }
                        }
                        if (b) {
                            possibilite.add(new Integer[]{j,i,(j + 1),i,(j + 2),i});
                        }
                        plateau.getEmplacement(j, i).setValeur(Val.CASEMURS);
                        plateau.getEmplacement(j + 1, i).setValeur(Val.__VIDE__);
                        plateau.getEmplacement(j + 2, i).setValeur(Val.CASEMURS);
                    }

                }
            }
        }
        //Log.info("Calculs", "Fin generation mur");
        return possibilite;
    }
    /**
     * retourne un nombre de mur de manière aléatoire
     * @param L ArrayList<String>
     * @return ArrayList<String>
     */
    public ArrayList<String> filtreliste_coup_murrandom(ArrayList<String> L) {
        ArrayList<String> result = new ArrayList<>();
        Random random = new Random();
        int n = L.size();
        if (n == 0) {
            return result;
        }
        for (int i = 0; i < 4; i++) {
            int nombreAleatoire = random.nextInt(n);
            result.add(L.get(nombreAleatoire));
        }
        return result;
    }
    /**
     * retourne un nombre de mur de manière aléatoire
     * @param id int
     * @return int
     */
    public int inverse_id(int id) {
        return 3 - id;
    }

    /**
     * retourne le coup de mur maximisant le chemin de l'adversaire moins le sien
     * @param L ArrayList<String>
     * @param listejoueur HashMap<Integer, Joueur>
     * @param idJoueur int
     * @return ArrayList<String>
     */
    public ArrayList<Integer[]> filtreliste_coup_mur(ArrayList<Integer[]> L, HashMap<Integer, Joueur> listejoueur, int idJoueur) {
        int n = L.size();
        ArrayList<Integer[]> res = new ArrayList<>();
        int indice = 0;
        int distance = Integer.MIN_VALUE;
        if (n == 0) {
            return res;
        }
        int a,b;
        Integer[] c;
        Murs mur;
        Joueur joueur = listejoueur.get(idJoueur);
        for (int i = 0; i < n; i++) {
            c = L.get(i);
            mur = joueur.setMur(plateau.getEmplacement(c[0], c[1]), plateau.getEmplacement(c[2], c[3]), plateau.getEmplacement(c[4], c[5]));
            a = this.dijkstra(listejoueur.get(inverse_id(idJoueur)).getX(), listejoueur.get(inverse_id(idJoueur)).getY(), inverse_id(idJoueur));
            b = this.dijkstra(joueur.getX(), joueur.getY(), idJoueur);
            if (distance < a - b) {
                distance = a - b;
                indice = i;

            }
            joueur.undoSetMur(mur, plateau);
        }

        res.add(L.get(indice));
        return res;
    }

    /**
     * retourne le coup de mur maximisant le chemin de l'adversaire moins le sien
     * @param listeJoueur HashMap<Integer, Joueur>
     * @param idJoueur int
     * @return Integer
     */
    public Integer euristique(HashMap<Integer, Joueur> listeJoueur, int idJoueur) {
        Joueur joueur = listeJoueur.get(idJoueur);
        if (idJoueur == 2) {
            return dijkstra(joueur.getX(), joueur.getY(), idJoueur) - dijkstra(listeJoueur.get(inverse_id(idJoueur)).getX(), listeJoueur.get(inverse_id(idJoueur)).getY(), inverse_id(idJoueur));
        }
        else {
            return dijkstra(listeJoueur.get(inverse_id(idJoueur)).getX(), listeJoueur.get(inverse_id(idJoueur)).getY(), inverse_id(idJoueur))-dijkstra(joueur.getX(), joueur.getY(), idJoueur);
        }
    }

    /**
     * retourne un entier représentant la qualité d'un coup
     * @param listeJoueur HashMap<Integer, Joueur>
     * @param idJoueur int
     * @param profondeur int
     * @return Integer
     */
    public int min_max(int profondeur, HashMap<Integer, Joueur> listeJoueur, int idJoueur) {

        Joueur joueur = listeJoueur.get(idJoueur);

        if ((joueur.getId() == 1 && joueur.getY() == 0)) {
            //Log.info("Calculs","p:"+profondeur+" max");
            return Integer.MAX_VALUE;
        }
        else if (joueur.getId() == 2 && joueur.getY() == 16) {
            //Log.info("Calculs","p:"+profondeur+" min, joueur id:");
            //System.out.println("min : "+joueur.getId()+" y: "+ljoueur.getY()+" id joueur : "+idJoueur);
            return Integer.MIN_VALUE;
        }
        else if (profondeur == 0) {
            //Log.info("Calculs","p:"+profondeur+" valeur "+r);
            return euristique(listeJoueur, idJoueur);
        }

        ArrayList<Integer[]> coupspion = listeMouvementsPion(joueur.getX(), joueur.getY(), idJoueur);

        //coups pion
        int x = joueur.getX();
        int y = joueur.getY();

        if (joueur.getId() == 1) {
            int valeurMax = Integer.MIN_VALUE;
            for (Integer[] coup_pion : coupspion) {
                joueur.setPionSimple(plateau.getEmplacement(coup_pion[0], coup_pion[1]));
                joueur.unsetPion(plateau.getEmplacement(x, y));
                int valeur = min_max(profondeur - 1, listeJoueur, 2);
                valeurMax = valeurMax>valeur?valeurMax:valeur;
                joueur.unsetPion(plateau.getEmplacement(joueur.getX(), joueur.getY()));
                joueur.setPionSimple(plateau.getEmplacement(x, y));
            }
            //coups murs
            ArrayList<Integer[]> coups = filtreliste_coup_mur(liste_coup_mur(joueur.getX(), joueur.getY(), listeJoueur, idJoueur), listeJoueur, idJoueur);

            int valeur;
            Murs mur;
            for (Integer[] c : coups) {

                mur = joueur.setMur(plateau.getEmplacement(c[0], c[1]), plateau.getEmplacement(c[2], c[3]), plateau.getEmplacement(c[4], c[5]));
                valeur = min_max(profondeur - 1, listeJoueur, 2);
                valeurMax = valeurMax>valeur?valeurMax:valeur;
                joueur.undoSetMur(mur, plateau);

            }
            return valeurMax;
        } else {
            int valeurMin = Integer.MAX_VALUE;
            for (Integer[] coup_pion : coupspion) {
                joueur.setPionSimple(plateau.getEmplacement(coup_pion[0], coup_pion[1]));
                joueur.unsetPion(plateau.getEmplacement(x, y));
                int valeur = min_max(profondeur - 1, listeJoueur, 1);
                valeurMin = valeurMin<valeur?valeurMin:valeur;
                joueur.unsetPion(plateau.getEmplacement(joueur.getX(), joueur.getY()));
                joueur.setPionSimple(plateau.getEmplacement(x, y));
            }
            //coups murs
            ArrayList<Integer[]> coups = filtreliste_coup_mur(liste_coup_mur(joueur.getX(), joueur.getY(), listeJoueur, idJoueur), listeJoueur, idJoueur);
            int valeur;
            Murs mur;

            for (Integer[] c : coups) {
                mur = joueur.setMur(plateau.getEmplacement(c[0], c[1]), plateau.getEmplacement(c[2], c[3]), plateau.getEmplacement(c[4], c[5]));
                valeur = min_max(profondeur - 1, listeJoueur, 1);
                valeurMin = valeurMin<valeur?valeurMin:valeur;
                joueur.undoSetMur(mur, plateau);
            }
            return valeurMin;
        }
    }

    /**
     * joue le meilleur coup pour le joueur donné en indice en utilisant min_max
     * @param listeJoueur HashMap<Integer, Joueur>
     * @param idJoueur int
     * @param profondeur int
     */
    public void use_min_max(HashMap<Integer, Joueur> listeJoueur, int idJoueur, int profondeur) {

//        int profondeur = 1;
        Log.info("Calculs", "début min_max profondeur : " + profondeur);
        int[] meilleurCoupInt = null;
        Integer[] meilleurCoup = null;
        Joueur joueur = listeJoueur.get(idJoueur);
        if (idJoueur == 1) {
            int valeurMax = Integer.MIN_VALUE;
            ArrayList<Integer[]> coupspion = listeMouvementsPion(joueur.getX(),joueur.getY(), idJoueur);
            int x = joueur.getX();
            int y = joueur.getY();
            for (Integer[] coup_pion : coupspion) {
                joueur.setPionSimple(plateau.getEmplacement(coup_pion[0], coup_pion[1]));
                joueur.unsetPion(plateau.getEmplacement(x, y));
                int valeur = min_max(profondeur - 1, listeJoueur, 2);
                //Log.info("Calculs","valeur:"+valeur);
                if (valeur >= valeurMax) {
                    valeurMax = valeur;
                    meilleurCoup = coup_pion;
                }
                //Log.info("Calculs","(p)valeurmax "+valeurMax+", coup "+meilleurCoup);
                joueur.unsetPion(plateau.getEmplacement(joueur.getX(), joueur.getY()));
                joueur.setPionSimple(plateau.getEmplacement(x, y));
            }
            //coups murs
            ArrayList<Integer[]> coups = filtreliste_coup_mur(liste_coup_mur(joueur.getX(), joueur.getY(), listeJoueur, idJoueur),listeJoueur,idJoueur);
            Murs mur;
            int valeur;
            boolean choix = false;
            for (Integer[] c : coups) {
                mur = joueur.setMur(plateau.getEmplacement(c[0], c[1]), plateau.getEmplacement(c[2], c[3]), plateau.getEmplacement(c[4], c[5]));
                valeur = min_max(profondeur - 1, listeJoueur, 2);
                if (valeur > valeurMax) {
                    valeurMax = valeur;
                    meilleurCoup = c;
                    choix = true;
                }
                //Log.info("Calculs","(m)valeurmax "+valeur+", coup "+meilleurCoup);
                joueur.undoSetMur(mur, plateau);
            }
            if (choix) {
                Integer[] c = meilleurCoup;
                mur = joueur.setMur(plateau.getEmplacement(c[0], c[1]), plateau.getEmplacement(c[2], c[3]), plateau.getEmplacement(c[4], c[5]));
                Log.info("Calculs", "le meilleur coup est mur : " + c[0] + " " + c[1] + " et " + c[4] + " " + c[5] );
            } else {
                Integer[] tab = meilleurCoup;
                joueur.unsetPion(plateau.getEmplacement(joueur.getX(), joueur.getY()));
                joueur.setPionSimple(plateau.getEmplacement(tab[0], tab[1]));
                Log.info("Calculs", "le meilleur coup est pion : " + tab[0] + " " + tab[1]);
            }
        } else {
            int valeurMin = Integer.MAX_VALUE;
            ArrayList<Integer[]> coupspion = listeMouvementsPion(joueur.getX(), joueur.getY(), idJoueur);
            int x = joueur.getX();
            int y = joueur.getY();
            for (Integer[] coup_pion : coupspion) {
                joueur.setPionSimple(plateau.getEmplacement(coup_pion[0], coup_pion[1]));
                joueur.unsetPion(plateau.getEmplacement(x, y));
                int valeur = min_max(profondeur - 1, listeJoueur, 1);
                if (valeur < valeurMin) {
                    valeurMin = valeur;
                    meilleurCoup = coup_pion;
                }
                Log.info("Calculs", "(p)valeur " + valeur + ", coup " + meilleurCoup);
                joueur.unsetPion(plateau.getEmplacement(joueur.getX(),joueur.getY()));
                joueur.setPionSimple(plateau.getEmplacement(x, y));
            }
            //coups murs
            ArrayList<Integer[]> coups = filtreliste_coup_mur(liste_coup_mur(joueur.getX(), joueur.getY(), listeJoueur, idJoueur),listeJoueur,idJoueur);
            Murs mur;
            boolean choix = false;
            int valeur;
            for (Integer[] c : coups) {
                mur = joueur.setMur(plateau.getEmplacement(c[0], c[1]), plateau.getEmplacement(c[2], c[3]), plateau.getEmplacement(c[4], c[5]));
                valeur = min_max(profondeur - 1, listeJoueur, 1);
                if (valeur < valeurMin) {
                    valeurMin = valeur;
                    meilleurCoup = c;
                    choix = true;
                }
                Log.info("Calculs", "(m)valeur " + valeur + ", coup " + meilleurCoup);
                joueur.undoSetMur(mur, plateau);
            }
            if (choix) {
                Integer[] c = meilleurCoup;
                mur = joueur.setMur(plateau.getEmplacement(c[0], c[1]), plateau.getEmplacement(c[2], c[3]), plateau.getEmplacement(c[4], c[5]));
                Log.info("Calculs", "le meilleur coup est mur : " + c[0] + " " + c[1] + " et " + c[4] + " " + c[5] );
            } else {
                Integer[] tab = meilleurCoup;
                joueur.unsetPion(plateau.getEmplacement(joueur.getX(), joueur.getY()));
                joueur.setPionSimple(plateau.getEmplacement(tab[0], tab[1]));
                Log.info("Calculs", "le meilleur coup est pion : " + tab[0] + " " + tab[1]);
            }
        }

    }
}
