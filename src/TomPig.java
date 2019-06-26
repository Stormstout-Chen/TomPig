import lib.*;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;


public class TomPig {

    static private int port = 8080;//服务端口
    static private ExecutorService pool;//内置的线程池 线程池大小决定了TomPig能处理的最高并发数
    static private Properties properties;//容器全局配置
    static private Properties mapProperties;//ulrMapping 配置
    static private Map<String,Class> urlMap;//urlMapping 表

    static {
        InputStream inputStream = TomPig.class.getClassLoader().getResourceAsStream("conf/kate.properties");
        properties = new Properties();
        try {
            properties.load(inputStream);
            port = Integer.valueOf(properties.getProperty("serverPort"));
            pool = Executors.newFixedThreadPool(Integer.valueOf(properties.getProperty("initSize")));
        } catch(IOException e){e.printStackTrace();}

        InputStream mapInputStream = TomPig.class.getClassLoader().getResourceAsStream("web/properties/url_mapping.properties");
        mapProperties = new Properties();
        urlMap = new HashMap<>();
        try {
            mapProperties.load(mapInputStream);
            Iterator<Map.Entry<Object, Object>> iterator = mapProperties.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry entry=(Map.Entry)iterator.next();
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                Class<?> clazz = Class.forName(value);//反正项目里也要用,先帮你初始化了
                urlMap.put(key,clazz);
            }
        } catch(Exception e){
            System.err.println("铁子,你这边路径映射配的有问题哦,请检查url_mapping.properties文件");
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true){
                Socket socket = serverSocket.accept();

                Runnable thread = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //获取输入和输出流
                            InputStream inputStream = socket.getInputStream();
                            OutputStream outputStream = socket.getOutputStream();

                            //封装Request和Response对象
                            Request request = new Request(inputStream);
                            Response response = new Response(outputStream);

                            //urlMapping
                            String servletName = null;
                            String url = request.getUrl();
                            if (url.contains("?")){
                                servletName = url.split("\\?")[0];
                            }else {
                                servletName = url;
                            }

                            if (urlMap.containsKey(servletName)){
                                //执行业务
                                Constructor constructor = urlMap.get(servletName).getConstructor(Request.class, Response.class);
                                Servlet servlet = (Servlet) constructor.newInstance(request,response);
                                servlet.doGet();
                            }

                            //释放资源
                            inputStream.close();
                            outputStream.close();
                            socket.close();
                        } catch (Exception e){e.printStackTrace();}
                    }
                };

                pool.execute(thread);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
