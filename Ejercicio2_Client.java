import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        int i=1;
        while(i==1)
        {
        try {
            // Establecer conexión con el servidor
            Socket socket = new Socket("localhost", 8080);

            // Leer número desde la entrada estándar
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Ingresa Numero: ");
            String number_str = reader.readLine();
            int number = Integer.parseInt(number_str);

            // Enviar número al servidor
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(number_str);
            bw.flush();

            // Leer siguiente número desde el servidor
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String next_number_str = br.readLine();
            int next_number = Integer.parseInt(next_number_str);
            System.out.println("Numero respondido por servidor: " + next_number);

            // Cerrar conexiones
            br.close();
            isr.close();
            is.close();
            bw.close();
            osw.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
}
