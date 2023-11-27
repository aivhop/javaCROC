package homework.chernetsov.task14.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Server {
    private final int port;
    public static final int DEFAULT_PORT = 2023;

    private final HashMap<String, Connection> connections = new HashMap<>();

    public Server(int port) {
        this.port = port;
    }

    public Server() {
        this(DEFAULT_PORT);
    }

    public void start() {
        System.out.println("Server starting...\n");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Successful started, port: " + port);
            while (true) {
                Socket client = serverSocket.accept();
                try {
                    synchronized (this) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_16));
                        PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_16), true);
                        Connection connection = new Connection(reader, writer);
                        new Thread(connection).start();
                    }
                } catch (IOException ex) {
                    System.err.println("Failed to connect to the user: " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    synchronized private void sendToAllOthers(String message, Connection sender) {
        for (Connection connection : connections.values()) {
            if (connection == sender) {
                continue;
            }
            connection.writer.println(message);
        }
    }

    synchronized private void sendServerMessageToAll(String message) {
        for (Connection connection : connections.values()) {
            connection.writer.println("Server: " + message);
        }
    }


    private class Connection implements Runnable {
        private String clientName;
        private final BufferedReader reader;

        private final PrintWriter writer;

        public Connection(BufferedReader reader, PrintWriter writer) {
            this.reader = reader;
            this.writer = writer;
        }


        private void initName() {
            writer.println("Hello, type your name: ");
            while (clientName == null) {
                try {
                    String name = reader.readLine();
                    while (name.trim().isEmpty()) {
                        writer.println("Your name can't be empty, type something please: ");
                        name = reader.readLine();
                    }
                    clientName = name;
                    writer.println("Welcome, " + clientName);
                    sendServerMessageToAll("New user \"" + clientName + "\", welcome him");
                } catch (IOException e) {
                    System.err.println("Failed to connect to the user: " + e.getMessage());
                    break;
                }
            }
        }

        @Override
        public void run() {
            initName();
            synchronized (connections) {
                connections.put(clientName, this);
            }
            System.out.println("New user: " + clientName);
            try {
                while (true) {
                    String message = reader.readLine();
                    if (!message.trim().isEmpty()) {
                        message = clientName + ": " + message;
                        System.out.println(message);
                        sendToAllOthers(message, this);
                    }
                }
            } catch (IOException e) {
                closeConnection(e.getMessage());
            }
        }

        private void closeConnection(String errorMsg) {
            String message = clientName + " left";
            System.out.println(errorMsg + ": " + message);
            synchronized (connections) {
                connections.remove(clientName, this);
            }
            sendServerMessageToAll(message);
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
