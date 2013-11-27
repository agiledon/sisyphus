package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import com.github.agiledon.sisyphus.exception.FailedSerializationException;
import com.github.agiledon.sisyphus.sis.util.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ObjectParser {
    private final Logger logger = LoggerFactory.getLogger(ObjectParser.class);
    private boolean ignoreNullField = true;

    public ObjectParser(boolean ignoreNullField) {
        this.ignoreNullField = ignoreNullField;
    }

    public <T> SisClass parseClass(T sourceObject, String fieldName, int level) {
        if (sourceObject == null) {
            throw new FailedSerializationException("The target is null");
        }
        if (isArray(sourceObject)) {
            return createArrayClass(sourceObject, fieldName, level);
        }
        if (isList(sourceObject)) {
            return createListClass(sourceObject, fieldName, level);
        }

        return createNormalClass(sourceObject, fieldName, level);
    }

    <T> SisClass createArrayClass(T sourceObject, String fieldName, int level) {
        SisArrayClass sisArrayClass = new SisArrayClass(fieldName);
        sisArrayClass.setCurrentLevel(level);

        Object[] array = (Object[]) sourceObject;
        if (isIgnoreNullField() && (array == null || array.length == 0)) {
            return sisArrayClass;
        }

        try {
            Class<?> elementType = Reflection.getElementTypeForArray(sourceObject.getClass());
            if (Reflection.isPrimitiveType(elementType)) {
                addElements(sisArrayClass, array);
            } else {
                addChildClasses(level, sisArrayClass, array, elementType);
            }
            return sisArrayClass;
        } catch (ClassNotFoundException e) {
            logger.warn(e.getMessage());
            return sisArrayClass;
        }
    }

    void addChildClasses(int level, SisArrayClass sisArrayClass, Object[] array, Class<?> elementType) {
        if (array == null || array.length == 0) {
            sisArrayClass.addChildClass(parseClass(Reflection.getFieldValue(elementType, ""), null, level + 1));
        } else {
            for (Object element : array) {
                sisArrayClass.addChildClass(parseClass(element, null, level + 1));
            }
        }
    }

    void addElements(SisArrayClass sisArrayClass, Object[] array) {
        if (array == null || array.length == 0) {
            sisArrayClass.addBasicElement(new BasicElement(""));
        } else {
            for (Object element : array) {
                sisArrayClass.addBasicElement(new BasicElement(element.toString()));
            }
        }
    }

    <T> SisClass createListClass(T sourceObject, String fieldName, int level) {
        SisListClass sisListClass = new SisListClass(fieldName);
        sisListClass.setCurrentLevel(level);

        ArrayList list = (ArrayList) sourceObject;
        if (list == null || list.size() == 0) {
            return sisListClass;
        }

        Object firstElement = list.get(0);
        if (Reflection.isPrimitiveType(firstElement.getClass())) {
            for (Object element : list) {
                sisListClass.addBasicElement(new BasicElement(element.toString()));
            }
        } else {
            for (Object element : list) {
                sisListClass.addChildClass(parseClass(element, null, level + 1));
            }
        }

        return sisListClass;
    }

    <T> SisNormalClass createNormalClass(T sourceObject, String fieldName, int level) {
        SisNormalClass sisNormalClass = new SisNormalClass(fieldName);
        sisNormalClass.setCurrentLevel(level);

        Field[] declaredFields = sourceObject.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            setOrIgnoreFieldValue(sourceObject, sisNormalClass, declaredField, level);
        }
        return sisNormalClass;
    }

    <T> void setOrIgnoreFieldValue(T sourceObject, SisNormalClass sisNormalClass, Field declaredField, int level) {
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
                sisNormalClass.addChildClass(parseClass(fieldValue, declaredField.getName(), level + 1));
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

    <T> boolean isList(T sourceObject) {
        Class<?> sourceClass = sourceObject.getClass();
        return sourceClass.getSuperclass() != null &&
                (sourceClass.getSuperclass().getSimpleName().equals("ArrayList") ||
                        sourceClass.getSuperclass().getSimpleName().equals("List") ||
                        sourceClass.getSimpleName().equals("ArrayList"));
    }

    <T> boolean isArray(T sourceObject) {
        return sourceObject.getClass().isArray();
    }

    private SisClass logAndRethrowException(Exception ex) {
        logger.error(ex.getMessage());
        throw new FailedDeserializationException(ex);
    }

    public boolean isIgnoreNullField() {
        return ignoreNullField;
    }
}