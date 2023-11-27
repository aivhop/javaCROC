package homework.chernetsov.task14.client;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public static final int DEFAULT_PORT = 2023;
    public static final String LOCAL_HOST = "127.0.0.1";
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final Socket socket;


    public Client(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_16));
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_16), true);
        } catch (IOException e) {
            throw new IllegalArgumentException("Incorrect host and port", e);
        }
    }

    public Client() {
        this(LOCAL_HOST, DEFAULT_PORT);
    }

    public void start() {
        Thread read = new Thread(new ReadMsg());
        Thread write = new Thread(new WriteMsg());
        read.start();
        write.start();
        try {
            read.join();
            write.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Critical error: " + e.getMessage(),e);
        }
    }

    private void close() {
        System.out.println("The connection is disconnected, the server is offline, write bye");
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Critical error: " + ex.getMessage(), ex);
        }
    }

    private class ReadMsg implements Runnable {
        @Override
        public void run() {
            while (!socket.isClosed()) {
                try {
                    String message = reader.readLine();
                    System.out.println(message);
                } catch (IOException e) {
                    if (e.getClass().getSimpleName().equals("SocketException")) {
                        close();
                    } else {
                        System.err.println("Critical error: " + e.getMessage());
                    }
                }
            }
        }
    }

    private class WriteMsg implements Runnable {
        private final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        @Override
        public void run() {
            while (!socket.isClosed()) {
                try {
                    String message = console.readLine();
                    writer.println(message);
                } catch (IOException e) {
                    if (e.getClass().getSimpleName().equals("SocketException")) {
                        close();
                    } else {
                        System.err.println("Critical error: " + e.getMessage());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
