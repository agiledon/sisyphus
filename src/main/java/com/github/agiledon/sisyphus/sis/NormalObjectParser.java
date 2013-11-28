package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.sis.util.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static com.github.agiledon.sisyphus.sis.util.Reflection.getFieldValue;
import static com.github.agiledon.sisyphus.sis.util.Reflection.isPrimitiveType;

public class NormalObjectParser extends ObjectParser {
    public NormalObjectParser(boolean ignoreNullField) {
        super(ignoreNullField);
    }

    @Override
    public <T> SisClass parseClass(T sourceObject, String fieldName, int level) {
        SisNormalClass sisNormalClass = new SisNormalClass(fieldName);
        sisNormalClass.setCurrentLevel(level);

        Field[] declaredFields = sourceObject.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            setOrIgnoreFieldValue(sisNormalClass, sourceObject, declaredField, level);
        }
        return sisNormalClass;
    }

    private <T> void setOrIgnoreFieldValue(SisNormalClass sisNormalClass, T sourceObject, Field declaredField, int level) {
        try {
            declaredField.setAccessible(true);
            Object fieldValue = declaredField.get(sourceObject);
            if (fieldValue == null && isIgnoreNullField()) {
                return;
            }
            if (isPrimitiveType(declaredField.getType())) {
                addBasicField(sisNormalClass, declaredField, fieldValue);
            } else {
                addChildClass(sisNormalClass, declaredField, level, fieldValue);
            }
        } catch (IllegalAccessException e) {
            logAndRethrowException(e);
        } catch (Exception e) {
            logAndRethrowException(e);
        }
    }

    private void addChildClass(SisNormalClass sisNormalClass, Field declaredField, int level, Object fieldValue) {
        try {
            if (fieldValue == null) {
                fieldValue = Reflection.createInstance(declaredField.getType());
            }
            sisNormalClass.addChildClass(parseChildClass(fieldValue, declaredField.getName(), level));
        } catch (IllegalAccessException e) {
            logAndRethrowException(e);
        } catch (InstantiationException e) {
            logAndRethrowException(e);
        } catch (InvocationTargetException e) {
            logAndRethrowException(e);
        }
    }

    private void addBasicField(SisNormalClass sisNormalClass, Field declaredField, Object fieldValue) {
        if (fieldValue == null) {
            fieldValue = getFieldValue(declaredField.getType(), "");
        }
        sisNormalClass.addBasicField(new BasicField(declaredField.getName(), fieldValue.toString()));
    }

}
