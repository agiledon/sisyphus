package com.github.agiledon.sisyphus.sis.util;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import com.google.common.base.Strings;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class Reflection {
    private Reflection() {}

    private static Logger logger = LoggerFactory.getLogger(Reflection.class);

    public static Object getFieldValue(Class<?> fieldType, String value)  {
        if (isPrimitiveType(fieldType)) {
            return parseFieldValue(fieldType, value);
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
        valueField.set(mainFieldValue, parseFieldValue(fieldType, innerValue));
    }

    @SuppressWarnings("unchecked")
    private static Object parseFieldValue(Class<?> fieldType, String value) {
        boolean nullOrEmptyValue = Strings.isNullOrEmpty(value);

        if (isEnum(fieldType)) {
            return nullOrEmptyValue ? fieldType.getEnumConstants()[0] : Enum.valueOf((Class<Enum>) fieldType, value.trim().toUpperCase());
        }
        String fieldTypeName = fieldType.getSimpleName();

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

    private static boolean isEnum(Class<?> fieldType) {
        return fieldType.getSuperclass() != null && "Enum".equals(fieldType.getSuperclass().getSimpleName());
    }

    public static boolean isPrimitiveType(Class<?> aClass) {
        String fieldTypeName = aClass.getSimpleName();
        return  "String".equals(fieldTypeName)
                || "Boolean".equalsIgnoreCase(fieldTypeName)
                || "BigInteger".equals(fieldTypeName)
                || "Integer".equals(fieldTypeName)
                || "int".equals(fieldTypeName)
                || "Float".equalsIgnoreCase(fieldTypeName)
                || "Double".equalsIgnoreCase(fieldTypeName)
                || "byte[]".equals(fieldTypeName)
                || isEnum(aClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T createInstance(Class<T> currentClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor<?>[] constructors = currentClass.getConstructors();
        if (constructors.length < 1) {
            return currentClass.newInstance();
        }
        final Constructor<T> constructor = (Constructor<T>) constructors[0];
        final List<Object> params = new ArrayList<Object>();
        for (Class<?> parameterType : constructor.getParameterTypes())
        {
            params.add((parameterType.isPrimitive()) ? ClassUtils.primitiveToWrapper(parameterType).newInstance() : null);
        }
        return constructor.newInstance(params.toArray());
    }

    public static Class<?> getElementTypeForList(Field childField) {
        ParameterizedType integerListType = (ParameterizedType) childField.getGenericType();
        return (Class<?>) integerListType.getActualTypeArguments()[0];
    }

    public static <T> Class<?> getElementTypeForArray(Class<T> currentClass) throws ClassNotFoundException {
        String canonicalName = currentClass.getCanonicalName();
        return Class.forName(canonicalName.substring(0, canonicalName.length() - 2));
    }
}