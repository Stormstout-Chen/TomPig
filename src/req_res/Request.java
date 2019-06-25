package req_res;

import java.io.*;
import java.util.*;

//request类,主要作用是解析输入流,将输入流中的内容解析出来(包括请求头和请求体)封装到request对象的各个属性当中
//至此,我们将浏览器发来的输入流封装成了一个request对象,供程序猿来使用
public class Request {
    private InputStream inputStream;
    private String head;
    private String url;
    private String method;
    private Map<String,String> paramaters = new HashMap<>();//请求内容

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
        byte[] bytes = new byte[1024];
        try {
            int length = inputStream.read(bytes);
            String httpRequest = new String(bytes, 0, length);
            head = httpRequest.split("\n")[0];
            url = head.split(" ")[1];
            method = head.split(" ")[0];
            if (method.equals("GET")){
                String contents = url.split("\\?")[1];
                for (String content : contents.split("&")) {
                    String[] split = content.split("=");
                    paramaters.put(split[0],split[1]);
                }
            }
        } catch (Exception e) {}
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getHead() {
        return head;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getParamaters() {
        return paramaters;
    }

}
