package com.github.agiledon.sisyphus.util;

public final class StringUtil {
    private StringUtil() {
    }

    public static String spaces(int count) {
        StringBuilder builder = new StringBuilder();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

}
