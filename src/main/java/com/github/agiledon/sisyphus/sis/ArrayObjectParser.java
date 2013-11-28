package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.sis.util.Reflection;

import static com.github.agiledon.sisyphus.sis.util.Reflection.getElementTypeForArray;

public class ArrayObjectParser extends ObjectParser {
    public ArrayObjectParser(boolean ignoreNullField) {
        super(ignoreNullField);
    }

    @Override
    public <T> SisClass parseClass(T sourceObject, String fieldName, int level) {
        SisArrayClass sisArrayClass = new SisArrayClass(fieldName);
        sisArrayClass.setCurrentLevel(level);

        Object[] array = (Object[]) sourceObject;
        if (isIgnoreNullField() && (array == null || array.length == 0)) {
            return sisArrayClass;
        }

        try {
            Class<?> elementType = getElementTypeForArray(sourceObject.getClass());
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

    private void addChildClasses(int level, SisArrayClass sisArrayClass, Object[] array, Class<?> elementType) {
        if (array == null || array.length == 0) {
            Object fieldValue = Reflection.getFieldValue(elementType, "");
            sisArrayClass.addChildClass(parseChildClass(fieldValue, null, level + 1));
        } else {
            for (Object element : array) {
                sisArrayClass.addChildClass(parseChildClass(element, null, level + 1));
            }
        }
    }

    private void addElements(SisArrayClass sisArrayClass, Object[] array) {
        if (array == null || array.length == 0) {
            sisArrayClass.addBasicElement(new BasicElement(""));
        } else {
            for (Object element : array) {
                sisArrayClass.addBasicElement(new BasicElement(element.toString()));
            }
        }
    }

}
