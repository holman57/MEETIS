import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MeetisHello {
    public static void main(String[] args) {
        Console console = System.console();
        cls();
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        cls();

        System.out.println("\n\nI'm going to try and make contact with the database");
        voice.speak("I'm going to try and make contact with the database");

        System.out.println("\n                     hold up\n");
        voice.speak("hold up");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class.forName(\"com.mysql.jdbc.Driver\");");
        } catch (ClassNotFoundException e) {
            System.out.println("\n      ClassNotFoundException com.mysql.jdbc.Driver");
            voice.speak("Class Not Found Exception J D B C Driver Exception");
        }
        String url = "jdbc:mysql://23.229.144.128:3306/";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "ZETA", "3LegDog3");
            System.out.println("conn = DriverManager.getConnection");
        } catch (SQLException e) {
            System.out.println("\n   DriverManager.getConnection     SQLException *");
            voice.speak("S Q L Exception ERROR ERROR Sequel Exception Driver Manager");
            e.printStackTrace();
        }
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            System.out.println("stmt = conn.createStatement();");
        } catch (SQLException e) {
            System.out.println("\n      stmt = conn. SQLException *");
            voice.speak("S Q L Exception DANGER Sequel Exception");
        }

        ResultSet resultset = null;
        try {
            resultset = stmt.executeQuery("SELECT COUNT(*) FROM GEOPOLITICS.DATA_PROBE");
            System.out.println("resultset = stmt.executeQuery(\"SELECT * FROM `DATA_PROBE`\");");
        } catch (SQLException e) {
            System.out.println("\n   SELECT COUNT(*) FROM     SQLException *");
            voice.speak("S Q L Exception DANGER select Count from data probe Sequel Exception");
            e.printStackTrace();
        }
        int dbSize = 0;
        try {
            while (resultset.next()){
                dbSize = resultset.getInt(1);
            }
            System.out.println("               dbSize = " + dbSize);
            voice.speak("the database has reached the size" + dbSize);
        } catch (SQLException e) {
            System.out.println("\n      SQLException * dbSize = set");
            voice.speak("S Q L Exception metaData ResultSetMetaData Sequel Exception count failure");
            e.printStackTrace();
        }
        try {
            resultset = stmt.executeQuery("SELECT * FROM  `GEOPOLITICS`.`DATA_PROBE` LIMIT " + (dbSize - 10) + " , 10");
            System.out.println("    stmt.executeQuery(sqlSELECT * FROM");
        } catch (SQLException e) {
            System.out.println("\n      SELECT * FROM LIMIT     SQL Exception *");
            voice.speak("S Q L Exception SELECT * FROM LIMIT failure");
            e.printStackTrace();
        }
        int numcols = 4;
        ArrayList<String> list = new ArrayList<String>();
        System.out.println("Loop through result set");
        try {
            ResultSetMetaData rsmd = resultset.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            System.out.print(" Column Count " + columnsNumber + "\n");
            while (resultset.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = resultset.getString(i);
                    list.add(columnValue);
                    System.out.println(columnValue/* + " " + rsmd.getColumnName(i)*/);
                    if (i == 2)
                        voice.speak(columnValue);
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("\n      Dump ResultSet     SQL Exception *");
            voice.speak("S Q L Exception Failure dump result set");
            e.printStackTrace();
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("\n                     SQLException *");
            voice.speak("Connection Close Failure");
            voice.speak("Force Terminate");
    }

        try{
            PrintWriter writer = new PrintWriter("Data_Points.txt", "UTF-8");
            System.out.println("\nWrite to local");
            voice.speak("write to local");
            for (int i = 0; i < list.size(); i++) {
                writer.println(list.get(i));
            }
            writer.close();
        } catch (IOException e) {
            voice.speak("Failed write to file");
            System.out.println("Failed to write local");
        }
        System.out.println("\nSuccessful");
        voice.speak("Successful write");
        System.out.println("Thank you");
        voice.speak("thank you");

        try {
            Runtime.getRuntime().exec("taskkill /f /im cmd.exe");

        } catch (Exception e) {

        }
        System.exit(0);
    }

    private static void cls() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (Exception e) {
        }
    }

    private static void waitRandomShort() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(200);
        for (int i = 0; i < 40; i++) {
            System.out.printf("|");
            TimeUnit.MILLISECONDS.sleep(random);
        }
        System.out.printf("\n");
    }
    private static void waitRandom() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(500);
        for (int i = 0; i < 40; i++) {
            System.out.printf("|");
            TimeUnit.MILLISECONDS.sleep(random);
        }
        System.out.printf("\n");
    }

    private static void waitRandomLong() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(15000);
        random += 1000;
        for (int i = 0; i < 40; i++) {
            System.out.printf("|");
            TimeUnit.MILLISECONDS.sleep(random);
        }
        System.out.printf("\n");
    }
}
