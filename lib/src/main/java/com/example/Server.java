package com.example;
/**
 * Created by Administrator on 2017/4/25.
 */

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) {
        Data data=new Data();
        Gson gson=new Gson();
        data.setName("qqq");
        data.setImage(123);
        String str=gson.toJson(data);

        try {
            ServerSocket server = new ServerSocket(8888);//定义客户端的端口号
            Socket client = server.accept();//定义一个Socket对象

            InputStream is = client.getInputStream();//服务器接受信息输入流，也就是接受从服务器段发送过来的消息
            BufferedReader br = new BufferedReader( new InputStreamReader(is));//用bufferedreader包装下输入流

            OutputStream os = client.getOutputStream();//这是用来给服务器发送消息的输出流

//            PrintStream ps = new PrintStream(os);
//            Scanner scanner = new Scanner(System.in);//从键盘输入字符串

            boolean flag = true;//定义一个死循环，让服务器不停的接受从客户端发送来的字符串
            while (flag) {

                String s = br.readLine();//s是从客户端接受到得字符串
                if (s==null)
                    break;
                System.out.println(s);
                String strResponse = "HTTP/1.1 200 OK\nContent-Length: " + str.getBytes().length + "\n\n" + str;
                os.write(strResponse.getBytes(), 0, strResponse.getBytes().length);
                os.flush();

//                String s2 = scanner.nextLine();//s2是写给客户端的字符串
//                ps.println("123456789");    //给客户端发送你写的东西
            }

            client.close();
        }
        catch (IOException e) {//try 跟catch你不用管，这是用来处理异常的，就是固定格式
            e.printStackTrace();
        }
    }
}
