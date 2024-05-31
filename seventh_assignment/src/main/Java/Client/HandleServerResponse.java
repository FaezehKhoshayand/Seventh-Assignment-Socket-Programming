package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class HandleServerResponse implements Runnable {
    private DataInputStream in;
    public HandleServerResponse(Socket s) throws IOException {
        in = new DataInputStream(s.getInputStream());
    }
    @Override
    public void run() {
        try {
            while (true) {
                System.out.print(in.readUTF());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void chat() throws IOException {
        while (true) {
            String input = in.readUTF();
            System.out.println(input);
            if (input.equals("leave")) {
                break;
            }

        }
    }

}
