import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.*;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

/**
 * Find all processes.
 *
 * @return process list.
 */
public class MEETIS_RUNTIME {
    public static void main(String[] args) {

        String computer_name = "";
        int runtime_pid;
        computer_name = ManagementFactory.getRuntimeMXBean().getName();
        String[] split = computer_name.split("@");
        runtime_pid = parseInt(split[0]);
        computer_name = split[1];

        System.out.println("Init PID: " + runtime_pid);
        System.out.println("Computer: " + computer_name);

        zZZ(2);


        int i = 5;
        try {
            String line;
            PrintWriter writer = new PrintWriter("MEETIS_RUNTIME.txt");
            Process p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe /FO csv");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (i > 2) {
                    writer.println(line);
                    System.out.println(line);
                }
                i++;
            }
            input.close();
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        zZZ(1);

        ProcessHandle.allProcesses().forEach(process -> System.out.println(processDetails(process)));





    }

    private static String processDetails(ProcessHandle process) {
        return String.format("%8d %8s %10s %26s %-40s %26s",
                process.pid(),
                text(process.parent().map(ProcessHandle::pid)),
                text(process.info().command()),
                text(process.parent().map(ProcessHandle::info)),
                text(process.descendants().findFirst()),
                text(process.children().findFirst())
        );
    }

    private static String text(Optional<?> optional) {
        return optional.map(Object::toString).orElse("-");
    }

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
        int GetWindowThreadProcessId(WinDef.HWND hwnd, IntByReference pid);
    }

    public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = (Kernel32)Native.loadLibrary("kernel32", Kernel32.class);
        public Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId);
        public int GetTickCount();
    }

    public interface psapi extends StdCallLibrary {
        psapi INSTANCE = (psapi)Native.loadLibrary("psapi", psapi.class);
        int GetModuleFileNameExA (Pointer process, Pointer hModule, byte[] lpString, int nMaxCount);
    }

    public static String getModuleFilename(WinDef.HWND hwnd) {
        byte[] exePathname = new byte[512];

        Pointer zero = new Pointer(0);
        IntByReference pid = new IntByReference();
        User32.INSTANCE.GetWindowThreadProcessId(hwnd, pid);
        System.out.println("PID is " + pid.getValue());

        Pointer process = Kernel32.INSTANCE.OpenProcess(1040, false, pid.getValue());
        int result = psapi.INSTANCE.GetModuleFileNameExA(process, zero, exePathname, 512);
        return Native.toString(exePathname).substring(0, result);
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
