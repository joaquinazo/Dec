package First;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.security.Key;
import java.util.Scanner;


public class Decripting {
    static String FILENAME = "UNENCRIPTED.txt";
    static String FILENAMEFINAL = "ENCRIPTED.txt";
    static String FILENAMEKEY = "KEY.txt";
    static String DES = "DES";
    static SecretKey key = null;
    static Cipher encriptador;
    static Cipher desencriptador;
    static KeyGenerator keyGenerator;
    static FileOutputStream OUTPUT = null;
    static File UNENCRIPTED = new File(FILENAME);
    static File ENCRIPTED = new File(FILENAMEFINAL);
    static File KEY = new File(FILENAMEKEY);
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {

            encriptador = Cipher.getInstance("DES");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            key = keyGenerator.generateKey();
            UNENCRIPTED.createNewFile();
            ENCRIPTED.createNewFile();
            KEY.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            System.out.println("Escribe la frase que quieras encriptar");
            String content = sc.nextLine();
            bw.write(content);
            bw.close();
            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        }


        ENCRIPTED = encriptar(UNENCRIPTED, ENCRIPTED);
        desencriptar(ENCRIPTED);
    }

    static public File encriptar(File de, File en) {
        System.out.println(de.length());
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(de)));
            String line = reader.readLine();
            System.out.println("line " + line);
            encriptador.init(Cipher.ENCRYPT_MODE, key);
            String str = new String(encriptador.doFinal(line.getBytes()));
            System.out.println("----->>>>>> " + str);


            PrintWriter pw = new PrintWriter(new FileWriter(ENCRIPTED));
            pw.print(str);

            PrintWriter pw2 = new PrintWriter(new FileWriter(KEY));
            pw2.print(new String(key.getEncoded()));

            reader.close();
            pw.close();
            pw2.close();
            //OUTPUT.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ENCRIPTED;
    }

    static public void desencriptar(File encryptedFile) {
        try {

            desencriptador = Cipher.getInstance("DES");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(encryptedFile)));
            String linecifrado = reader.readLine();
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(KEY)));
            String linekey = reader2.readLine();
            key = new SecretKeySpec(linekey.getBytes(), 0, linekey.getBytes().length, "DES");

            System.out.println("1. "+linecifrado);
            //byte[] input = linecifrado.getBytes();
            desencriptador.init(Cipher.DECRYPT_MODE, key);

            String decifrado=new String( desencriptador.doFinal(linecifrado.getBytes()));
            System.out.println("MENSAJE DECIFRADO: !!! "+decifrado);

            reader.close();
            reader2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}