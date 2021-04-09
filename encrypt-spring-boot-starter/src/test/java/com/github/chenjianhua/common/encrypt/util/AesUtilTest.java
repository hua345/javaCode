package com.github.chenjianhua.common.encrypt.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2021/4/9
 */
@Slf4j
class AesUtilTest {
    private static final String ORIGIN_STR = "helloWorld";

    @Test
    @DisplayName("测试aes加密解密")
    void testAesEncrypt() {
        String uuid = UuidUtil.getUUID32();
        log.info("uuid:{}", uuid);
        String encodedRequestJson = AesUtil.encryptToBase64(ORIGIN_STR, uuid);
        log.info("{} AES encode: {}", ORIGIN_STR, encodedRequestJson);
        String messageDe = AesUtil.decryptFromBase64(encodedRequestJson, uuid);
        assertEquals(ORIGIN_STR, messageDe);
    }

    @Test
    void testJsAesEncrypt() {
        String uuid = UuidUtil.getUUID32();
        String encodedRequestJson = AesUtil.encryptToBase64(ORIGIN_STR, uuid);
        log.info("{} AES encode: {}", ORIGIN_STR, encodedRequestJson);
        String messageDe = AesUtil.decryptFromBase64(encodedRequestJson, uuid);
        assertEquals(ORIGIN_STR, messageDe);
        // node cryptojsTest.js helloworld 75655c3553aa438f90dc811c1558c43d
        // String strCmd = "cmd /c node F:\\Code\\Code\\common-spring-boot-starter\\src\\main\\java\\com\\github\\common\\util\\encrypt\\cryptojsTest.js " + ORIGIN_STR + " " + uuid;
        String strCmd = "node /Users/chenjianhua/Code/Code/encrypt-spring-boot-starter/src/main/java/com/github/chenjianhua/common/encrypt/util/cryptojsTest.js " + ORIGIN_STR + " " + uuid;
        // 对于Java中AES的默认模式是：AES/ECB/PKCS5Padding
        // 如果使用CryptoJS，请调整为：padding: CryptoJS.pad.Pkcs7,new AES("AES/ECB/PKCS7Padding")
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

}