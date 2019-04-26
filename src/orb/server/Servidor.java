package orb.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Servidor {

    private static final int PORT = 4444;

    public static void main(String[] args) {
        // Open ans wait for connections
        // Once client connected, receive codOp and execute the correspondent operation
        Agenda agendaTelefonica = null;

        ServerSocket serverSocket;
        Socket socket;
        DataInputStream inputStream;
        DataOutputStream outputStream;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for connections on port: " + PORT);

            while (true) {
                String response;
                HashMap<String, String> responseMap = new HashMap<>();

                socket = serverSocket.accept();
                System.out.println("----------------------------------------------------------------");
                System.out.println("Client connected");

                inputStream = new DataInputStream(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("I/O channels opened");

                response = inputStream.readUTF();

                responseMap.put("codOp", response.split("-")[0]);
                System.out.println("Operation code received: " + responseMap.get("codOp"));

                Servidor server = new Servidor();

                switch (responseMap.get("codOp")) {
                    case "1":
                        agendaTelefonica = server.createOperation();
                        break;
                    case "2":
                        server.asociateOperation(agendaTelefonica, response, responseMap);
                        break;
                    case "3":
                        server.obtainOperation(agendaTelefonica, outputStream, response, responseMap);
                        break;
                    default:
                        System.out.println("Unknown operation code received");
                }
                System.out.println("Closing connection");
                System.out.println("----------------------------------------------------------------");
                socket.close();
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Agenda createOperation() {
        Agenda agendaTelefonica = new Agenda();
        System.out.println("New object Agenda created");
        return agendaTelefonica;
    }

    private void asociateOperation(Agenda agendaTelefonica, String response, HashMap<String, String> responseMap) {
        responseMap.put("key", response.split("-")[1]);
        System.out.println("Key received: " + responseMap.get("key"));

        responseMap.put("value", response.split("-")[2]);
        System.out.println("Value received: " + responseMap.get("value"));

        agendaTelefonica.asociar(responseMap.get("key"), Integer.parseInt(responseMap.get("value")));
        System.out.println("Operation asociar executed with args: " +
                responseMap.get("key") + ", " + responseMap.get("value"));
    }

    private void obtainOperation(Agenda agendaTelefonica, DataOutputStream outputStream, String response, HashMap<String, String> responseMap) throws IOException {
        responseMap.put("key", response.split("-")[1]);
        System.out.println("Key received: " + responseMap.get("key"));

        int requestValue = agendaTelefonica.obtener(responseMap.get("key"));

        outputStream.writeInt(requestValue);
        System.out.println("Sending to client value: " + requestValue);
    }
}
