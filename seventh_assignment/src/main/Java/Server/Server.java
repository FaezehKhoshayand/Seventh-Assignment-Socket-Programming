package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Server Class
public class Server {
    // TODO: Implement the server-side operations

    // TODO: Add constructor and necessary methods
    private static ArrayList<Socket> clients = new ArrayList<>();
    private static ArrayList<String> messages = new ArrayList<>();
    public static ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public static ArrayList<String> getMessages() {
        return messages;
    }
    public static ArrayList<Socket> getClients() {
        return clients;
    }
    public static void main(String[] args) throws IOException {
        // TODO: Implement the main method to start the server
        final int PORT = 8888;
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Waiting for clients to connect...");

        while (true) {
            Socket s = server.accept();
            System.out.println("Client connected.");
            Service task = new Service(s);
            threadPool.execute(task);
        }
    }
}