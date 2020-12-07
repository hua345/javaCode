package com.github.common.util.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author chenjianhua
 * @date 2020/12/07
 */
@Slf4j
public class Base64Util {
    private final static Base64.Decoder base64Decoder = Base64.getDecoder();
    private final static Base64.Encoder base64Encoder = Base64.getEncoder();

    /**
     * Base64加密
     */
    public static String encryptToBase64Str(String originStr) {
        if (StringUtils.isEmpty(originStr)) {
            return null;
        }
        return base64Encoder.encodeToString(originStr.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64解密
     */
    public static String decryptBase64Str(String encryptStr) {
        return new String(base64Decoder.decode(encryptStr), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String originStr = "123456";
        String base64Str = Base64Util.encryptToBase64Str(originStr);
        log.info("base64 btoa({}): {}", originStr, base64Str);
        log.info("base64 atob({}): {}", base64Str, Base64Util.decryptBase64Str(base64Str));
    }
}
