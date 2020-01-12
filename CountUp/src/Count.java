import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class Count {
    public static void main(String[] args) {
        try{
            PrintWriter writer = new PrintWriter("Count_Log.txt");
            writer.println(ManagementFactory.getRuntimeMXBean().getName());
            System.out.println(ManagementFactory.getRuntimeMXBean().getName());
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (int i = 1; i > 0; i++) {
            System.out.print(i + " ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {}
        }
    }
}
