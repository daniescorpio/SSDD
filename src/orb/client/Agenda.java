package orb.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Agenda implements IRepository {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private int objId;

    private final String ipAdress = "localhost";
    private final int port = 4444;

    public Agenda() {
        // Connect to server and send codOp 1  to server
        Request request = new Request(1);
        try {
            // Open connection
            socket = new Socket(ipAdress, port);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());

            // Write data
            outputStream.writeUTF(request.toRequestString());
            outputStream.flush();

            // Read data
            objId = inputStream.readInt();
            while (objId != -1) {
                objId = inputStream.readInt();
            }

            // Close connection
            outputStream.close();
            socket.close();
        } catch (IOException e) {
        }
    }

    public void asociar(String s, int v) {
        // Connect to server and send codOp 2  to server
        // After that send the key s and the value v  to server
        // Message structure codOp-objId-key-value
        Request request = new Request(2, this.objId, s, v);
        try {
            // Open connection
            socket = new Socket("localhost", 4444);
            outputStream = new DataOutputStream(socket.getOutputStream());

            // Write data
            outputStream.writeUTF(request.toRequestString());
            outputStream.flush();

            // Close connection
            outputStream.close();
            socket.close();
        } catch (IOException e) {}
    }

    public int obtener(String s) {
        // Connect to server and send codOp 3 to server
        // After that send the key s to server
        // After that receive the value from server
        // Message structure codOp-objId-key
        Request request = new Request(3, this.objId, s);
        int responseValue = 0;
        try {
            // Open connection
            socket = new Socket("localhost", 4444);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());

            // Write data
            outputStream.writeUTF(request.toRequestString());
            outputStream.flush();

            // Read data
            responseValue = inputStream.readInt();
            while (responseValue != 0) {
                responseValue = inputStream.readInt();
            }

            // Close connection
            outputStream.close();
            socket.close();
        } catch (IOException e) {}
        return responseValue;
    }
}
