package web.web_servlet;

import lib.*;

import java.util.Map;

public class SignupServlet extends Servlet {

    public SignupServlet(Request request, Response response) {
        super(request, response);
    }

    @Override
    public void doGet() {
        Map<String, String> paramaters = request.getParamaters();
        //把参数封装成一个user对象
        //userDao.save(user);
        String username = paramaters.get("username");
        response.write("  hello "+username+"!  you signup successfully! wish you lol everyDay~");
    }

    @Override
    public void doPost() {
        doGet();
    }

}
