package club.lanou.dicegame.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Reply {
    /**
     * 通过随机端口向客户端回复结果
     * @param res 要回复的数据
     * @param datagramPacket 客户端请求的数据包，里面会包含客户端的ip和端口
     *
     */
    public static void reply(byte[] res, DatagramPacket datagramPacket, int sendPort){
        try {
            System.out.println("向" + datagramPacket.getAddress() + "回复" + new String(res));
            DatagramPacket resDatagramPacket = new DatagramPacket(res, res.length,
                    datagramPacket.getAddress(), datagramPacket.getPort());
            DatagramSocket datagramSocket = new DatagramSocket(new InetSocketAddress(sendPort));
            datagramSocket.send(resDatagramPacket);
            datagramSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
