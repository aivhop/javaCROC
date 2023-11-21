package homework.chernetsov.task14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import java.util.HashMap;
import java.util.Set;

public class Server {
    private HashMap<ServerConnection, String> clients = new HashMap<>();

    public final String LOCAL_HOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 2021;

    private final ServerSocket serverSocket;
    private final int port;

    public HashMap<ServerConnection, String> getClients() {
        return new HashMap<>(clients);
    }

    public Set<ServerConnection> getConnections() {
        return clients.keySet();
    }

    public void addClient(String clientName, ServerConnection connection) {
        clients.put(connection, clientName);
    }

    public void removeClient(ServerConnection connection) {
        clients.remove(connection);
    }

    public void start() throws IOException {
        System.out.println("Start server\n");
        try {
            while (true) {//todo exceptions
                Socket client = serverSocket.accept();
                ServerConnection connection = new ServerConnection(client, this);
                Thread thread = new Thread(connection);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);//todo exceptions
        } finally {
            serverSocket.close();
        }
    }


    public Server(int port) throws IOException { //todo exceptions
        this.serverSocket = new ServerSocket(port);
        this.port = port;
    }

    public Server(ServerSocket serverSocket) { //todo exceptions
        this.serverSocket = serverSocket;
        this.port = serverSocket.getLocalPort();
    }

    public Server() throws IOException {
        this(DEFAULT_PORT);
    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args) {
        try {

            Server myServ = new Server();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
