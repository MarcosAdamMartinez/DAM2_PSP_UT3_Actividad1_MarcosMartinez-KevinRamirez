import javax.net.ssl.HostnameVerifier;
import java.io.IOException;
import java.util.ArrayList;

public class EjecutorPing extends Thread{

    private static String host;

    private ArrayList<String> listaHostsArriba = new ArrayList<>();

    public EjecutorPing(String host, ArrayList<String> lista) {
        this.host = host;
        this.listaHostsArriba = lista;
    }

    @Override
    public void run() {
        ProcessBuilder pb;
        if (System.getProperty("os.name").toLowerCase().equals("win")){
            pb = new ProcessBuilder("ping","-n", "1" , host);
        } else {
            pb = new ProcessBuilder("ping","-c", "1" , host);
        }

        try {
            Process process = pb.start();
            process.waitFor();
            if (process.exitValue() == 0){
                listaHostsArriba.add(host);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


//    public static ArrayList<String> addHostArriba() {
//        listaHostsArriba.add(host);
//        return listaHostsArriba;
//    }


}
