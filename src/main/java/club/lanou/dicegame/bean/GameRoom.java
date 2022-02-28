package club.lanou.dicegame.bean;

import club.lanou.dicegame.utils.Constant;

import java.net.Socket;
import java.util.Objects;

public class GameRoom {
    private String id;
    private Socket homeowner;
    private final Socket[] players;
    private boolean isStarted;
    private boolean isClosed;
    private Integer gameId;

    //
    public GameRoom(Socket homeowner){
        players = new Socket[4];
        generateRoomId();
    }

    public boolean addPlayer(Socket socket){
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null){
                players[i] = socket;
                return true;
            }
        }
        return false;
    }

    public Socket removePlayer(int index){
        Socket player = players[index];
        players[index] = null;
        return player;
    }

    public Socket getHomeowner(){
        return homeowner;
    }

    public void setHomeowner(Socket homeowner) {
        this.homeowner = homeowner;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public Socket[] getPlayers() {
        return players;
    }

    public String getRoomId(){
        return id;
    }

    // 随机生成8位房间号
    private void generateRoomId(){
        // 保证不会出现房间id为Constant.HOMEOWNER_ROOM_ID
        while (Objects.equals(id, Constant.HOMEOWNER_ROOM_ID)){
            int a = (int) (Math.random() * 10);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                sb.append(a);
            }
            id = sb.toString();
        }
    }
}
