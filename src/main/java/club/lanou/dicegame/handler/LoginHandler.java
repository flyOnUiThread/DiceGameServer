package club.lanou.dicegame.handler;

import club.lanou.dicegame.bean.User;
import club.lanou.dicegame.service.UserService;
import club.lanou.dicegame.utils.Constant;
import com.alibaba.fastjson.JSON;

import java.net.DatagramPacket;

/**
 * 用户登录处理
 * 通过上传的密码+用户名加密字段来查找用户，存在则向客户端回复1，否则回复0
 */
public class LoginHandler implements Runnable{
    private final DatagramPacket datagramPacket;
    private final UserService userService = new UserService();

    public LoginHandler(DatagramPacket datagramPacket){
        this.datagramPacket = datagramPacket;
    }

    @Override
    public void run() {
        System.out.println("来自" + datagramPacket.getAddress() + "的登录请求！");

        String msg = new String(datagramPacket.getData());

        User user = userService.queryUserByPassword(msg);
        System.out.println("登录" + user);
        byte[] res;
        if (user == null){          // 未找到该用户向客户端返回空对象
            User nullUser = new User();
            res = JSON.toJSONBytes(nullUser);
        }else {
            // 登录成功后给用户信息脱敏，保留id、头像url、昵称，再发送给客户端
            user.setUserName(null);
            user.setUserPassword(null);
            user.setUserIconAbsPath(null);
            res = JSON.toJSONBytes(user);
        }
        // 通过登录端口回复客户端
        Reply.reply(res,datagramPacket, Constant.UDP_REPLY_PORT);

        // 测试是否被监听线程独占
        System.out.println("已发出！");
    }
}
