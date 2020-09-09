package com.github.springbootjunittest.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author chenjianhua
 * @date 2020/9/9
 */
public class DateUtil {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentDate() {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        String dateString = formatter.format(time);
        return dateString;
    }
}
