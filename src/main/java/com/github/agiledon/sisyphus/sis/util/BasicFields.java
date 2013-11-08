package com.github.agiledon.sisyphus.sis.util;

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

    private static Object parseFieldValue(String fieldTypeName, String value) {
        boolean nullOrEmptyValue = Strings.isNullOrEmpty(value);

        if ("String".equals(fieldTypeName)) {
            return nullOrEmptyValue ? "" : value;
        } else if ("Boolean".equalsIgnoreCase(fieldTypeName)) {
            return nullOrEmptyValue ? false : Boolean.parseBoolean(value);
        } else if ("Integer".equalsIgnoreCase(fieldTypeName)) {
            return nullOrEmptyValue ? 0 : Integer.valueOf(value);
        } else if ("int".equalsIgnoreCase(fieldTypeName)) {
            return nullOrEmptyValue ? 0 : Integer.valueOf(value);
        } else if ("byte[]".equals(fieldTypeName)) {
            return nullOrEmptyValue ? "0".getBytes() : value.getBytes();
        } else if ("Float".equalsIgnoreCase(fieldTypeName)) {
            return nullOrEmptyValue ? 0 : Float.valueOf(value);
        } else if ("Double".equalsIgnoreCase(fieldTypeName)) {
            return nullOrEmptyValue ? 0 : Double.valueOf(value);
        } else {
            return nullOrEmptyValue ? BigInteger.valueOf(0) : BigInteger.valueOf(Long.parseLong(value));
        }
    }

    private static boolean isPrimitiveType(String fieldTypeName) {
        return "String".equals(fieldTypeName)
                || "Boolean".equalsIgnoreCase(fieldTypeName)
                || "BigInteger".equals(fieldTypeName)
                || "Integer".equals(fieldTypeName)
                || "int".equals(fieldTypeName)
                || "Float".equalsIgnoreCase(fieldTypeName)
                || "Double".equalsIgnoreCase(fieldTypeName)
                || "byte[]".equals(fieldTypeName);
    }
}