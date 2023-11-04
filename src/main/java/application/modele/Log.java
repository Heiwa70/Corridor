/**
 * Classe Log écrite par Clément, Maxence et Nicolas.
 * FISA Informatique UTBM en PR70 2023.
 */

package application.modele;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * La classe Log est utilisé comme classe statique.
 * Elle permet d'écrire les évènements du programme dans plusieurs fichier.
 */
public class Log {

    // Chemin des fichiers logs.
    public static String chemin = "src//main//ressources//Logs//";
    // Format de la date du nom du dossier.
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // Format de la date pour chaque ligne de log.
    private static final DateTimeFormatter chronoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

    /**
     * Enregistrement d'une ligne d'erreur.
     *
     * @param nomClasse String, nom du fichier où sera enregistré la ligne d'erreur.
     * @param message   String, la description de la ligne d'erreur.
     */
    public static void error(String nomClasse, String message) {
        send("error", nomClasse, message);
    }

    /**
     * Enregistrement d'une ligne d'alerte.
     *
     * @param nomClasse String, nom du fichier où sera enregistré la ligne d'alerte.
     * @param message   String, la description de la ligne d'alerte.
     */
    public static void warn(String nomClasse, String message) {
        send("warn", nomClasse, message);
    }

    /**
     * Enregistrement d'une ligne d'informations.
     *
     * @param nomClasse String, nom du fichier où sera enregistré la ligne d'informations.
     * @param message   String, la description de la ligne d'informations.
     */
    public static void info(String nomClasse, String message) {
        send("info", nomClasse, message);
    }

    /**
     * Enregistrement du message.
     * Chaque ligne sera dans le format suivant :
     * AAAA-MM-JJ HH:MM:SS:MS [type] = message
     *
     * @param type      String, type du log.
     * @param nomClasse String, nom du fichier.
     * @param message   String, description du log.
     */
    private static void send(String type, String nomClasse, String message) {

        new File(chemin).mkdir();
        String filePath = chemin + dateFormat.format(LocalDateTime.now());
        new File(filePath).mkdir();

        File file = new File(filePath + "//" + nomClasse + ".log");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            // Ouverture, écriture et fermeture du fichier log.
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            fw.write(chronoFormat.format(LocalDateTime.now()) + " [" + type + "] = " + message + "\n");
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}