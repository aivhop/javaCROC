package homework.chernetsov.task14;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {
    private final int port;
    private final String host;

    private final Socket socket;

    private PrintWriter writer;
    private BufferedReader readerServer;
    private BufferedReader readerConsole = new BufferedReader(new InputStreamReader(System.in));

    public ClientConnection(int port, String host, Socket socket) {
        this.port = port;
        this.host = host;
        this.socket = socket;
    }

    public ClientConnection(Socket socket, String host) throws IOException {
        this.socket = socket;
        this.port = socket.getPort();
        this.host = host;
        this.writer = new PrintWriter(socket.getOutputStream(),true);
        this.readerServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread read = new Thread(new ReadMsg());
        Thread write = new Thread(new WriteMsg());
        read.start();
        write.start();
    }


    @Override
    public void run() {
    }

    private class ReadMsg implements Runnable {
        @Override
        public void run() {
            String message;
            while (true) {
                try {
                    message = readerServer.readLine();
                    /*System.out.println(message);*/
                    if (message != null && !message.isEmpty()) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);//todo
                }

            }
        }
    }

    private class WriteMsg implements Runnable {
        @Override
        public void run() {
            String message;
            while (true) {
                try {
                    message = readerConsole.readLine();
                    if (message != null && !message.isEmpty()) {
                        writer.write(message);
                        writer.flush();
                        System.out.println("do flush");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}



