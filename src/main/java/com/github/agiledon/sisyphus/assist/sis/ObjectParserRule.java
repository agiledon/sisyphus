package com.github.agiledon.sisyphus.assist.sis;

import com.github.agiledon.sisyphus.exception.FailedSerializationException;
import com.github.agiledon.sisyphus.sis.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.github.agiledon.sisyphus.sis.util.BasicFields.isPrimitiveType;

public class ObjectParserRule<T> {
    private final T sourceObject;
    private final String fieldName;
    private final int level;

    public ObjectParserRule(T sourceObject, String fieldName, int level) {
        this.sourceObject = sourceObject;
        this.fieldName = fieldName;
        this.level = level;
    }

    public SisClass parseClass() {
        if (getSourceObject() == null) {
            throw new FailedSerializationException("The target is null");
        }
        if (this.isArray(getSourceObject())) {
            return this.createArrayClass(getSourceObject(), getFieldName(), getLevel());
        }
        if (this.isList(getSourceObject())) {
            return this.createListClass(getSourceObject(), getFieldName(), getLevel());
        }

        return this.createNormalClass(getSourceObject(), getFieldName(), getLevel());
    }

    private <T> SisClass createArrayClass(T sourceObject, String fieldName, int level) {
        SisArrayClass sisArrayClass = new SisArrayClass(fieldName);
        sisArrayClass.setCurrentLevel(level);

        Object[] array = (Object[]) sourceObject;
        if (array == null || array.length == 0) {
            return sisArrayClass;
        }

        Object firstElement = array[0];
        if (isPrimitiveType(firstElement.getClass().getSimpleName())) {
            for (Object element : array) {
                sisArrayClass.addBasicElement(new BasicElement(element.toString()));
            }
        } else {
            for (Object element : array) {
                sisArrayClass.addChildClass(new ObjectParserRule<Object>(element, null, level + 1).parseClass());
            }
        }

        return sisArrayClass;
    }

    private <T> SisClass createListClass(T sourceObject, String fieldName, int level) {
        SisListClass sisListClass = new SisListClass(fieldName);
        sisListClass.setCurrentLevel(level);

        ArrayList list = (ArrayList) sourceObject;
        if (list == null || list.size() == 0) {
            return sisListClass;
        }

        Object firstElement = list.get(0);
        if (isPrimitiveType(firstElement.getClass().getSimpleName())) {
            for (Object element : list) {
                sisListClass.addBasicElement(new BasicElement(element.toString()));
            }
        } else {
            for (Object element : list) {
                sisListClass.addChildClass(new ObjectParserRule<Object>(element, null, level + 1).parseClass());
            }
        }

        return sisListClass;
    }

    private <T> SisNormalClass createNormalClass(T sourceObject, String fieldName, int level) {
        SisNormalClass sisNormalClass = new SisNormalClass(fieldName);
        sisNormalClass.setCurrentLevel(level);

        Field[] declaredFields = sourceObject.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            try {
                Object fieldValue = declaredField.get(sourceObject);
                if (fieldValue == null) {
                    continue;
                }
                if (isPrimitiveType(fieldValue.getClass().getSimpleName())) {
                    sisNormalClass.addBasicField(new BasicField(declaredField.getName(), fieldValue.toString()));
                } else {
                    sisNormalClass.addChildClass(new ObjectParserRule<Object>(fieldValue, declaredField.getName(), level + 1).parseClass());
                }
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        return sisNormalClass;
    }

    private <T> boolean isList(T sourceObject) {
        Class<?> sourceClass = sourceObject.getClass();
        return sourceClass.getSuperclass().getSimpleName().equals("ArrayList") ||
                sourceClass.getSuperclass().getSimpleName().equals("List") ||
                sourceClass.getSimpleName().equals("ArrayList");
    }

    private <T> boolean isArray(T sourceObject) {
        return sourceObject.getClass().getSimpleName().endsWith("[]");
    }

    public T getSourceObject() {
        return sourceObject;
    }

    public String getFieldName() {
        return fieldName;
    }

    public int getLevel() {
        return level;
    }
}
