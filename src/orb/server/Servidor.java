package orb.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Servidor {

    private static final int PORT = 4444;

    private static final int INDEX_COD_OP = 0;
    private static final int INDEX_OBJ_ID = 1;
    private static final int INDEX_KEY = 2;
    private static final int INDEX_VALUE = 3;

    private static int lastId = -1;

    private Map<Integer, Agenda> agendas;

    public static void main(String[] args) {
        // Open ans wait for connections
        // Once client connected, receive codOp and execute the correspondent operation
        Servidor server = new Servidor();

        server.agendas = new HashMap<>();

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

                responseMap.put("codOp", response.split("-")[INDEX_COD_OP]);
                System.out.println("Operation code received: " + responseMap.get("codOp"));

                switch (responseMap.get("codOp")) {
                    case "1":
                        server.createOperation(outputStream);
                        break;
                    case "2":
                        server.asociateOperation(response, responseMap);
                        break;
                    case "3":
                        server.obtainOperation(outputStream, response, responseMap);
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

    private void createOperation(DataOutputStream outputStream) {
        Agenda agenda = new Agenda(lastId++);
        this.agendas.put(lastId, agenda);
        System.out.println("New object Agenda created with id: " + lastId);
        try {
            outputStream.writeInt(lastId);
            System.out.println("Sending to client objId: " + lastId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void asociateOperation(String response, HashMap<String, String> responseMap) {
        responseMap.put("objId", response.split("-")[INDEX_OBJ_ID]);
        System.out.println("ObjId received: " + responseMap.get("objId"));

        responseMap.put("key", response.split("-")[INDEX_KEY]);
        System.out.println("Key received: " + responseMap.get("key"));

        responseMap.put("value", response.split("-")[INDEX_VALUE]);
        System.out.println("Value received: " + responseMap.get("value"));

        this.agendas.get(Integer.parseInt(responseMap.get("objId"))).asociar(responseMap.get("key"), Integer.parseInt(responseMap.get("value")));
        System.out.println("Operation asociar executed with args: " + responseMap.get("key") + ", " + responseMap.get("value"));
    }

    private void obtainOperation(DataOutputStream outputStream, String response, HashMap<String, String> responseMap) throws IOException {
        responseMap.put("objId", response.split("-")[INDEX_OBJ_ID]);
        System.out.println("ObjId received: " + responseMap.get("objId"));

        responseMap.put("key", response.split("-")[INDEX_KEY]);
        System.out.println("Key received: " + responseMap.get("key"));

        int requestValue = this.agendas.get(Integer.parseInt(responseMap.get("objId"))).obtener(responseMap.get("key"));

        outputStream.writeInt(requestValue);
        System.out.println("Sending to client value: " + requestValue);
    }
}
