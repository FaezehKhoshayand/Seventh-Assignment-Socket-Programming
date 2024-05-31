package Client;
import java.io.*;
import java.net.Socket;


// Client Class
public class Client {
    // TODO: Implement the client-side operations

    // TODO: Add constructor and necessary methods
        public String name;
    public static void main(String[] args) throws IOException {
        // TODO: Implement the main method to start the client
        final int PORT = 8888;
        Socket s = new Socket("localHost", PORT);
        System.out.println("Connected to the Server");
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        HandleServerResponse handleServerResponse = new HandleServerResponse(s);
        new Thread(handleServerResponse).start();
        while (true) {
            String line = in.readLine();
            out.writeUTF(line);
        }
    }
}