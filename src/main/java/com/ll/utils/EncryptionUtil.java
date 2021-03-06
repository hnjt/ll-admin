package com.ll.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密
 */
public class EncryptionUtil {

    @SuppressWarnings("unused")
    private static final String Algorithm = "DESede";

    private static final byte[] keyBytes = { -81, 34, 79, 88, -34, 16, 64, -69, 40, 37, 121, 81, -53, -17, -85, 102,
            -66, 41, 124, -35, 48, 64, 54, -30 };

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    private static byte[] hexStringToByte(String hexString) {
        int length = hexString.length() / 2;
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            int tempInt = Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16);
            b[i] = ((byte) tempInt);
        }
        return b;
    }

    public static String encrypt(String inStr) {
        StringBuffer inoutStr = new StringBuffer(inStr);
        inoutStr.delete(0, inoutStr.length());
        try {
            SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
            Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            c1.init(Cipher.ENCRYPT_MODE, deskey);//内核不同加密结果不同
            byte[] digest = c1.doFinal(inStr.getBytes());
            inoutStr.append(byte2hex(digest));
        } catch (Exception e) {
            e.printStackTrace();
            return "error.encrypt";
        }
        return inoutStr.toString();
    }

//    todo encrypt bak function
/*   public static String encrypt(String inStr) {
       StringBuffer inoutStr = new StringBuffer(inStr);
       inoutStr.delete(0, inoutStr.length());
       try {
           SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
           Cipher c1 = Cipher.getInstance("DESede");
           c1.init(1, deskey);
           byte[] digest = c1.doFinal(inStr.getBytes());
           inoutStr.append(byte2hex(digest));
       }
       catch (Exception e) {
           e.printStackTrace();
           return "error.encrypt";
       }
       return inoutStr.toString();
   }*/

    public static String decrypt(String inStr) {
        StringBuffer inoutStr = new StringBuffer(inStr);
        inoutStr.delete(0, inoutStr.length());
        try {
            SecretKey deskey = new SecretKeySpec(keyBytes, "DESede");
            Cipher cliper = Cipher.getInstance("DESede");
            cliper.init(2, deskey);
            byte[] digest = cliper.doFinal(hexStringToByte(inStr));
            inoutStr.append(new String(digest));
        } catch (Exception e) {
            e.printStackTrace();
            return "error.encrypt";
        }
        return inoutStr.toString();
    }

//     public static void main(String[] args) {
//     System.out.println(decrypt("5A49B598429B93B7BA183A86B3F49B93C3EE88BA6930576E"));
//     System.out.println(encrypt("12312312321321321"));
//     }
}
