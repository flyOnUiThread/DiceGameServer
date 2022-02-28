package club.lanou.dicegame.handler.game;

import club.lanou.dicegame.bean.GameRoom;

import java.net.Socket;
import java.util.Map;

public class GameRoomHandler implements Runnable{
    private GameRoom room;
    private final Socket playerSocket;
    public GameRoomHandler(Socket playerSocket) {
        this.playerSocket = playerSocket;
    }

    @Override
    public void run() {

    }
}
