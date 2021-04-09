package com.github.chenjianhua.common.encrypt.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2021/4/9
 */
@Slf4j
class RsaUtilTest {
    private static final String ORIGIN_STR = "helloWorld";

    @Test
    @DisplayName("测试rsa加解密")
    void testRsaEncrypt() throws Exception {
        //生成公钥和私钥
        Map<Integer, String> keyMap = RsaUtil.genKeyPair();
        log.info("生成公钥: {}", keyMap.get(0));
        log.info("生成私钥:{}", keyMap.get(1));

        // 使用公钥加密
        String messageEn = RsaUtil.encryptToBase64Str(ORIGIN_STR, keyMap.get(0));
        log.info("RSA({}): {}", ORIGIN_STR, messageEn);
        // 使用私钥解密
        String messageDe = RsaUtil.decryptBase64Str(messageEn, keyMap.get(1));
        assertEquals(ORIGIN_STR, messageDe);
    }

    @Test
    @DisplayName("测试rsa签名和验签")
    void testRsaSign() throws Exception {
        //生成公钥和私钥
        Map<Integer, String> keyMap = RsaUtil.genKeyPair();
        log.info("生成公钥: {}", keyMap.get(0));
        log.info("生成私钥:{}", keyMap.get(1));

        // 使用私钥加签
        String messageSign = RsaUtil.getRsaSignWithBase64(ORIGIN_STR, keyMap.get(1));
        log.info("RSA Sign({}): {}", ORIGIN_STR, messageSign);
        boolean checkResult = RsaUtil.checkRsaSignWithBase64(ORIGIN_STR, messageSign, keyMap.get(0));
        assertEquals(true, checkResult);
    }
}