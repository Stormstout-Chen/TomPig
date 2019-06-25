import req_res.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.*;


public class TomPig {

    static private Properties properties;
    static private int port = 8080;
    static ExecutorService pool;

    static {
        InputStream inputStream = TomPig.class.getClassLoader().getResourceAsStream("kate.properties");
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch(IOException e){e.printStackTrace();}
        port = Integer.valueOf(properties.getProperty("serverPort"));
        pool = Executors.newFixedThreadPool(Integer.valueOf(properties.getProperty("initSize")));
    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true){
                Socket socket = serverSocket.accept();

                Runnable thread = new Runnable() {
                    @Override
                    public void run() {
                        InputStream inputStream = null;
                        try {
                            inputStream = socket.getInputStream();
                            OutputStream outputStream = socket.getOutputStream();

                            Request request = new Request(inputStream);
                            Response response = new Response(outputStream);

                            Servlet servlet = new Servlet(request, response);
                            servlet.doGet();
                            socket.close();
                        } catch (IOException e){e.printStackTrace();}
                    }
                };

                pool.execute(thread);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
