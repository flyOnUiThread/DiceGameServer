package club.lanou.dicegame.handler;

import club.lanou.dicegame.bean.User;
import club.lanou.dicegame.service.UserService;
import club.lanou.dicegame.utils.Constant;
import club.lanou.dicegame.utils.DataUtil;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

/**
 * 存储用户头像线程
 * 将用户头像存入服务器指定文件夹，文件命名根据UUID生成随机文件名
 * 再将文件的URL存入数据库
 * 最后关闭套接字，客户端需要等待服务端关闭套接字后再关闭套接字并回收资源
 */
public class FileHandler implements Runnable {
    private final UserService userService = new UserService();
    private final Socket socket;

    public FileHandler(Socket socket) {
        this.socket = socket;
        try {
            // 设置read 10s超时
            socket.setSoTimeout(10000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        InputStream in = null;
        FileOutputStream fos = null;
        try {
            in = socket.getInputStream();

            // 流的前32字节为用户密码+用户名的md5加密字段值，用于校验用户是否存在
            byte[] checkData = new byte[32];
            in.read(checkData);
            String password = new String(checkData);

            // 如果通过加密字段找不到该用户则不继续向下执行
            User user = userService.queryUserByPassword(password);
            if (user == null){
                return;
            }

            // 生成随机文件名
            String randomFileName = UUID.randomUUID() + ".png";
            // 文件在服务器上的绝对路径
            File dir = new File(Constant.USER_ICON_FOLDER_PATH);
            if (!dir.exists()){
                dir.mkdirs();
            }
            String iconAbsPath = Constant.USER_ICON_FOLDER_PATH + randomFileName;

            // 读取8位的文件长度
            byte[] fileLengthBytes = new byte[8];
            in.read(fileLengthBytes);

            // 转成long
            long fileLengthLong = DataUtil.bytesToLong(fileLengthBytes);

            // 将头像文件写入拼接好的文件路径
            fos = new FileOutputStream(iconAbsPath);
            byte[] bytes = new byte[2048];
            int len;
            while (fileLengthLong != 0 && (len = in.read(bytes)) != 0) {
                fos.write(bytes, 0, len);
                fos.flush();
                fileLengthLong -= len;
            }

            // 拼接用户头像的url
            String userIconUrl = Constant.USER_ICON_URL + randomFileName;

            userService.updateUserIconURL(user.getId(), iconAbsPath, userIconUrl);
            OutputStream out = socket.getOutputStream();

            // 向客户端响应，解除read的阻塞状态
            out.write(0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }

                if (in != null){
                    in.close();
                }

                if (fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
