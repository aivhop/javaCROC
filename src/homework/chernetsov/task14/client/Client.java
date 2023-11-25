package homework.chernetsov.task14.client;


import java.io.*;
import java.net.Socket;

public class Client {
    public static final int DEFAULT_PORT = 2023;
    public static final String LOCAL_HOST = "127.0.0.1";
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final Socket socket;

    private final int port;
    private final String host;



    public Client(String host, int port) {
        this.port = port;
        this.host = host;
        try {
            this.socket = new Socket(host, port);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    private class ReadMsg implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    String message = reader.readLine();
                    System.out.println(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class WriteMsg implements Runnable {
        private final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        @Override
        public void run() {
            while (true) {
                try {
                    String message = console.readLine();
                    writer.println(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
