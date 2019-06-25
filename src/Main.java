public class Main {

    //自己实现一个简单的tomcat服务器 能够正常接收请求,处理业务逻辑并做出响应
    public static void main(String[] args) {
        //启动我们的TomPig服务器,然后就可以在浏览器里访问这个猪册项目啦
        //测试url:
        TomPig tomPig = new TomPig();
        tomPig.start();
    }
}
