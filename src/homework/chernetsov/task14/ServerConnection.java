package homework.chernetsov.task14;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class ServerConnection implements Runnable {

    private Server server;

    private Socket socket;
    private String clientName;
    private PrintWriter writer;
    private BufferedReader reader;

    private Thread write;

    public ServerConnection(Socket socket, Server server) {//todo exceptions
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        String message;
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            this.writer = writer;
            this.reader = reader;
            this.write = new Thread(new WriteMsg());
            Thread read = new Thread(new WriteMsg());

            writer.write("Type your name:\n");
            writer.flush();
            writer.close();
            System.out.println("IN CONNETCIOn");
            clientName = reader.readLine();

            /*while (clientName == null || clientName.isEmpty()) {
                clientName = reader.readLine(); //todo exceptions
                System.out.println("in clientNam");
                System.out.println(clientName);
            }*/

            server.addClient(clientName, this);
            System.out.println("New client, hello " + clientName);

        } catch (IOException e) {
            throw new RuntimeException(e);//todo
        } finally {
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);//todo
                }
            }
            server.removeClient(this);
        }
    }

    private class ReadMsg implements Runnable {
        @Override
        public void run() {
            String message;
            while (true) {
                try {
                    message = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (message == null || message.isEmpty()) {
                    continue;
                }
                if (message.equals("/exit")) {
                    break;
                }
                for (ServerConnection connection : server.getConnections()) {
                    if (connection.equals(this)) {
                        continue;
                    }
                    try {
                        connection.send(clientName + ": " + message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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


    public void send(String message) throws IOException {//todo exceptions
        writer.write(message + '\n');
        writer.flush();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerConnection that = (ServerConnection) o;
        return Objects.equals(server, that.server) && Objects.equals(socket, that.socket) && Objects.equals(clientName, that.clientName) && Objects.equals(writer, that.writer) && Objects.equals(reader, that.reader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, socket, clientName, writer, reader);
    }
}

//todo logger for errors
