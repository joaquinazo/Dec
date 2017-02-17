package Second;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Scanner;


public class Hash {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        byte[] c1;
        String line;
        try {

            System.out.println("1.- Obteniendo la instancia con algoritmo " + args[0]);
            MessageDigest md = MessageDigest.getInstance(args[0]);
            System.out.println("2.- Actualizando el contenido de la instancia");
            String mssg = "";
            System.out.println("Introduce lo que quieras guardas (\"CMD + D o CNTRL + Z \" para salir)");
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                mssg = mssg + " " + line;
                if (line.equals("QUIT")) {
                    break;
                }
            }
            c1 = mssg.getBytes(Charset.forName("UTF-8"));


            md.update(c1);

            System.out.println("3.- Calculando el resumen");
            byte[] resumen = md.digest();
            System.out.println("Resumen: " + new String(resumen, Charset.forName("UTF-8")));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}