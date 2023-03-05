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
            System.out.println("Cliente conectado: " + cliente.getInetAddress());
        } catch (IOException e) {
            System.err.println("Fallo al aceptar la petición");
            System.exit(1);
        }

        PrintWriter EscritorSalida = new PrintWriter(cliente.getOutputStream(), true);
        BufferedReader LectorEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

        String LineaEntrada, LineaSalida;

        ClientHandler ClientHandler = new ClientHandler();

        while ((LineaEntrada = LectorEntrada.readLine()) != null) {
            if (LineaEntrada.equalsIgnoreCase("Adios")) {
                EscritorSalida.println("Adios");
                break;
            } else {
                System.out.println("Cliente: " + LineaEntrada);
                LineaSalida = ClientHandler.processInput(LineaEntrada);
                EscritorSalida.println(LineaSalida);
            }
        }
        EscritorSalida.close();
        LectorEntrada.close();
        cliente.close();
        server.close();
    }
}

class ClientHandler {
    private static final int ESPERANDO = 0;
    private static final int ENVIARESP1 = 1;
    private static final int ENVIARESP2 = 2;
    private static final int OTRA = 3;

    private int estado;// = ESPERANDO;

    private String[] resp1 = { "Hola, bienvenido al server", "Mi nombre es Servidor", "Server dice hola" };

    public String processInput(String TextoRecibido) {
        String textoDeSalida = null;
        if (estado == ESPERANDO) {
            textoDeSalida = "Esperando cadena de texto";
            estado = ENVIARESP1;
        } else if (estado == ENVIARESP1) {
            if (TextoRecibido.equalsIgnoreCase("Hola")) {
                textoDeSalida = resp1[2];
                estado = ENVIARESP2;
            } else {
                textoDeSalida = "No reconozco el mensaje, intenta mandando Hola";
            }
        } else if (estado == ENVIARESP2) {
            if (TextoRecibido.equalsIgnoreCase("Nombre")) {
                textoDeSalida = resp1[1];
                estado = OTRA;
            } else {
                textoDeSalida = "Prueba escribiendo Nombre\n";
                estado = ENVIARESP1;
            }
        } else if (estado == OTRA) {
            textoDeSalida = "Adios";
        }
        return textoDeSalida;
    }
}
