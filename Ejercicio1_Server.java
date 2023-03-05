import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        
        ServerSocket server = null;

        try {
            server = new ServerSocket(1234);
            System.out.println("Server escuchando en el puerto 1234");
        } catch (IOException e) {
            System.err.println("Error al crear el servidor: " + e.getMessage());
            System.exit(1);
        }

        Socket cliente = null;

        try {
            cliente = server.accept();
            System.out.println("Cliente conectado: "+cliente.getInetAddress());
        } catch (IOException e) {
            System.err.println("Fallo al aceptar la petición");
            System.exit(1);
        }

        PrintWriter EscritorSalida = new PrintWriter(cliente.getOutputStream(), true);
        BufferedReader LectorEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

        String LineaEntrada, LineaSalida;

        while((LineaEntrada = LectorEntrada.readLine()) != null) {
            System.out.println("Cliente: "+LineaEntrada);
            LineaSalida = LineaEntrada;

            EscritorSalida.println("Hola Bienvenido al server");
            if(LineaSalida.equals("Bye."))
            break;
        }
        EscritorSalida.close();
        LectorEntrada.close();
        cliente.close();
        server.close();
    }
}
