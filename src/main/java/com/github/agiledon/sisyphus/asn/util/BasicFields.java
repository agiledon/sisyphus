package com.github.agiledon.sisyphus.asn.util;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigInteger;

public abstract class BasicFields {
    private static Logger logger = LoggerFactory.getLogger(BasicFields.class);

    public static Object getFieldValue(Class<?> fieldType, String value)  {
        String fieldTypeName = fieldType.getSimpleName();
        if (isPrimitiveType(fieldTypeName)) {
            return parseFieldValue(fieldTypeName, value);
        }
        Object fieldValue;
        try {
            fieldValue = fieldType.newInstance();
            setInnerValueField(fieldType, fieldValue, value);
        } catch (Throwable t) {
            String errorMessage = String.format("Failed to set the failed value for %s with %s", fieldType.getName(), value);
            logger.error(errorMessage);
            throw new FailedDeserializationException(errorMessage);
        }
        return fieldValue;
    }

    private static void setInnerValueField(Class<?> fieldType, Object mainFieldValue, String innerValue) throws NoSuchFieldException, IllegalAccessException {
        Field valueField = fieldType.getField("value");
        valueField.set(mainFieldValue, parseFieldValue(valueField.getType().getSimpleName(), innerValue));
    }

    private static Object parseFieldValue(String fieldTypeName, String value1) {
        boolean nullOrEmptyValue = Strings.isNullOrEmpty(value1);

        if ("String".equals(fieldTypeName)) {
            return nullOrEmptyValue ? " " : value1;
        } else if ("byte[]".equals(fieldTypeName)) {
            return nullOrEmptyValue ? "0".getBytes() : value1.getBytes();
        } else {
            return nullOrEmptyValue ? BigInteger.valueOf(0) : BigInteger.valueOf(Integer.parseInt(value1));
        }
    }

    private static boolean isPrimitiveType(String fieldTypeName) {
        return "String".equals(fieldTypeName)
                || "BigInteger".equals(fieldTypeName)
                || "byte[]".equals(fieldTypeName);
    }
}