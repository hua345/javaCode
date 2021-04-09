package com.github.chenjianhua.common.encrypt.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author chenjianhua
 * @date 2020/12/07
 */
@Slf4j
public class AesUtil {
    private static final Integer AES_KEY_LENGTH = 16;
    private static final String KEY_ALGORITHM = "AES";
    // 默认的加密算法
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final String utf8CharSet = StandardCharsets.UTF_8.name();

    public static void main(String[] args) throws Exception {
        //加密字符串
        String message = "helloWorld";
        String uuid = UuidUtil.getUUID32();
        log.info("uuid:{}", uuid);
        String encodedRequestJson = AesUtil.encryptToBase64(message, uuid);
        log.info("{} AES encode: {}", message, encodedRequestJson);
        String messageDe = AesUtil.decryptFromBase64(encodedRequestJson, uuid);
        log.info("AES decode: {}", messageDe);

        String strCmd = "cmd /c node F:\\Code\\Code\\common-spring-boot-starter\\src\\main\\java\\com\\github\\common\\util\\encrypt\\cryptojsTest.js " + message + " " + uuid;
        try {
            Process ps = Runtime.getRuntime().exec(strCmd);
            InputStream is = ps.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
            ps.waitFor();
            is.close();
            reader.close();
            ps.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static String encryptToBase64(String data, String key) {
        if (!StringUtils.hasText(data) ||
                !StringUtils.hasText(key)) {
            throw new RuntimeException("AES data or Key is empty");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(key.substring(0, 16).getBytes());
            // 初始化,暂时不用IV模式
            cipher.init(Cipher.ENCRYPT_MODE, seckey, iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(utf8CharSet)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("aes encrypt fail!", e);
        }
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    public static String decryptFromBase64(String data, String key) {
        if (!StringUtils.hasText(data) ||
                !StringUtils.hasText(key)) {
            throw new RuntimeException("AES data or Key is empty");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(key.substring(0, 16).getBytes());
            // 初始化,暂时不用IV模式
            cipher.init(Cipher.DECRYPT_MODE, seckey, iv);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)), utf8CharSet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static String generateRandomKeyWithBase64() {
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(" generateRandomKey fail!", e);
        }
        SecureRandom random = new SecureRandom();
        //AES 要求密钥长度 must be equal to 128, 192 or 256
        keygen.init(128, random);
        Key key = keygen.generateKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
