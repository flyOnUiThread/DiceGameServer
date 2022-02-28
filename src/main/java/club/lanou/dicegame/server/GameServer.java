package club.lanou.dicegame.server;

import club.lanou.dicegame.bean.GameRoom;
import club.lanou.dicegame.handler.game.GameRoomHandler;
import club.lanou.dicegame.utils.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 游戏服务器
 *
 */
public class GameServer {
    private ServerSocket serverSocket;
    private final ExecutorService acceptExecutorService;
    private final ExecutorService gameRoomHandleExecutorService;
    private final Map<String, GameRoom> roomMap;

    public GameServer(){
        this.acceptExecutorService = Executors.newSingleThreadExecutor();
        this.gameRoomHandleExecutorService = Executors.newCachedThreadPool();
        this.roomMap = new ConcurrentHashMap<>();
    }

    /**
     * 启动游戏服务器，开启服务端的Constant.GAME_SERVER_PORT端口
     * 循环接收客户端对服务器Constant.GAME_SERVER_PORT端口的连接
     * -------------------------------
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void boot() {
        try {
            serverSocket = new ServerSocket(Constant.GAME_SERVER_PORT);
            acceptExecutorService.execute(() -> {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();      // 接收客户端连接
                        gameRoomHandleExecutorService.execute(new GameRoomHandler(socket)); // 提交到游戏房间处理线程池
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
