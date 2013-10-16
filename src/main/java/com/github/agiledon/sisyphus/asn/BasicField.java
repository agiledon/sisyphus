package com.github.agiledon.sisyphus.asn;

import java.lang.reflect.Field;
import java.math.BigInteger;

public class BasicField {
    private String name;
    private String value;

    public BasicField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Field field = aClass.getDeclaredField(name);
        field.set(mainObject, getFieldValue(field.getType()));
    }

    private Object getFieldValue(Class<?> fieldType) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        String fieldTypeName = fieldType.getSimpleName();
        if (isPrimitiveType(fieldTypeName)) {
            return parseFieldValue(fieldTypeName);
        }
        Object fieldValue = fieldType.newInstance();
        setInnerValueField(fieldType, fieldValue);
        return fieldValue;
    }

    private void setInnerValueField(Class<?> fieldType, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
        Field valueField = fieldType.getField("value");
        valueField.set(fieldValue, parseFieldValue(valueField.getType().getSimpleName()));
    }

    private Object parseFieldValue(String fieldTypeName) {
        if ("String".equals(fieldTypeName)) {
            return value;
        } else if ("byte[]".equals(fieldTypeName)) {
            return value.getBytes();
        }else {
            return BigInteger.valueOf(Integer.parseInt(value));
        }
    }

    private boolean isPrimitiveType(String fieldTypeName) {
        return "String".equals(fieldTypeName)
                || "BigInteger".equals(fieldTypeName)
                || "byte[]".equals(fieldTypeName);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BasicField) {
            BasicField that = (BasicField) o;
            if (name != null ? !name.equals(that.name) : that.name != null) {
                return false;
            }
            if (value != null ? !value.equals(that.value) : that.value != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
