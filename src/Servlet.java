import req_res.*;

import java.util.Map;

//servlet 项目中处理业务逻辑的类
//给他request和response,让用户自己弄处理业务的代码,完后通过request拿请求,通过response做响应
//正确的用法是写成抽象类 让用户自己去重写doGet方法来自定义业务逻辑
//这里就懒得弄啦
public class Servlet {
    private Request request;
    private Response response;

    public Servlet(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public void doGet(){
        Map<String, String> paramaters = request.getParamaters();
        //把参数封装成一个user对象
        //userDao.save(user);
        String username = paramaters.get("username");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response.write("  hello "+username+"!  you signup successfully! wish you lol everyDay~");
    }
}
