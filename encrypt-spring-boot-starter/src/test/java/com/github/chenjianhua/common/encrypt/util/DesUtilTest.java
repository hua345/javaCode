package com.github.chenjianhua.common.encrypt.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2021/4/9
 */
@Slf4j
class DesUtilTest {
    //加密字符串
    private static final String ORIGIN_STR = "helloWorld";

    @Test
    @DisplayName("测试des加密解密")
    void testEncryptDes() {
        String aesKey = UuidUtil.getUUID8();
        log.info("aesKey:{}", aesKey);
        String encryptStr = DesUtil.encryptDes(ORIGIN_STR, aesKey);
        log.info("des encryptStr:{}", encryptStr);
        String decryptStr = DesUtil.decryptDes(encryptStr, aesKey);
        assertEquals(ORIGIN_STR, decryptStr);
    }
}