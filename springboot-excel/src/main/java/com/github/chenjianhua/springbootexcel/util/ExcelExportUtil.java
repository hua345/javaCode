package com.github.chenjianhua.springbootexcel.util;

import com.github.chenjianhua.springbootexcel.bo.BeginAndEndTimeBo;
import com.github.common.util.DateFormatEnum;
import com.github.common.util.DateUtil;
import com.github.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author chenjianhua
 * @date 2020/12/22
 */
@Slf4j
public class ExcelExportUtil {
    public static final Integer minUnitNum = 200;

    /**
     * Integer -2147483648~2147483647
     */
    public static final Integer maxSheetNum = 10 * 100;

    public static final BigDecimal minUnitBigDecimal = BigDecimal.valueOf(minUnitNum);

    public static final BigDecimal maxSheetNumBigDecimal = BigDecimal.valueOf(maxSheetNum);

    public static Integer currentSheetNum(Long currentDataSize) {
        if (currentDataSize < maxSheetNum) {
            return 1;
        } else {
            return BigDecimal.valueOf(currentDataSize).divide(maxSheetNumBigDecimal, RoundingMode.UP).intValue();
        }
    }

    public static List<BeginAndEndTimeBo> split(Long exportCount, Date startTradeTime, Date endTradeTime) {
        // 开始时间和结束时间至少有一个不为空
        LocalDateTime endLocalDateTime = null;
        if (Objects.isNull(endTradeTime)) {
            endLocalDateTime = LocalDateTime.now();
        } else {
            endLocalDateTime = DateUtil.date2LocalDateTime(endTradeTime);
        }
        //  开始时间service层做了判断，一定是有值的
        LocalDateTime startLocalDateTime = DateUtil.date2LocalDateTime(startTradeTime);
        int splitNum = new BigDecimal(exportCount).divide(minUnitBigDecimal, RoundingMode.UP).intValue();
        splitNum = splitNum + 1;
        // 实际的结束实际会带时分秒，比ChronoUnit.DAYS.between多23:59:59
        long days = ChronoUnit.DAYS.between(startLocalDateTime, endLocalDateTime) + 1;
        // 时间分割步长
        int step = new BigDecimal(days).divide(BigDecimal.valueOf(splitNum), RoundingMode.UP).intValue();
        if (step < 1) {
            step = 1;
        }
        List<BeginAndEndTimeBo> beginAndEndTimeBoList = new ArrayList<>(splitNum);
        for (int i = 0; i < splitNum; i++) {
            BeginAndEndTimeBo beginAndEndTimeBo = new BeginAndEndTimeBo();
            beginAndEndTimeBo.setStartLocalDateTime(startLocalDateTime);
            beginAndEndTimeBo.setEndLocalDateTime(startLocalDateTime.plusDays(step));
            startLocalDateTime = beginAndEndTimeBo.getEndLocalDateTime();
            if (beginAndEndTimeBo.getEndLocalDateTime().isBefore(endLocalDateTime)) {
                beginAndEndTimeBoList.add(beginAndEndTimeBo);
            } else {
                beginAndEndTimeBo.setEndLocalDateTime(endLocalDateTime);
                beginAndEndTimeBoList.add(beginAndEndTimeBo);
                break;
            }
        }
        log.info("导出时间范围:{} ~ {},exportCount:{},分割次数:{},时间段大小:{},总天数:{},间隔天数:{}", DateUtil.formatDateTime(startTradeTime, DateFormatEnum.DATE_YYYY_MM_DD_HH_MM_SS), DateUtil.formatDateTime(endLocalDateTime, DateFormatEnum.DATE_YYYY_MM_DD_HH_MM_SS), exportCount, splitNum, beginAndEndTimeBoList.size(), days, step);
        return beginAndEndTimeBoList;
    }

    public static void main(String[] args) {
        log.info("currentSheetNum:{}", currentSheetNum(61 * 10000L));
        log.info("currentSheetNum:{}", currentSheetNum(1000L));

        LocalDateTime startLocalDateTime = LocalDateTime.of(2020, 1, 02, 00, 00, 00);
        LocalDateTime endLocalDateTime = LocalDateTime.of(2020, 12, 01, 23, 59, 59);
        List<BeginAndEndTimeBo> beginAndEndTimeBos = split(106 * 1000L, DateUtil.localDateTime2Date(startLocalDateTime), DateUtil.localDateTime2Date(endLocalDateTime));
        beginAndEndTimeBos.stream().forEach(item -> log.info(JsonUtil.toJSONString(item)));
        beginAndEndTimeBos = split(106 * 1000L, DateUtil.localDateTime2Date(startLocalDateTime), null);
        beginAndEndTimeBos.stream().forEach(item -> log.info(JsonUtil.toJSONString(item)));

        /***********************************************************************************************************/
        startLocalDateTime = LocalDateTime.of(2020, 8, 01, 00, 00, 00);
        endLocalDateTime = LocalDateTime.of(2020, 12, 23, 23, 59, 59);
        beginAndEndTimeBos = split(1036L, DateUtil.localDateTime2Date(startLocalDateTime), DateUtil.localDateTime2Date(endLocalDateTime));
        beginAndEndTimeBos.stream().forEach(item -> log.info(JsonUtil.toJSONString(item)));
    }
}
