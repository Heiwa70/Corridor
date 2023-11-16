package application.vue.pages;


import application.controleur.Plateau;
import application.modele.Emplacement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class Game extends Parent {

    private void showPlateau() {
        Plateau plateau = new Plateau(9, 9);

        int sizeW = 30;
        int sizeH = 30;
        int width = sizeW;
        int height = sizeH;
        int positionX = 0;
        int positionY = 0;
        String couleur = "#ffffff";
        for (int y = 0; y < plateau.getHeight(); y++) {
            height = sizeH;
            positionX = 0;
            for (int x = 0; x < plateau.getWidth(); x++) {
                Emplacement emplacement = plateau.getEmplacement(x, y);
                String valeur = emplacement.toString();
                width = sizeW;
                System.out.println(valeur);
                int w = width;
                int h = height;
                switch (valeur) {
                    case "CASEPION":
                        couleur = "#654456";
                        break;
                    case "__PION__":
                        couleur = "#0000ff";
                        break;
                    case "CASEMURS":
                        w = x % 2 == 1 ? width / 2 : width;
                        h = y % 2 == 1 ? height / 2 : height;
                        couleur = "#233223";
                        break;
                    case "__MURS__":
                        couleur = "#000000";
                        w = x % 2 == 1 ? width / 2 : width;
                        h = y % 2 == 1 ? height / 2 : height;
                        break;
                    case "__VIDE__":
                        w = width / 2;
                        h = height / 2;
                        couleur = "#000000";
                        break;
                    case "_OCCUPE_":
                        couleur = "#000000";
                        break;
                    default:
                        couleur = "#ffffff";
                        break;
                }
                Button buttonNext = new Button();
                int finalX = x;
                int finalY = y;

                buttonNext.setOnAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        System.out.println("Par le mode '" + emplacement.toString() + "' .!!! Je te nique en X : " + finalX + ", Y : " + finalY);
                    }
                });
                buttonNext.setLayoutX(positionX);
                buttonNext.setLayoutY(positionY);
                buttonNext.setStyle("-fx-pref-width: " + w + "; -fx-pref-height: " + h + "; -fx-background-color: " + couleur + "; -fx-border-width:1; -fx-border-color:#000000");

                positionX += w;

                // buttonNext.setStyle("-fx-background-color: #"+couleur+"; -fx-border-width:2; -fx-border-color:#000000");
                System.out.println(couleur);
                getChildren().addAll(buttonNext);
            }

            positionY += y % 2 == 1 ? height / 2 : height;
        }

    }

    }
