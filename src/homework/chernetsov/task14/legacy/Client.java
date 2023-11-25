package homework.chernetsov.task14.legacy;

import java.io.IOException;
import java.net.Socket;

public class Client {
    //private final String name;
    private final Socket socket;

    public Client(Socket socket) {
        //this.name = name;
        this.socket = socket;
    }



    public static void main(String[] args) {
        try {
            Socket socket1 = new Socket(Server.LOCAL_HOST, Server.DEFAULT_PORT);
            ClientConnection connection = new ClientConnection(socket1, Server.LOCAL_HOST);
        } catch (IOException e) {
            System.out.println("Fatal error: " + e.getMessage());
        }

    }
}
