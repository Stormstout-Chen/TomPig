package web.web_servlet;

import lib.Request;
import lib.Response;
import lib.Servlet;

import java.util.Map;

public class LoginServlet extends Servlet {

    public LoginServlet(Request request, Response response) {
        super(request, response);
    }

    @Override
    public void doGet() {
        Map<String, String> paramaters = request.getParamaters();
        //登录的逻辑
        String username = paramaters.get("username");
        response.write("  hello "+username+"! login successful!");
    }

    @Override
    public void doPost() {
        doGet();
    }

}
