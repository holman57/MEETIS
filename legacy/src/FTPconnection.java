/**
 * Created by Jupiter on 1/8/2016.
 */
import java.io.*;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;

/**
 * A program that demonstrates how to upload files from local computer
 * to a remote FTP server using Apache Commons Net API.
 * @author www.codejava.net
 */
public class FTPconnection {

    public FTPconnection() {

        String server = "ftp.lukeholman.net";
        int port = 21;
        String user = "holman57";
        String pass = "3LegDog3";

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File("C:/Delta/test.txt");

            String firstRemoteFile = "public_html/meetis/test.txt";
            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The file is uploaded successfully.");
            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public FTPconnection(ArrayList<String> list, String filename) {

        try {
            FileWriter writer = new FileWriter(filename);
            for(String str: list) {
                writer.write(str + ", ");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        String server = "ftp.lukeholman.net";
        int port = 21;
        String user = "holman57";
        String pass = "3LegDog3";

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(filename);

            String firstRemoteFile = "public_html/meetis/" + filename;
            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The file is uploaded successfully.");
            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public FTPconnection(ArrayList<String> list, String filename, JTextArea output) {

        try {
            FileWriter writer = new FileWriter(filename);
            for(String str: list) {
                writer.write(str + ", ");
            }
            writer.close();
        } catch (IOException e) {
            output.append("Error: " + e.getMessage());
        }

        String server = "ftp.lukeholman.net";
        int port = 21;
        String user = "holman57";
        String pass = "3LegDog3";

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(filename);

            String firstRemoteFile = "public_html/meetis/" + filename;
            InputStream inputStream = new FileInputStream(firstLocalFile);

            output.append("\n Start uploading file " + filename);
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                output.append("\n file uploaded successfully.");
            }

        } catch (IOException ex) {
            output.append("\n Error: " + ex.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                output.append("\n Error: " + ex.getMessage());
            }
        }
    }

}