package lib;

//servlet 项目中处理业务逻辑的类 作为抽象类,供猿自己去实现并加入业务逻辑
//给他request和response,让用户自己弄处理业务的代码,并通过request拿请求,通过response做响应
public abstract class Servlet {
    protected Request request;
    protected Response response;

    public Servlet(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public abstract void doGet();

    public abstract void doPost();
}
