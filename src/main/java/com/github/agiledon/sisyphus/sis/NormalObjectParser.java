package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.sis.util.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class NormalObjectParser extends ObjectParser {
    public NormalObjectParser(boolean ignoreNullField) {
        super(ignoreNullField);
    }

    @Override
    public <T> SisClass parseClass(T sourceObject, String fieldName, int level) {
        return createNormalClass(sourceObject, fieldName, level);
    }

    private <T> SisNormalClass createNormalClass(T sourceObject, String fieldName, int level) {
        SisNormalClass sisNormalClass = new SisNormalClass(fieldName);
        sisNormalClass.setCurrentLevel(level);

        Field[] declaredFields = sourceObject.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            setOrIgnoreFieldValue(sourceObject, sisNormalClass, declaredField, level);
        }
        return sisNormalClass;
    }

    private <T> void setOrIgnoreFieldValue(T sourceObject, SisNormalClass sisNormalClass, Field declaredField, int level) {
        try {
            declaredField.setAccessible(true);
            Object fieldValue = declaredField.get(sourceObject);
            if (fieldValue == null && isIgnoreNullField()) {
                return;
            }
            if (Reflection.isPrimitiveType(declaredField.getType())) {
                if (fieldValue == null) {
                    fieldValue = Reflection.getFieldValue(declaredField.getType(), "");
                }
                sisNormalClass.addBasicField(new BasicField(declaredField.getName(), fieldValue.toString()));
            } else {
                if (fieldValue == null) {
                    fieldValue = Reflection.createInstance(declaredField.getType());
                }
                sisNormalClass.addChildClass(parseChildClass(fieldValue, declaredField.getName(), level));
            }
        } catch (IllegalAccessException e) {
            logAndRethrowException(e);
        } catch (InstantiationException e) {
            logAndRethrowException(e);
        } catch (InvocationTargetException e) {
            logAndRethrowException(e);
        } catch (Exception e) {
            logAndRethrowException(e);
        }
    }

}
