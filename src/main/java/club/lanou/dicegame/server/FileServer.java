package club.lanou.dicegame.server;

import club.lanou.dicegame.utils.Constant;
import club.lanou.dicegame.handler.FileHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 接收客户端套接字连接，提交数据处理线程到线程池
 * 由于需要接收文件，因此需要使用TCP而不能使用UDP
 */
public class FileServer {
    private ServerSocket serverSocket;
    private final ExecutorService acceptExecutorService;
    private final ExecutorService fileHandleExecutorService;

    public FileServer(){
        this.acceptExecutorService = Executors.newSingleThreadExecutor();
        this.fileHandleExecutorService = Executors.newCachedThreadPool();
    }
    /**
     * 启动文件服务器，开启服务端的Constant.FILE_SERVER_PORT端口
     * 循环接收客户端对服务器Constant.FILE_SERVER_PORT端口的连接
     * 将连接的套接字对象交给文件处理线程，并将此线程提交到文件处理线程池中执行。
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void boot() {
        try {
            serverSocket = new ServerSocket(Constant.FILE_SERVER_PORT);
            acceptExecutorService.execute(() -> {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();      // 接收客户端数据包
                        fileHandleExecutorService.execute(new FileHandler(socket));   // 提交文件处理线程到线程池
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
