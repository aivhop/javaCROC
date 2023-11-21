package homework.chernetsov.task14;

import java.net.Socket;

public class Client {
    private final String name;
    private final Socket socket;

    public Client(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }
}
