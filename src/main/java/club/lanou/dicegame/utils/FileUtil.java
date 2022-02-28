package club.lanou.dicegame.utils;

import java.io.File;

public class FileUtil {
    /**
     * 删除文件，
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath){
        return new File(filePath).delete();
    }
}
