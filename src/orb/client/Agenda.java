package orb.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Agenda implements IRepository {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private final String ipAdress = "localhost";
    private final int port = 4444;

    public Agenda() {
        // Connect to server and send codOp 1  to server
        String request = "1";
        try {
            // Open connection
            socket = new Socket(ipAdress, port);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());

            // Write data
            outputStream.writeUTF(request);
            outputStream.flush();

            // Close connection
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error during connection");
        }
    }

    public void asociar(String s, int v) {
        // Connect to server and send codOp 2  to server
        // After that send the key s and the value v  to server
        String request = "2".concat("-").concat(s).concat("-").concat(String.valueOf(v));
        try {
            // Open connection
            socket = new Socket("localhost", 4444);
            outputStream = new DataOutputStream(socket.getOutputStream());

            // Write data
            outputStream.writeUTF(request);
            outputStream.flush();

            // Close connection
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error during connection");
        }
    }

    public int obtener(String s) {
        // Connect to server and send codOp 3 to server
        // After that send the key s to server
        // After that receive the value from server
        String request = "3".concat("-").concat(s);
        int responseValue = 0;
        try {
            // Open connection
            socket = new Socket("localhost", 4444);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());

            // Write data
            outputStream.writeUTF(request);
            outputStream.flush();

            // Read data
            responseValue = inputStream.readInt();
            while (responseValue != 0) {
                responseValue = inputStream.readInt();
            }

            // Close connection
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error during connection");
        }
        return responseValue;
    }
}
