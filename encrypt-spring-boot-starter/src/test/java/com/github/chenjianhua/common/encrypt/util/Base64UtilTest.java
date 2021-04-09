package com.github.chenjianhua.common.encrypt.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2021/4/9
 */
class Base64UtilTest {
    private static final String ORIGIN_STR = "123456";

    @Test
    @DisplayName("测试base64加密解密")
    public void testBase64() {
        String base64Str = Base64Util.encryptToBase64Str(ORIGIN_STR);
        String decryptStr = Base64Util.decryptBase64Str(base64Str);
        assertEquals(base64Str,"MTIzNDU2");
        assertEquals(ORIGIN_STR, decryptStr);
    }
}