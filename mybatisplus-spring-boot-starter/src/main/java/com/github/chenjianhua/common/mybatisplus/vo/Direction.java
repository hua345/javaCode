package com.github.chenjianhua.common.mybatisplus.vo;

import java.util.Locale;
import java.util.Optional;

/**
 * @author chenjianhua
 * @date 2021/4/23
 */
public enum Direction {

    ASC,
    DESC;

    Direction() {
    }

    public boolean isAscending() {
        return this.equals(ASC);
    }

    public boolean isDescending() {
        return this.equals(DESC);
    }

    public static Direction fromString(String value) {
        try {
            return valueOf(value.toUpperCase(Locale.US));
        } catch (Exception var2) {
            throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), var2);
        }
    }

    public static Optional<Direction> fromOptionalString(String value) {
        try {
            return Optional.of(fromString(value));
        } catch (IllegalArgumentException var2) {
            return Optional.empty();
        }
    }
}
