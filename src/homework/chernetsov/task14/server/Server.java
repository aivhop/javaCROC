package homework.chernetsov.task14.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final int port;
    public static final int DEFAULT_PORT = 2023;
    public static final String LOCAL_HOST = "127.0.0.1";

    private final PrintWriter writerLog;

    private ArrayList<Connection> connections = new ArrayList<>();
    //todo hashmap name + connection

    private boolean isWork = false;

    public Server(int port) {
        this.port = port;
    }

    public Server() {
        this(DEFAULT_PORT);
    }

    //todo clear clients- if he left
    //todo не отправлять пустое сообщение
    public void start() {
        System.out.println("Server starting...\n");
        isWork = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Successful started, port: " + port);
            while (isWork) {
                Socket client = serverSocket.accept();
                Connection connection = new Connection(client);
                connections.add(connection);
                Thread thread = new Thread(connection);
                thread.start();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);//todo mb custom
        }
    }

    private class Connection implements Runnable {
        Socket clientSocket;//todo modificator
        String clientName;

        BufferedReader reader;

        PrintWriter writer;

        boolean isConnected = false;

        public Connection(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());//todo log
            }
            isConnected = true;
        }

        private void initName(){
            writer.println("Hello, type your name:\n");
            try {
                String name = reader.readLine();
                while (name.isEmpty()){
                    writer.println("Your name can't be empty, type something please:\n");
                    name = reader.readLine();
                }
                clientName = name;
                writer.println("Welcome, " + clientName);
            } catch (IOException e) {
                //todo log
                throw new RuntimeException(e);//todo custom exception
            }
        }
        private void send(String message){
            for (Connection connection : connections) {
                if (connection == this) {
                    continue;
                }
                connection.writer.println(message);
            }
        }

        @Override
        public void run() {
            initName();
            System.out.println("New user: " + clientName);
            while (isConnected && isWork) {
                try {
                    String message = reader.readLine();
                    System.out.println(message);//todo log
                    send(message);
                } catch (IOException e) {
                    System.err.println(e.getMessage());//todo log.txt
                }
            }
        }

    }


    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
