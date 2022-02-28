package club.lanou.dicegame.handler.game;

import java.net.Socket;

public class GameHandler implements Runnable{
    private final Socket socket;

    public GameHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
