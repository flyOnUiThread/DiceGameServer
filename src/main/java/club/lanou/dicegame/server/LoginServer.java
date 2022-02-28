package club.lanou.dicegame.server;

import club.lanou.dicegame.handler.LoginHandler;
import club.lanou.dicegame.utils.Constant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用户登录服务器
 */
public class LoginServer {
    private DatagramSocket datagramSocket;
    private final ExecutorService registerDataHandleExecutorService;

    public LoginServer(){
        this.registerDataHandleExecutorService = Executors.newCachedThreadPool();
    }

    public void boot(){
        try {
            // 监听登录服务器端口
            datagramSocket = new DatagramSocket(Constant.LOGIN_SERVER_PORT);
            new Thread(() -> {
                while (true) {
                    try {
                        byte[] data = new byte[32];
                        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
                        datagramSocket.receive(datagramPacket);      // 接收客户端数据包
                        // 提交用户登录任务线程到线程池
                        registerDataHandleExecutorService.execute(new LoginHandler(datagramPacket));
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
