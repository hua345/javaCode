package com.github.chenjianhua.common.encrypt.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenjianhua
 * @date 2021/4/9
 */
class Sha256Md5UtilTest {
    private static final String ORIGIN_STR = "123456";

    @Test
    @DisplayName("测试sha256(md5)")
    public void testSha256Md5() {
        String md5Hex = DigestUtils.md5DigestAsHex(ORIGIN_STR.getBytes(StandardCharsets.UTF_8));
        String shaValue = Sha256Md5Util.sha256Encode(md5Hex);
        assertEquals(md5Hex, "e10adc3949ba59abbe56e057f20f883e");
        assertEquals(shaValue, "-cgbbvs1qjuluer06p436127brppnc4sk7k9k8odb1a8dch3g4ik");
    }
}