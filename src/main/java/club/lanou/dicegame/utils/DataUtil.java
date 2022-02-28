package club.lanou.dicegame.utils;

public class DataUtil {
    /**
     * 字节数组转long
     * @param b 字节数组
     * @return long
     */
    public static long bytesToLong(byte[] b) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (b[i] & 0xFF);
        }
        return result;
    }

    /**
     * long转字节数组
     * @param l long
     * @return 字节数组
     */
    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }
}
