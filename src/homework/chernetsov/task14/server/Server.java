package homework.chernetsov.task14.server;

import homework.chernetsov.task14.exceptions.ConnectionCreationError;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
                    Connection connection = new Connection(client);
                    new Thread(connection).start();
                } catch (ConnectionCreationError ex) {
                    System.err.println("Не удалось соединиться с пользователем: " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public void close() {
        connections.values().forEach((Connection c) -> c.sendMessage("The server is closed, goodbye"));
        connections.clear();
    }


    private class Connection implements Runnable {
        private Socket clientSocket;
        private String clientName;

        private BufferedReader reader;

        private PrintWriter writer;

        public Connection(Socket clientSocket) throws ConnectionCreationError {
            this.clientSocket = clientSocket;
            try {
                this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            } catch (IOException ex) {
                throw new ConnectionCreationError(ex);
            }
        }

        private void initName() {
            writer.println("Hello, type your name: ");
            while (clientName == null) {
                try {
                    String name = reader.readLine();
                    while (name.isEmpty()) {
                        writer.println("Your name can't be empty, type something please: ");
                        name = reader.readLine();
                    }
                    clientName = name;
                    writer.println("Welcome, " + clientName);
                    sendToAll("New user: " + clientName + ", say hello");
                } catch (IOException e) {
                    System.err.println("Error in reading message: " + e.getMessage());
                    writer.println("Error, try again please: ");
                }
            }
        }

        private void sendToAll(String message) {
            for (Connection connection : connections.values()) {
                if (connection == this) {
                    continue;
                }
                connection.writer.println(message);
            }
        }

        public void sendMessage(String message) {
            writer.println(message);
        }

        @Override
        public void run() {
            initName();
            connections.put(clientName, this);
            System.out.println("New user: " + clientName);

            try {
                while (true) {
                    String message = reader.readLine();
                    if (!message.isEmpty()) {
                        message = clientName + ": " + message;
                        System.out.println(message);
                        sendToAll(message);
                    }
                }
            } catch (IOException e) {
                closeConnection(e.getMessage());
            }
        }


        private void closeConnection(String errorMsg) {
            String message = clientName + " left";
            System.out.println(errorMsg + ": " + message);
            connections.remove(clientName, this);
            sendToAll(message);
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
