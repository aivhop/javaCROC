package homework.chernetsov.task14.client;

import homework.chernetsov.task14.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final int DEFAULT_PORT = 2023;
    public static final String LOCAL_HOST = "127.0.0.1";
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final String name;
    private final Socket socket;

    private final int port;
    private final String host;



    public Client(String host, int port) {
        this.name = initName();//todo server сторона*/
        this.port = port;
        this.host = host;
        try {
            this.socket = new Socket(host, port);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            throw new RuntimeException(e);//todo custom
        }

    }

    public Client() {
        this(LOCAL_HOST, DEFAULT_PORT);
    }

    public static String initName() {//todo
        System.out.println("Enter your name: ");
        return new Scanner(System.in).nextLine();
    }

    public void start() {
        System.out.println("Enter your name: ");
        System.out.println("start client");
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
            System.out.println("start reading");
            while (true) {
                try {
                    String message = reader.readLine();
                    System.out.println("Read: " + message);
                } catch (IOException e) {
                    throw new RuntimeException(e);//todo custom log.txt
                }
            }
        }
    }

    private class WriteMsg implements Runnable {
        private final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        @Override
        public void run() {
            System.out.println("start writing");
            writer.println(name);
            System.out.println("send name");
            while (true) {
                try {
                    String message = console.readLine();
                    System.out.println("You write: " + message);
                    writer.println(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);//todo
                }

            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
        /*ClientTest cl = new ClientTest(Server.LOCAL_HOST,Server.DEFAULT_PORT,"Paul");
        cl.start();*/

    }
}
