package POJO;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class OIOServer {

    public void server(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            for (; ; ) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println("accept client!");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream outputStream;
                        try {
                            outputStream = clientSocket.getOutputStream();
                            outputStream.write("Hi".getBytes(Charset.forName("utf-8")));
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}
