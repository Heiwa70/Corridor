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
    public List<int[]> listeMouvementsPion(int x, int y, int idJoueur) {
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
    public List<int[]> listeMouvementsPion(int x, int y) {

        List<int[]> possibilites = new ArrayList<>();
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
                        possibilites.add(new int[]{xx, yy});
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
    private void consitionAjoutPossibilite(List<int[]> possibilites, int x, int y) {
        if (testCase(x, y, Val.CASEPION)) {
            possibilites.add(new int[]{x, y});
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
            for (int[] coup : listeMouvementsPion(x_courant, y_courant, idjoueur)) {
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

        for (int[] coup : listeMouvementsPion(x, y)) {
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
    public ArrayList<String> liste_coup_mur(int x, int y, HashMap<Integer, Joueur> listeJoueurs, int idJoueur) {
        //Log.info("Calculs", "Debut generation mur");
        ArrayList<String> possibilite = new ArrayList<>();
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
                            possibilite.add(i + ";" + j + ";" + i + ";" + (j + 1) + ";" + i + ";" + (j + 2));
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
                            possibilite.add(j + ";" + i + ";" + (j + 1) + ";" + i + ";" + (j + 2) + ";" + i);
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
    public ArrayList<String> filtreliste_coup_mur(ArrayList<String> L, HashMap<Integer, Joueur> listejoueur, int idJoueur) {
        int n = L.size();
        ArrayList<String> res = new ArrayList<String>();
        int indice = 0;
        int distance = Integer.MIN_VALUE;
        if (n == 0) {
            return res;
        }
        for (int i = 0; i < n; i++) {
            String[] c = L.get(i).split(";");
            Murs mur = listejoueur.get(idJoueur).setMur(plateau.getEmplacement(Integer.parseInt(c[0]), Integer.parseInt(c[1])), plateau.getEmplacement(Integer.parseInt(c[2]), Integer.parseInt(c[3])), plateau.getEmplacement(Integer.parseInt(c[4]), Integer.parseInt(c[5])));
            int a = this.dijkstra(listejoueur.get(inverse_id(idJoueur)).getX(), listejoueur.get(inverse_id(idJoueur)).getY(), inverse_id(idJoueur));
            int b = this.dijkstra(listejoueur.get(idJoueur).getX(), listejoueur.get(idJoueur).getY(), idJoueur);
            if (distance < a - b) {
                distance = a - b;
                indice = i;

            }
            listejoueur.get(idJoueur).undoSetMur(mur, plateau);
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
        if (idJoueur == 2) {
            return dijkstra(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY(), idJoueur) - dijkstra(listeJoueur.get(inverse_id(idJoueur)).getX(), listeJoueur.get(inverse_id(idJoueur)).getY(), inverse_id(idJoueur));
        }
        else {
            return dijkstra(listeJoueur.get(inverse_id(idJoueur)).getX(), listeJoueur.get(inverse_id(idJoueur)).getY(), inverse_id(idJoueur))-dijkstra(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY(), idJoueur);
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
        if ((listeJoueur.get(idJoueur).getId() == 1 && listeJoueur.get(idJoueur).getY() == 0)) {
            //Log.info("Calculs","p:"+profondeur+" max");
            return Integer.MAX_VALUE;
        }
        if (listeJoueur.get(idJoueur).getId() == 2 && listeJoueur.get(idJoueur).getY() == 16) {
            //Log.info("Calculs","p:"+profondeur+" min, joueur id:");
            //System.out.println("min : "+listeJoueur.get(idJoueur).getId()+" y: "+listeJoueur.get(idJoueur).getY()+" id joueur : "+idJoueur);
            return Integer.MIN_VALUE;
        }
        if (profondeur == 0) {
            int r = euristique(listeJoueur, idJoueur);
            //Log.info("Calculs","p:"+profondeur+" valeur "+r);
            return r;
        }
        if (listeJoueur.get(idJoueur).getId() == 1) {
            int valeurMax = Integer.MIN_VALUE;
            //coups pion
            List<int[]> coupspion = listeMouvementsPion(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY(), idJoueur);
            int x = listeJoueur.get(idJoueur).getX();
            int y = listeJoueur.get(idJoueur).getY();
            for (int[] coup_pion : coupspion) {
                listeJoueur.get(idJoueur).setPion(plateau.getEmplacement(coup_pion[0], coup_pion[1]));
                listeJoueur.get(idJoueur).unsetPion(plateau.getEmplacement(x, y));
                //Log.info("Calculs","relance min_max avec le coup : p"+coup_pion[0]+" "+coup_pion[1]+ " profondeur "+profondeur);
                int valeur = min_max(profondeur - 1, listeJoueur, 2);
                //Log.info("Calculs","reçu min_max du coup : p"+coup_pion[0]+" "+coup_pion[1]+ " profondeur "+profondeur + " valeur "+valeur);
                valeurMax = Math.max(valeurMax, valeur);
                listeJoueur.get(idJoueur).unsetPion(plateau.getEmplacement(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY()));
                listeJoueur.get(idJoueur).setPion(plateau.getEmplacement(x, y));
            }
            //coups murs
            List<String> coups = filtreliste_coup_mur(liste_coup_mur(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY(), listeJoueur, idJoueur), listeJoueur, idJoueur);
            for (String coup : coups) {
                String[] c = coup.split(";");
                Murs mur = listeJoueur.get(idJoueur).setMur(plateau.getEmplacement(Integer.parseInt(c[0]), Integer.parseInt(c[1])), plateau.getEmplacement(Integer.parseInt(c[2]), Integer.parseInt(c[3])), plateau.getEmplacement(Integer.parseInt(c[4]), Integer.parseInt(c[5])));
                //Log.info("Calculs","relance min_max avec le coup : m"+c[0]+" "+c[1]+" "+c[2]+" profondeur "+profondeur);
                int valeur = min_max(profondeur - 1, listeJoueur, 2);
                //Log.info("Calculs","reçu min_max du coup : m"+c[0]+" "+c[1]+" "+c[2]+" profondeur "+profondeur+" valeur "+valeur);
                valeurMax = Math.max(valeurMax, valeur);
                listeJoueur.get(idJoueur).undoSetMur(mur, plateau);


            }
            return valeurMax;
        } else {
            int valeurMin = Integer.MAX_VALUE;
            //coups pion
            List<int[]> coupspion = listeMouvementsPion(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY(), idJoueur);
            int x = listeJoueur.get(idJoueur).getX();
            int y = listeJoueur.get(idJoueur).getY();
            for (int[] coup_pion : coupspion) {
                listeJoueur.get(idJoueur).setPion(plateau.getEmplacement(coup_pion[0], coup_pion[1]));
                listeJoueur.get(idJoueur).unsetPion(plateau.getEmplacement(x, y));
                int valeur = min_max(profondeur - 1, listeJoueur, 1);
                valeurMin = Math.min(valeurMin, valeur);
                listeJoueur.get(idJoueur).unsetPion(plateau.getEmplacement(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY()));
                listeJoueur.get(idJoueur).setPion(plateau.getEmplacement(x, y));
            }
            //coups murs
            List<String> coups = filtreliste_coup_mur(liste_coup_mur(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY(), listeJoueur, idJoueur), listeJoueur, idJoueur);
            for (String coup : coups) {
                String[] c = coup.split(";");
                Murs mur = listeJoueur.get(idJoueur).setMur(plateau.getEmplacement(Integer.parseInt(c[0]), Integer.parseInt(c[1])), plateau.getEmplacement(Integer.parseInt(c[2]), Integer.parseInt(c[3])), plateau.getEmplacement(Integer.parseInt(c[4]), Integer.parseInt(c[5])));
                int valeur = min_max(profondeur - 1, listeJoueur, 1);
                valeurMin = Math.min(valeurMin, valeur);
                listeJoueur.get(idJoueur).undoSetMur(mur, plateau);
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
        Object meilleurCoup = null;
        if (idJoueur == 1) {
            int valeurMax = Integer.MIN_VALUE;
            List<int[]> coupspion = listeMouvementsPion(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY(), idJoueur);
            int x = listeJoueur.get(idJoueur).getX();
            int y = listeJoueur.get(idJoueur).getY();
            for (int[] coup_pion : coupspion) {
                listeJoueur.get(idJoueur).setPion(plateau.getEmplacement(coup_pion[0], coup_pion[1]));
                listeJoueur.get(idJoueur).unsetPion(plateau.getEmplacement(x, y));
                int valeur = min_max(profondeur - 1, listeJoueur, 2);
                //Log.info("Calculs","valeur:"+valeur);
                if (valeur >= valeurMax) {
                    valeurMax = valeur;
                    meilleurCoup = coup_pion;
                }
                //Log.info("Calculs","(p)valeurmax "+valeurMax+", coup "+meilleurCoup);
                listeJoueur.get(idJoueur).unsetPion(plateau.getEmplacement(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY()));
                listeJoueur.get(idJoueur).setPion(plateau.getEmplacement(x, y));
            }
            //coups murs
            List<String> coups = filtreliste_coup_mur(liste_coup_mur(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY(), listeJoueur, idJoueur),listeJoueur,idJoueur);
            for (String coup : coups) {
                String[] c = coup.split(";");
                Murs mur = listeJoueur.get(idJoueur).setMur(plateau.getEmplacement(Integer.parseInt(c[0]), Integer.parseInt(c[1])), plateau.getEmplacement(Integer.parseInt(c[2]), Integer.parseInt(c[3])), plateau.getEmplacement(Integer.parseInt(c[4]), Integer.parseInt(c[5])));
                int valeur = min_max(profondeur - 1, listeJoueur, 2);
                if (valeur > valeurMax) {
                    valeurMax = valeur;
                    meilleurCoup = coup;
                }
                //Log.info("Calculs","(m)valeurmax "+valeur+", coup "+meilleurCoup);
                listeJoueur.get(idJoueur).undoSetMur(mur, plateau);
            }
            if (meilleurCoup instanceof String) {
                String[] c = ((String) meilleurCoup).split(";");
                Murs mur = listeJoueur.get(idJoueur).setMur(plateau.getEmplacement(Integer.parseInt(c[0]), Integer.parseInt(c[1])), plateau.getEmplacement(Integer.parseInt(c[2]), Integer.parseInt(c[3])), plateau.getEmplacement(Integer.parseInt(c[4]), Integer.parseInt(c[5])));
                Log.info("Calculs", "le meilleur coup est mur : " + c[0] + " " + c[1] + " et " + c[4] + " " + c[5]);
            } else {
                int[] tab = (int[]) meilleurCoup;
                listeJoueur.get(idJoueur).unsetPion(plateau.getEmplacement(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY()));
                listeJoueur.get(idJoueur).setPion(plateau.getEmplacement(Objects.requireNonNull(tab)[0], tab[1]));
                Log.info("Calculs", "le meilleur coup est pion : " + tab[0] + " " + tab[1]);
            }
        } else {
            int valeurMin = Integer.MAX_VALUE;
            List<int[]> coupspion = listeMouvementsPion(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY(), idJoueur);
            int x = listeJoueur.get(idJoueur).getX();
            int y = listeJoueur.get(idJoueur).getY();
            for (int[] coup_pion : coupspion) {
                listeJoueur.get(idJoueur).setPion(plateau.getEmplacement(coup_pion[0], coup_pion[1]));
                listeJoueur.get(idJoueur).unsetPion(plateau.getEmplacement(x, y));
                int valeur = min_max(profondeur - 1, listeJoueur, 1);
                if (valeur < valeurMin) {
                    valeurMin = valeur;
                    meilleurCoup = coup_pion;

                }
                Log.info("Calculs", "(p)valeur " + valeur + ", coup " + meilleurCoup);
                listeJoueur.get(idJoueur).unsetPion(plateau.getEmplacement(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY()));
                listeJoueur.get(idJoueur).setPion(plateau.getEmplacement(x, y));
            }
            //coups murs
            List<String> coups = filtreliste_coup_mur(liste_coup_mur(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY(), listeJoueur, idJoueur),listeJoueur,idJoueur);
            for (String coup : coups) {
                String[] c = coup.split(";");
                Murs mur = listeJoueur.get(idJoueur).setMur(plateau.getEmplacement(Integer.parseInt(c[0]), Integer.parseInt(c[1])), plateau.getEmplacement(Integer.parseInt(c[2]), Integer.parseInt(c[3])), plateau.getEmplacement(Integer.parseInt(c[4]), Integer.parseInt(c[5])));
                int valeur = min_max(profondeur - 1, listeJoueur, 1);
                if (valeur < valeurMin) {
                    valeurMin = valeur;
                    meilleurCoup = coup;
                }
                Log.info("Calculs", "(m)valeur " + valeur + ", coup " + meilleurCoup);
                listeJoueur.get(idJoueur).undoSetMur(mur, plateau);
            }
            if (meilleurCoup instanceof String) {
                String[] c = ((String) meilleurCoup).split(";");
                Murs mur = listeJoueur.get(idJoueur).setMur(plateau.getEmplacement(Integer.parseInt(c[0]), Integer.parseInt(c[1])), plateau.getEmplacement(Integer.parseInt(c[2]), Integer.parseInt(c[3])), plateau.getEmplacement(Integer.parseInt(c[4]), Integer.parseInt(c[5])));
                Log.info("Calculs", "le meilleur coup est mur : " + c[0] + " " + c[1] + " et " + c[4] + " " + c[5]);
            } else {
                int[] tab = (int[]) meilleurCoup;
                listeJoueur.get(idJoueur).unsetPion(plateau.getEmplacement(listeJoueur.get(idJoueur).getX(), listeJoueur.get(idJoueur).getY()));
                listeJoueur.get(idJoueur).setPion(plateau.getEmplacement(tab[0], tab[1]));
                Log.info("Calculs", "le meilleur coup est pion : " + tab[0] + " " + tab[1]);
            }
        }

    }
}
