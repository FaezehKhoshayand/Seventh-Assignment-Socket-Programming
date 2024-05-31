package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Service implements Runnable{
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    Service(Socket client) throws IOException {
        this.client = client;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
    }
    @Override
    public void run() {
            try {
                out.writeUTF("Enter your name");
                name = in.readUTF();
                while (true) {
                    out.writeUTF("Enter your command: 1-Group Chat 2-View FiLes 3-Download Files 4-Exit");
                    String command = in.readUTF();
                    switch(command) {
                        case "1":
                            chat();
                            break;
                        case "2":
                            break;
                        case "3":
                            break;
                        case "4":
                            return;
                        default:
                            out.writeUTF("Enter a number between 1 and 3");
                            out.flush();
                }
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
    public void chat() throws IOException {
        Server.getClients().add(client);
        if (!Server.getMessages().isEmpty()) {
            for(String temp : Server.getMessages()) {
                out.writeUTF(temp);
            }
        }
        while (true) {
            out.writeUTF("Enter '1' to leave the chat");
            String message = in.readUTF();
            if(message.equals("1")) {
                Server.getClients().remove(client);
                break;
            }
            else {
                String nameMessage = this.name + ":" + message;
                Server.getMessages().add(nameMessage);
                for (Socket temp : Server.getClients()) {
                    DataOutputStream o = new DataOutputStream(temp.getOutputStream());
                    o.writeUTF(nameMessage);
                }
            }
        }
    }

}
