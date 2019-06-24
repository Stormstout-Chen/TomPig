import req_res.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class TomPig {

    private  int port = 8080;

    public TomPig(int port) {
        this.port = port;
    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true){
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                Request request = new Request(inputStream);
                Response response = new Response(outputStream);

                Servlet servlet = new Servlet(request, response);
                servlet.doGet();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
