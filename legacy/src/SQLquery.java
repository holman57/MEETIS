

import com.sun.xml.internal.fastinfoset.util.StringArray;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class SQLquery {
    public SQLquery(internalComparison element, JTextArea outputTextArea) {
/*        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://23.229.144.128:3306/";
        Connection conn = DriverManager.getConnection(url, "ZETA", "3LegDog3");
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO `GEOPOLITICS`.`DATA_PROBE` (`TITLE`, `URL`, `IMG`) VALUES ('" + element.title + "', '" + element.url + "', '" + element.img + "');");

        String omicron = element.title + " " + element.url + element.img;
        outputTextArea.append(
                "\n        ( Total chars: " + omicron.length() +
                        ", Title length: " + element.title.length() +
                        ", URL length: " + element.url.length() +
                        ", IMG Length: " + element.img.length() + " )\n"
        );
        outputTextArea.append("\n" + element.title);
        outputTextArea.append("\n                           ( SQL Query Successful )\n");

        //  CLOSE CONNECTION
        conn.close();*/
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            outputTextArea.append("\n          ( ClassNotFoundException )");
        }

        String url = "jdbc:mysql://23.229.144.128:3306/";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "ZETA", "3LegDog3");
        } catch (SQLException e) {
            outputTextArea.append("\n          ( SQLException ) ( conn = DriverManager.getConnection(url, \"ZETA\", \"3LegDog3\"); )");
        }

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            outputTextArea.append("\n          ( SQLException ) ( stmt = conn.createStatement(); )");
        }

        try {
            stmt.executeUpdate(
"INSERT INTO `GEOPOLITICS`.`DATA_PROBE` (`TITLE`, `URL`, `IMG`) VALUES ('" + element.title + "', '" + element.url + "', '" + element.img + "');"
            );
        } catch (SQLException e) {
            outputTextArea.append("\n          ( SQLException ) (  stmt.executeUpdate( statement ); )");
        }

        outputTextArea.append("\n          ( SQL Query Successful )");

        String omicron = element.title + " " + element.url + element.img;
        outputTextArea.append(
                "\n        ( Total chars: " + omicron.length() +
                        ", Title length: " + element.title.length() +
                        ", URL length: " + element.url.length() +
                        ", IMG Length: " + element.img.length() + " )"
        );
        outputTextArea.append("\n" + element.title);

        //  CLOSE CONNECTION
        try {
            conn.close();
        } catch (SQLException e) {
            outputTextArea.append("\n          ( SQLException ) ( conn.close(); )");
        }
    }

    public SQLquery () {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
    //        System.out.println("\t\t( ClassNotFoundException )");
        }

        String url = "jdbc:mysql://23.229.144.128:3306/";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "ZETA", "3LegDog3");
        } catch (SQLException e) {
            System.out.println("\t( SQLException ) ( conn = DriverManager.getConnection(url, \"ZETA\", \"3LegDog3\"); )");
        }

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("\t( SQLException ) ( stmt = conn.createStatement(); )");
        }

        try {
            stmt.executeUpdate(
    "INSERT INTO `GEOPOLITICS`.`DATA_PROBE` (`TITLE`, `URL`, `IMG`) VALUES ('test00', 'test00', 'test00');"
                );
        } catch (SQLException e) {
            System.out.println("\t( SQLException ) (  stmt.executeUpdate( statement ); )");
        }

        System.out.println("\t\t( SQL Query Successful )");

            //  CLOSE CONNECTION
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("\t( SQLException ) ( conn.close(); )");
        }
    }
    public static void exUpdate (internalComparison beta, JTextArea jTextArea) throws ClassNotFoundException {

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://23.229.144.128:3306/";
            conn = DriverManager.getConnection(url, "ZETA", "3LegDog3");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO `GEOPOLITICS`.`DATA_PROBE` (`TITLE`, `URL`, `IMG`) VALUES ('" + beta.title + "', '" + beta.url + "', '" + beta.img + "');");
            conn.close();
        } catch (SQLException e) {
            jTextArea.append(e.getMessage());
            jTextArea.append(Arrays.toString(e.getStackTrace()));
        }

        String omicron = beta.title + " " + beta.url + beta.img;
        jTextArea.append(
                        "\n ( Total chars: " + omicron.length() +
                        ", Title length: " + beta.title.length() +
                        ", URL length: " + beta.url.length() +
                        ", IMG Length: " + beta.img.length() + " )"
        );
        jTextArea.append("\n ( " + beta.title + " ) ");

        Random rand = new Random();
        if (rand.nextInt(4) == 3) 
            verbalize(beta.title);

        String[] temp1 = beta.title.split(" ");
        StringBuilder sb = new StringBuilder();
        int sLength = 0;
        boolean breakline = true;
        for (int i = 0; i < temp1.length; i++) {
            if (sLength < 50) {
                sb.append(temp1[i]);
                sLength += temp1[i].length();
                sb.append(" ");
                sLength++;
            } else {
                if (breakline) {
                    System.out.println(sb.toString());
                    sb.setLength(0);
                }
                breakline = false;
                sb.append(temp1[i]);
                sLength += temp1[i].length();
                sb.append(" ");
                sLength++;
            }
        }
        System.out.println(sb.toString());
        try {
            sleep(1000);
        } catch (InterruptedException ignore) {}
    }
    public static void verbalize(String phrase) {
        do {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception ignore) {
            }
        } while (Talking());
        String http = "";
        String[] breakMe = phrase.split(" ");
        for (int i = 0; i < breakMe.length; i++) {
            if (breakMe[i].contains("http")) {
                http = breakMe[i];
                breakMe[i] = null;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < breakMe.length; i++) {
            if (breakMe[i] != null) {
                sb.append(breakMe[i]);
                sb.append(" ");
            }
        }
        String removedHyperlink = sb.toString();
        phrase = removedHyperlink.replaceAll("[^A-Za-z0-9@!$%&()., ]", " ");
        writeToMeetisFramework("phrase.txt", phrase);
        try {
            if (http.length() > 1) {
                Runtime.getRuntime().exec(new String[]{
                        "cmd", "/c", "start", "cmd", "/k", "java -jar Verbalize.jar "
                        /*      + "\"" + phrase + "\"" + " "*/ + "\"" + http + "\""
                });
            } else {
                Runtime.getRuntime().exec(new String[]{
                        "cmd", "/c", "start", "cmd", "/k", "java -jar Verbalize.jar"
                        //       + "\"" + phrase + "\""
                });
            }
        } catch (Exception ignore) {
        }
    }
    private static void writeToMeetisFramework(String fileName, String writeLineUpdate) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            writer.println(writeLineUpdate);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static boolean Talking(){
        String tString = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("MeetisTalking.txt"));
            if (br != null & !br.equals("")) {
                tString = br.readLine();
            }
        } catch (Exception ignore) {
        }
        if (tString.equalsIgnoreCase("Talking")){
            return true;
        } else {
            return false;
        }
    }
}