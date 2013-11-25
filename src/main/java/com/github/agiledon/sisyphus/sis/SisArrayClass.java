package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import com.github.agiledon.sisyphus.sis.util.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Collection;

import static com.github.agiledon.sisyphus.sis.util.Reflection.getElementTypeForArray;

public class SisArrayClass extends SisClass {
    private static Logger logger = LoggerFactory.getLogger(SisArrayClass.class);

    public SisArrayClass() {
        super();
    }

    public SisArrayClass(String fieldName) {
        super(fieldName);
    }

    @Override
    protected <T> T newInstance(Class<T> currentClass) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return newArrayInstance(currentClass);
    }

    @Override
    protected void printEndIndicator(StringBuilder stringBuilder) {
        stringBuilder.append("]");
    }

    @Override
    protected void printStartIndicator(StringBuilder stringBuilder) {
        stringBuilder.append("[");
    }

    @SuppressWarnings("unchecked")
    private <T> T newArrayInstance(Class<T> currentClass) {
        try {
            Class<?> elementClass = Class.forName(getElementTypeForArray(currentClass));
            int elementCounts = this.getChildClasses().size();
            Object[] array = (Object[]) Array.newInstance(elementClass, elementCounts);
            for (int i = 0; i < elementCounts; i++) {
                array[i] = getChildClasses().get(i).instantiate(elementClass);
            }

            return (T) array;
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            throw new FailedDeserializationException(e);
        }
    }

    protected void addElements(Collection currentCollection, Class<?> elementClass) {
        addClassElements(currentCollection, elementClass);
        addBasicElements(currentCollection, elementClass);
    }

    @SuppressWarnings("unchecked")
    private void addClassElements(Collection currentCollection, Class<?> elementClass) {
        for (SisClass childSisClass : getChildClasses()) {
            currentCollection.add(childSisClass.instantiate(elementClass));
        }
    }

    @SuppressWarnings("unchecked")
    private void addBasicElements(Collection currentCollection, Class<?> elementClass) {
        for (BasicElement element : getBasicElements()) {
            try {
                Object filedValue = Reflection.getFieldValue(elementClass, element.getValue());
                currentCollection.add(filedValue);
            } catch (Exception e) {
                logAndRethrowException(elementClass.getClass().getName());
            }
        }
    }

    private void logAndRethrowException(String currentTypeName) {
        String errorMessage = String.format("Can not get the class type %s", currentTypeName);
        logger.error(errorMessage);
        throw new FailedDeserializationException(errorMessage);
    }
}
