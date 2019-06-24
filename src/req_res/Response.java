package req_res;

import java.io.*;

//response类 主要作用是提供相应的方法,将程序猿传入的想要响应的内容封装进输出流回写给浏览器
public class Response {
    private OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(String body){
        StringBuffer httpResponse = new StringBuffer();
        httpResponse.append("HTTP/1.1 200 OK\n")
                .append("Content-Type: text/html\n")
                .append("\r\n")
                .append("<html><body>")
                .append(body)
                .append("</html></body>");

        try {
            outputStream.write(httpResponse.toString().getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
