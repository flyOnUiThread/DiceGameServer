package club.lanou.dicegame.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String md5(String passwd){
        byte[] b = passwd.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            b = messageDigest.digest(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new BigInteger(1,b).toString(16);
    }
}