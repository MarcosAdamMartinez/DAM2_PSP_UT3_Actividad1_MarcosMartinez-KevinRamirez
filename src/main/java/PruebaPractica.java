import java.util.ArrayList;
import java.util.Scanner;

public class PruebaPractica {

    private static ArrayList<String> listaHostsArriba = new ArrayList<>();

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        System.out.print("Introduce una subred /24: ");
        String subred = teclado.next();

        if (validarSubred(subred)) {
            System.out.println("Subred valida");
        }
        String ip = subred + ".";

        EjecutorPing[] listaHilos = new EjecutorPing[255];

        try {
            for (int i = 0; i < listaHilos.length; i++) {
                listaHilos[i] = new EjecutorPing(ip+i, listaHostsArriba);
                listaHilos[i].start();
            }

            for (Thread t: listaHilos) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ejecutarHiloHostsArriba();

    }

    public static void ejecutarHiloHostsArriba() {
        for(int i=0; i< listaHostsArriba.toArray().length; i++) {
            HilosIP hilo = new HilosIP(listaHostsArriba.get(i));
        }
    }

    public static boolean validarSubred(String ip) {

        // Step 1: Separate the given string into an array of strings using the dot as delimiter
        String[] parts = ip.split("\\.");

        // Step 2: Check if there are exactly 3 parts
        if (parts.length != 3) {
            return false;
        }

        // Step 3: Check each part for valid number
        for (String part : parts) {
            try {
                // Step 4: Convert each part into a number
                int num = Integer.parseInt(part);

                // Step 5: Check whether the number lies in between 0 to 255
                if (num < 0 || num > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                // If parsing fails, it's not a valid number
                return false;
            }
        }

        // If all checks passed, return true
        return true;
    }
}
