package club.lanou.dicegame.utils;


public class Constant {
    public static final String USER_ICON_FOLDER_PATH = "D:/test/";
    public static final String USER_ICON_URL = "http://106.14.227.128/download/usericon/";

    public static final int LOGIN_SERVER_PORT = 52519;
    public static final int REGISTER_SERVER_PORT = 52520;
    public static final int UDP_REPLY_PORT = 52521;
    public static final int FILE_SERVER_PORT = 52522;
    public static final int GAME_SERVER_PORT = 52523;
    public static final int INFO_SERVER_PORT = 52524;

    public static final byte[] SUCCESS = "1".getBytes();
    public static final byte[] ABNORMAL = "2".getBytes();
    public static final byte[] FAIL = "0".getBytes();


    public static final String HOMEOWNER_ROOM_ID = "00 000000";

    public static final String SERVER_RUNTIME_LOG_PATH = "D:/test/ServerRunTimeLog.log";

    public static final String CMD_HELP = "makelog：打印当前服务器运行日志到" + SERVER_RUNTIME_LOG_PATH + "\n" +
            "close：会关闭服务器\n" + "命令输入不区分大小写";
}
