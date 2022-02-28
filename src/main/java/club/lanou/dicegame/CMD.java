package club.lanou.dicegame;

import club.lanou.dicegame.utils.Constant;

import java.util.Scanner;

public class CMD {
    public static void boot(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("makelog")){
                System.out.println("日志文件已追加输出到" + Constant.SERVER_RUNTIME_LOG_PATH);
            }else if (command.equalsIgnoreCase("close")){
                System.out.println("服务器正在关闭...");
                System.exit(0);
            }else if (command.equalsIgnoreCase("help")){
                System.out.println(Constant.CMD_HELP);
            }
        }
    }
}
