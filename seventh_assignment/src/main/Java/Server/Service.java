package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Service implements Runnable{
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;

    Service(Socket client) throws IOException {
        this.client = client;
        in = new DataInputStream(client.getInputStream());
        out = new DataOutputStream(client.getOutputStream());
    }
    public void run() {
        while (true) {
            try {
                out.writeUTF("Enter your name");
                String name = in.readUTF();
                out.writeUTF("Enter your command: 1-Group Chat 2-View FiLes 3-Download Files 4-Exit");
                String command = in.readUTF();
                switch(command) {
                    case "1":

                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    default:
                        out.writeUTF("Enter a number between 1 and 3");
                        out.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                try {
                    in.close();
                    out.close();
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

}
