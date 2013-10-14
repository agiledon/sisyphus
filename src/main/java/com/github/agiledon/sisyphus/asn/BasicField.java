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
        Class<?> fieldType = field.getType();
        String fieldTypeName = fieldType.getSimpleName();
        field.set(mainObject, getFieldValue(fieldType, fieldTypeName));
    }

    private Object getFieldValue(Class<?> fieldType, String fieldTypeName) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        Object fieldValue;

        if (!isPrimitiveType(fieldTypeName)) {
            fieldValue = fieldType.newInstance();

            //for specific type which has value field
            setInnerValueField(fieldType, fieldValue);
        } else {
            if ("BigInteger".equals(fieldTypeName)) {
                fieldValue = BigInteger.valueOf(Integer.parseInt(value));
            } else {
                fieldValue = value;
            }
        }
        return fieldValue;
    }

    private void setInnerValueField(Class<?> fieldType, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
        Field valueField = fieldType.getField("value");
        if ("String".equals(valueField.getType().getSimpleName())) {
            valueField.set(fieldValue, value);
        } else {
            valueField.set(fieldValue, BigInteger.valueOf(Integer.parseInt(value)));
        }
    }

    private boolean isPrimitiveType(String fieldTypeName) {
        return "String".equals(fieldTypeName) || "BigInteger".equals(fieldTypeName);
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
