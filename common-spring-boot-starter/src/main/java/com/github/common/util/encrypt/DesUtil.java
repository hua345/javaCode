package com.github.common.util.encrypt;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DesUtil {
    public static void main(String[] args) {
        //加密字符串
        String message = "helloWorld";
        String aesKey = UuidUtil.getUUID8();
        System.out.println("message: " + message);
        System.out.println("aesKey: " + aesKey);
        String encryptStr = encryptDES(message, aesKey);
        System.out.println("encryptStr: " + encryptStr);
        String decryptStr = decryptDES(encryptStr, aesKey);
        System.out.println("encryptStr: " + decryptStr);
    }


    /**
     * 加密 这个iv偏移量是数组！！
     */
    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};

    public static String encryptDES(String encryptString, String encryptKey) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 解密
     */
    public static String decryptDES(String decryptString, String decryptKey) {
        try {
            byte[] byteMi = Base64.getDecoder().decode(decryptString);
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);

            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}