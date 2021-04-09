//package com.github.chenjianhua.common.encrypt.util;
//
//import cn.hutool.crypto.Mode;
//import cn.hutool.crypto.Padding;
//import cn.hutool.crypto.symmetric.AES;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//
//import java.nio.charset.StandardCharsets;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
///**
// * @author chenjianhua
// * @date 2021/4/9
// */
//@Slf4j
//public class HutoolTest {
//    private static final String ORIGIN_STR = "helloWorld";
////        <dependency>
////            <groupId>cn.hutool</groupId>
////            <artifactId>hutool-crypto</artifactId>
////            <version>5.5.2</version>
////        </dependency>
//    @Test
//    void testHutoolAES() {
//        String uuid = UuidUtil.getUUID32();
//        AES aesObj = new AES(Mode.CBC, Padding.PKCS5Padding, uuid.getBytes(),
//                uuid.substring(0, 16).getBytes());
//        String encryptData = aesObj.encryptBase64(ORIGIN_STR, StandardCharsets.UTF_8);
//        log.info("encryptData: {}", encryptData);
//        String decryptData = aesObj.decryptStr(encryptData, StandardCharsets.UTF_8);
//        assertEquals(ORIGIN_STR, decryptData);
//    }
//}
