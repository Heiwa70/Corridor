package application.modele;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class Log {

    public static String path = "src//main//ressources//Logs//";
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter chronoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

    public static void error(String nomClasse, String message) {
        send("error", nomClasse, message);
    }

    public static void warn(String nomClasse, String message) {
        send("warn", nomClasse, message);
    }

    public static void info(String nomClasse, String message) {
        send("info", nomClasse, message);
    }

    private static void send(String type, String nomClasse, String message) {

        new File(path).mkdir();
        String filePath = path+dateFormat.format(LocalDateTime.now());
        new File(filePath).mkdir();

        File file = new File(filePath+"//"+nomClasse+".log");

        try{
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            fw.write(chronoFormat.format(LocalDateTime.now())+" ["+type+"] = "+message+"\n");
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}