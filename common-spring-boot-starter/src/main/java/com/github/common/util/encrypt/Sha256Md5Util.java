package com.github.common.util.encrypt;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author chenjianhua
 * @date 2020/12/07
 */
@Slf4j
public class Sha256Md5Util {

    /**
     * 密码加密
     */
    public static String sha256Encode(String s) {
        BigInteger sha;
        byte[] inputData = s.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(inputData);
            sha = new BigInteger(messageDigest.digest());
            return sha.toString(32);
        } catch (Exception e) {
            log.error("加密异常：", e);
            return null;
        }
    }


    public static void main(String[] args) {
        String password = "123456";
        String md5Hex = DigestUtil.md5Hex(password);
        String shaValue = sha256Encode(md5Hex);
        log.info("{} md5:{}", password, md5Hex);
        log.info("{} SHA256(md5):{}", password, shaValue);
    }
}

