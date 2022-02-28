package club.lanou.dicegame.server;

import club.lanou.dicegame.utils.Constant;
import club.lanou.dicegame.handler.RegisterHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用户注册服务器
 * 接收客户端连接对象，再提交数据处理线程到线程池中
 */
public class RegisterServer {
    private ServerSocket serverSocket;
    private final ExecutorService registerDataHandleExecutorService;

    public RegisterServer(){
        this.registerDataHandleExecutorService = Executors.newCachedThreadPool();
    }

    public void boot(){
        try {
            serverSocket = new ServerSocket(Constant.REGISTER_SERVER_PORT);
            new Thread(() -> {
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        // 提交用户注册任务线程到线程池
                        registerDataHandleExecutorService.execute(new RegisterHandler(clientSocket));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
