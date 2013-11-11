package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.sis.util.BasicFields;
import com.github.agiledon.sisyphus.exception.ElementClassNotFoundException;
import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import static com.github.agiledon.sisyphus.Fixture.from;

public class SisCollectionClass extends SisClass {
    public static final String TYPE_MAPPING_FILE_NAME = "typeMapping.yaml";
    private static Logger logger = LoggerFactory.getLogger(SisCollectionClass.class);

    public SisCollectionClass() {
        super();
    }

    public SisCollectionClass(String fieldName) {
        super(fieldName);
    }

    @Override
    protected <T> T newInstance(Class<T> currentClass) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        if (isArray(currentClass)) {
            return newArrayInstance(currentClass);
        }

        T currentObject = currentClass.newInstance();
        addElement(currentObject);
        return currentObject;
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

    private <T> String getElementTypeForArray(Class<T> currentClass) {
        String canonicalName = currentClass.getCanonicalName();
        return canonicalName.substring(0, canonicalName.length() - 2);
    }

    private <T> boolean isArray(Class<T> currentClass) {
        return currentClass.getSimpleName().endsWith("[]");
    }

    @SuppressWarnings("unchecked")
    protected void addElement(Object currentObject) {
        Collection currentCollection = (Collection) currentObject;
        String currentTypeName = currentObject.getClass().getName();

        Map<String, String> typeMapping = from(TYPE_MAPPING_FILE_NAME).to(Map.class);

        Class<?> elementClass;
        if (isConfigured(currentTypeName, typeMapping)) {
            elementClass = getElementClassOnConfiguration(currentTypeName, typeMapping);
        } else {
            elementClass = getElementClassOnConvention(currentTypeName);
        }
        addElements(currentCollection, elementClass);
    }

    private void addElements(Collection currentCollection, Class<?> elementClass) {
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
                Object filedValue = BasicFields.getFieldValue(elementClass, element.getValue());
                currentCollection.add(filedValue);
            } catch (Exception e) {
                logAndRethrowException(elementClass.getClass().getName());
            }
        }
    }

    private Class<?> getElementClassOnConfiguration(String currentTypeName, Map<String, String> typeMapping) {
        try {
            return Class.forName(typeMapping.get(currentTypeName));
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            throw new FailedDeserializationException(e);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new FailedDeserializationException(e);
        }
    }

    private Class<?> getElementClassOnConvention(String currentTypeName) {
        //todo
        String elementType = currentTypeName.replaceFirst("(?i)List", "");
        try {
            return Class.forName(elementType);
        } catch (ClassNotFoundException e) {
            logger.error("Class {} is not found. The cause is {}", elementType, e.getMessage());
            throw new ElementClassNotFoundException(e);
        }
    }

    private boolean isConfigured(String currentTypeName, Map<String, String> typeMapping) {
        return typeMapping != null && typeMapping.containsKey(currentTypeName);
    }

    private void logAndRethrowException(String currentTypeName) {
        String errorMessage = String.format("Can not get the class type %s", currentTypeName);
        logger.error(errorMessage);
        throw new FailedDeserializationException(errorMessage);
    }
}
