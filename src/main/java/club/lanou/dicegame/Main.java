package club.lanou.dicegame;

import club.lanou.dicegame.server.FileServer;
import club.lanou.dicegame.server.LoginServer;
import club.lanou.dicegame.server.RegisterServer;

public class Main {
    public static void main(String[] args) {
        LoginServer loginServer = new LoginServer();
        RegisterServer registerServer = new RegisterServer();
        FileServer fileServer = new FileServer();

        loginServer.boot();
        registerServer.boot();
        fileServer.boot();
        CMD.boot();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("服务器已关闭");
    }
}
