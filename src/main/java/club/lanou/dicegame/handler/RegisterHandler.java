package club.lanou.dicegame.handler;

import club.lanou.dicegame.bean.User;
import club.lanou.dicegame.service.UserService;
import club.lanou.dicegame.utils.Constant;
import club.lanou.dicegame.utils.DataUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * 用户注册处理线程
 * 将客户端上传的json通过解析器解析成JsonObject，获取其中的信息
 * 用json中包含的用户信息插入数据库，根据向客户端返回插入结果
 * 向客户端回传三种结果，1：成功，2：异常，0：失败
 */
public class RegisterHandler implements Runnable {
    private final Socket clientSocket;
    private final UserService userService = new UserService();

    public RegisterHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("来自" + clientSocket.getInetAddress() + "的注册请求！");
        byte[] res;
        InputStream in = null;
        OutputStream os = null;
        try {
            in = clientSocket.getInputStream();
            // 读取上传数据长度
            byte[] len = new byte[8];
            in.read(len);

            byte[] reciveBytes = new byte[(int) DataUtil.bytesToLong(len)];
            in.read(reciveBytes);
            JSONObject jsonObject = (JSONObject) JSON.parse(new String(reciveBytes));

            User user = new User();
            user.setUserName(jsonObject.getString("userName"));

            res = userService.isUserExist(user.getUserName()) ? Constant.ABNORMAL : null;
            if (res == null){
                user.setUserPassword(jsonObject.getString("userPassword"));
                user.setUserNickName(jsonObject.getString("userNickName"));
                user.setUserIconUrl(jsonObject.getString("userIconUrl"));
                res = userService.addUser(user) ? Constant.SUCCESS : Constant.FAIL;
            }
            System.out.println(Arrays.toString(res));
            os = clientSocket.getOutputStream();
            os.write(res);
            System.out.println("注册服务结束");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                clientSocket.close();

                if (in != null) in.close();

                if (os != null) os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
