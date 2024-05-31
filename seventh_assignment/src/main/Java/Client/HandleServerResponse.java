package Client;

import java.io.DataInputStream;
import java.io.FileOutputStream;
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
                String i = in.readUTF();
                if (i.equals("file")) {
                    int length = 0;
                    String path = in.readUTF();
                    FileOutputStream os = new FileOutputStream(path);
                    Long size = in.readLong();
                    byte[] buffer = new byte[8192];
                    while (size > 0 && (length = in.read(buffer, 0 , (int) Math.min(buffer.length, size))) != -1) {
                        os.write(buffer, 0, length);
                        size -= length;
                    }
                    System.out.println("Qq");
                    os.close();
                }
                else {
                    System.out.println(i);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
