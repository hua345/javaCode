package com.github.chenjianhua.common.id.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenjianhua
 * @date 2021/6/1
 * 前缀 + 14位年月日时分秒长度 + 2位机器长度 + 6位数字长度
 * 前缀 + 24位字符串长度
 */
@Slf4j
public class SnowFlakeStr {
    /**
     * 14位年月日时分秒长度
     */
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 6位数字长度,每秒最大生成999999
     */
    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("000000");

    private final static Integer MAX_SEQ = 999000;

    private final static Integer MIN_TWO_LENGTH_MACHINE_ID = 10;

    private static String lastDateTimeStr;

    private AtomicInteger atomicInteger = new AtomicInteger();

    private String prefix;
    /**
     * machineId范围:0~64
     */
    private Long machineId;

    public SnowFlakeStr(String prefix, Long machineId) {
        this.prefix = prefix;
        this.machineId = machineId;
    }

    public synchronized String getOrderNumber() {
        StringBuilder sb = new StringBuilder(this.prefix);
        String currentDateTimeStr = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        sb.append(currentDateTimeStr);
        if (MIN_TWO_LENGTH_MACHINE_ID <= machineId) {
            sb.append(machineId);
        } else {
            sb.append("0").append(machineId);
        }
        Integer sequenceNum;
        if (null == lastDateTimeStr || currentDateTimeStr.equals(lastDateTimeStr)) {
            sequenceNum = atomicInteger.getAndIncrement();
            if (sequenceNum >= MAX_SEQ) {
                atomicInteger.set(1);
            }
        } else {
            log.info("currentDateTimeStr:{},lastDateTimeStr:{}", currentDateTimeStr, lastDateTimeStr);
            atomicInteger.set(1);
            sequenceNum = 0;
        }
        lastDateTimeStr = currentDateTimeStr;
        sb.append(DECIMAL_FORMAT.format(sequenceNum));
        return sb.toString();
    }
}
