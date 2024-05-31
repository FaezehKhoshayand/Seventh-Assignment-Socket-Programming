package Server;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Service implements Runnable{
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private ArrayList<File> files = new ArrayList<>();;

    Service(Socket client) throws IOException {
        files.add(new File("Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\Java\\Server\\data\\all-of-me-john-legend"));
        files.add(new File("Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\Java\\Server\\data\\a-man-without-love-ngelbert-Hmperdinck"));
        files.add(new File("Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\Java\\Server\\data\\birds-imagine-dragons"));
        files.add(new File("Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\Java\\Server\\data\\blinding-lights-the-weekend"));
        files.add(new File("Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\Java\\Server\\data\\dont-matter-to-me-drake"));
        files.add(new File("Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\Java\\Server\\data\\feeling-in-my-body-elvis"));
        files.add(new File("Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\Java\\Server\\data\\out-of-time-the-weekend"));
        files.add(new File("Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\Java\\Server\\data\\something-in-the-way-nirvana"));
        files.add(new File("Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\Java\\Server\\data\\why-you-wanna-trip-on-me-michael-jackson"));
        files.add(new File("Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\Java\\Server\\data\\you-put-a-spell-on-me-austin-giorgio"));
        this.client = client;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
    }
    @Override
    public void run() {
            try {
                out.writeUTF("Enter your name\n");
                name = in.readUTF();
                while (true) {
                    out.writeUTF("Enter your command: 1-Group Chat 2-View FiLes 3-Exit");
                    String command = in.readUTF();
                    switch(command) {
                        case "1":
                            chat();
                            break;
                        case "2":
                            fileDownloader();
                            break;
                        case "3":
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
        out.writeUTF("Enter 'leave' to leave the chat\n");
        Server.getClients().add(client);
        if (!Server.getMessages().isEmpty()) {
            for(String temp : Server.getMessages()) {
                out.writeUTF(temp +"\n");
            }
        }
        while (true) {
            String message = in.readUTF();
            if(message.equals("leave")) {
                Server.getClients().remove(client);
                break;
            }
            else {
                String nameMessage = this.name + ":" + message;
                Server.getMessages().add(nameMessage);
                for (Socket temp : Server.getClients()) {
                    DataOutputStream o = new DataOutputStream(temp.getOutputStream());
                    o.writeUTF(nameMessage + "\n");
                }
            }
        }
    }
    public void fileDownloader() throws IOException {
        out.writeUTF("Choose one\n");
        out.writeUTF("1-all-of-me-john-legend\n");
        out.writeUTF("2-a-man-without-love-ngelbert-Hmperdinck\n");
        out.writeUTF("3-birds-imagine-dragons\n");
        out.writeUTF("4-blinding-lights-the-weekend\n");
        out.writeUTF("5-dont-matter-to-me-drake\n");
        out.writeUTF("6-feeling-in-my-body-elvis\n");
        out.writeUTF("7-out-of-time-the-weekend\n");
        out.writeUTF("8-something-in-the-way-nirvana\n");
        out.writeUTF("9-why-you-wanna-trip-on-me-michael-jackson\n");
        out.writeUTF("10-you-put-a-spell-on-me-austin-giorgio\n");
        String option = in.readUTF();
        File file = files.get(Integer.parseInt(option) - 1);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(file);
            os = new FileOutputStream(new File("Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\Java\\Client"));
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
                os.flush();
            }
        } finally {
            is.close();
            os.close();
        }
    }
}
