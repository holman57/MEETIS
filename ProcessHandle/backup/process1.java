

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import java.lang.*;


public class MEETIS_RUNTIME {

    public static void main(String[] args) {

        int i = 0;
        try {
            String line;
            Process p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (i > 2)
                    System.out.println(line);
                i++;
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        zZZ(5);

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
    };

    public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = (Kernel32)Native.loadLibrary("kernel32", Kernel32.class);
        public Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId);
        public int GetTickCount();
    };

    public interface psapi extends StdCallLibrary {
        psapi INSTANCE = (psapi)Native.loadLibrary("psapi", psapi.class);
        int GetModuleFileNameExA (Pointer process, Pointer hModule, byte[] lpString, int nMaxCount);

    };


    public static String getModuleFilename(WinDef.HWND hwnd) {
        byte[] exePathname = new byte[512];

        Pointer zero = new Pointer(0);
        IntByReference pid = new IntByReference();
        User32.INSTANCE.GetWindowThreadProcessId(hwnd, pid);
        System.out.println("PID is " + pid.getValue());

        Pointer process = Kernel32.INSTANCE.OpenProcess(1040, false, pid.getValue());
        int result = psapi.INSTANCE.GetModuleFileNameExA(process, zero, exePathname, 512);
        String text = Native.toString(exePathname).substring(0, result);
        return text;

    }

/*    *//**
     * Finds all visible window threads.
     *
     * @return All visible window threads.
     *//*
    public static List<WinDef.HWND> findAll() {
        final User32 user32 = User32.INSTANCE;
        final List<WinDef.HWND> windows = new LinkedList<>();
        user32.EnumWindows(new User32.WNDENUMPROC() {

            @Override
            public boolean callback(WinDef.HWND hWnd, Pointer arg) {
                if (user32.IsWindowVisible(hWnd)) {
                    windows.add(hWnd);
                }
                return true;
            }
        }, null);
        return windows;
    }*/



/*import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class MEETIS_RUNTIME {

    public static void main(String[] args) {*/
    //    do {
/*            Runtime rt1 = Runtime.getRuntime();
            Process proc1 = null;

            try {
                String line;
                Process p = Runtime.getRuntime().exec
                        (System.getenv("windir") +"\\system32\\"+"tasklist.exe /fo csv /nh");
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null) {
                    System.out.println(line); //<-- Parse data here.
                }
                input.close();
            } catch (Exception err) {
                err.printStackTrace();
            }




            */













/*
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
            } catch (Exception e) {}


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

*/


     //   } while (true);


  //  }







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
