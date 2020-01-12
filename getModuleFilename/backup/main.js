import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class MEETIS_RUNTIME {

    public static void main(String[] args) {
        do {
            Runtime rt1 = Runtime.getRuntime();
            Process proc1 = null;
            try {
                proc1 = rt1.exec(new String[]{"cmd", "/c", "start", "cmd", "/k", "java -jar Count.jar"});
            } catch (Exception e) {}

            try{
                PrintWriter writer = new PrintWriter("MEETIS_RUNTIME.txt");
                writer.println(ManagementFactory.getRuntimeMXBean().getName());
                System.out.println("Runtime: ");
                System.out.println(ManagementFactory.getRuntimeMXBean().getName());
                writer.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                zZZ(5);
            } catch (Exception e) { }


            try {
               // zZZZ(60);
                
                zZZ(5);
            } catch (Exception e) {}


            String everything = null;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("Count_Log.txt"));
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            everything = getString(everything, br);
            System.out.println(everything);
            String[] parts = everything.split("@");
            String part1 = parts[0];
            try {
                proc1 = rt1.exec("taskkill /F /PID " + part1);
                proc1 = rt1.exec("taskkill /f /im cmd.exe") ;
            } catch (Exception e) {}
            try {
                zZZ(1);
            //    proc1 = rt1.exec(new String[]{"cmd", "/c", "start", "cmd", "/k", "java -jar MEETIS_RUNTIME.jar"});
                zZZ(50);

            } catch (Exception e) {}
            try {
                br = new BufferedReader(new FileReader("MEETIS_RUNTIME.txt"));
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            everything = getString(everything, br);
            System.out.println(everything);
            String[] parts1 = everything.split("@");
            String part2 = parts1[0];
            try {
                zZZZ(60);
                proc1 = rt1.exec("taskkill /F /PID " + part2);
                proc1 = rt1.exec("taskkill /f /im cmd.exe");
                zZZ(5);
            } catch (Exception e) {}






        } while (true);


    }







    private static String getString(String everything, BufferedReader br) {
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return everything;
    }
    private static void zZZ(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception ignore) {
        }
    }
    private static void zZZZ(int minutes) {
        try {
            TimeUnit.MINUTES.sleep(minutes);
        } catch (Exception ignore) {
        }
    }
}
