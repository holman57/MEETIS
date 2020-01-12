import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static java.lang.Integer.parseInt;
/**
 * Find all processes
 * Poll runtime.config
 *                                          MEETIS_RUNTIME /\Oo/\\ *
 * @return process list
 * @return runtime.config variation
 */
public class MEETIS_RUNTIME {
    private static boolean system_active = true;
    private static String computer_name = "";
    public static void main(String[] args) {
        ArrayList<String> runtime_config = new ArrayList<>();
        ArrayList<String> runtime_processes = new ArrayList<>();
        computer_name = ManagementFactory.getRuntimeMXBean().getName();
        String[] split = computer_name.split("@");
        int runtime_pid = parseInt(split[0]);
        computer_name = split[1];
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date init_time = new Date();
        zZZ(1);
        Thread task_thread = new Thread(() -> {
            do {
                saveTasklist();
                zZZ(5);
            } while (system_active);
        });
        task_thread.start();
        System.out.println("---------------------------------------------" +
                            "\nInit PID: " + runtime_pid +
                            "\nComputer: " + computer_name +
                            "\nDate/Time: " + dateFormat.format(init_time) +
                            "\n---------------------------------------------");
        Runtime rt1 = Runtime.getRuntime();
        try {
            rt1.exec(new String[]{"cmd", "/c", "start", "cmd", "/k", "java -jar CountUp.jar"});
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Random random = new Random();
        zZZ(5 + random.nextInt(7));
        String temp = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("Count_Log.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        temp = getString(temp, br);
        System.out.println(temp);
        String[] parts = temp.split("@");
        String part = parts[0];
        try {
            rt1.exec("taskkill /F /PID " + part);
            rt1.exec("taskkill /f /im cmd.exe");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        zZZ(1);
        File config_file = new File("runtime.config");
        if (config_file.exists()) {
            config_file.delete();
        }
        writeLineFile("runtime", "config","Status: Initializing");
        appendLineFile("runtime.config", "Check: " + dateFormat.format(init_time));
        appendLineFile("runtime.config", "\nComputer: " + computer_name);
        Thread config_thread = new Thread(() -> {
            do {
                updateRuntime(runtime_config, "runtime.config");
                zZZ(5);
            } while (system_active);
        });
        config_thread.start();
        Thread check_processes_thread = new Thread(() -> {
            do {
                updateRuntimeProcesses(runtime_processes, lastFileModified("runtime-data\\" + computer_name + "\\processes\\").getPath());
                zZZ(5);
            } while (system_active);
        });
        check_processes_thread.start();
        try {
            new ProcessBuilder("cmd", "/c", "startOBS.bat").inheritIO().start().waitFor();
            zZZ(5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            new ProcessBuilder("cmd", "/c", "java -jar ../MeetisTV/MeetisTV.jar").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        zZZ(55555);
        system_active = false;
    }

    private static void updateRuntimeProcesses(ArrayList<String> update_array, String file_path) {
        ArrayList<String> parsed_check_array = new ArrayList<>();
        boolean process_variation = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file_path));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                String[] parts = sCurrentLine.split("\"");
                parsed_check_array.add(parts[1]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (parsed_check_array.size() != update_array.size()) {
            int max_size = (parsed_check_array.size() > update_array.size() ? parsed_check_array.size() : update_array.size());
            for (int i = 0; i < max_size; i++) {
                if (i < parsed_check_array.size() && i < update_array.size()) {
                    process_variation = isProcess_variation(update_array, parsed_check_array, process_variation, i);
                }
            }
            update_array.clear();
            update_array.addAll(parsed_check_array);
        } else {
            for (int j = 0; j < parsed_check_array.size(); j++) {
                process_variation = isProcess_variation(update_array, parsed_check_array, process_variation, j);
            }
            if (process_variation) {
                update_array.clear();
                update_array.addAll(parsed_check_array);
            }
        }
    }

    private static boolean isProcess_variation(ArrayList<String> update_array, ArrayList<String> parsed_check_array, boolean process_variation, int j) {
        if (!parsed_check_array.get(j).equalsIgnoreCase(update_array.get(j))) {
            if (!process_variation) {
                System.out.println("( process variation detected )");
            }
            process_variation = true;
            System.out.println("\t  process_check: " + parsed_check_array.get(j));
            System.out.println("\truntime_process: " + update_array.get(j));
        }
        return process_variation;
    }

    private static void updateRuntime(ArrayList<String> update_array, String file_path) {
        ArrayList<String> check_array = new ArrayList<>();
        boolean updated = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file_path));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                check_array.add(sCurrentLine);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (check_array.size() != update_array.size()) {
            update_array.clear();
            update_array.addAll(check_array);
            updated = true;
        } else {
            boolean config_variation = false;
            for (int j = 0; j < check_array.size(); j++) {
                if (!check_array.get(j).equalsIgnoreCase(update_array.get(j))) {
                    if (!config_variation) {
                        System.out.println("( variation detected )");
                    }
                    System.out.println("\t  config_check: " + check_array.get(j));
                    System.out.println("\truntime_config: " + update_array.get(j));

                    config_variation = true;
                }
            }
            if (config_variation) {
                update_array.clear();
                update_array.addAll(check_array);
                updated = true;
            }
        }
        if (updated) {
            for (int j = 0; j < update_array.size(); j++) {
                System.out.println(j + ": " + update_array.get(j));
            }
        }
    }

    public static File lastFileModified(String dir) {
        File fl = new File(dir);
        File[] files = fl.listFiles(file -> file.isFile());
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        return choice;
    }


    private static void saveTasklist() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date init_time = new Date();
        File computer_dir = new File("runtime-data\\" + computer_name);
        if (!computer_dir.exists() || !computer_dir.isDirectory()) {
            computer_dir.mkdirs();
        }
        File processes_dir = new File("runtime-data\\" + computer_name + "\\processes");
        if (!processes_dir.exists() || !processes_dir.isDirectory()) {
            processes_dir.mkdirs();
        }
        try {
            String line;
            PrintWriter writer = new PrintWriter("runtime-data\\" + computer_name + "\\processes\\" + computer_name + "@" + dateFormat.format(init_time) + ".csv");
            Process p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe /FO csv");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                writer.println(line);
            }
            input.close();
            writer.close();
        //    System.out.println("tasklist saved: " + computer_name + "@" + dateFormat.format(init_time) + ".csv");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void appendLineFile(String path, String appendText) {
        Path PATH = Paths.get(path);
        try {
            Files.write(PATH, appendText.getBytes(), StandardOpenOption.APPEND);
            System.out.println("wrote: " + appendText +
                             "\n file: " + path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeLineFile(String path, String filetype, String line) {
        try{
            PrintWriter writer = new PrintWriter(path + "." + filetype);
            writer.println(line);
            System.out.println("wrote: " + line +
                             "\n file: " + path + "." + filetype);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //    ProcessHandle.allProcesses().forEach(process -> System.out.println(processDetails(process)));
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
