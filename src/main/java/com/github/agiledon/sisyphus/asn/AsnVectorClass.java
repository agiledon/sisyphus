package com.github.agiledon.sisyphus.asn;

import com.github.agiledon.sisyphus.asn.util.BasicFields;
import com.github.agiledon.sisyphus.exception.ElementClassNotFoundException;
import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Vector;

import static com.github.agiledon.sisyphus.Fixture.from;

public class AsnVectorClass extends AsnClass {
    public static final String TYPE_MAPPING_FILE_NAME = "typeMapping.yaml";
    private static Logger logger = LoggerFactory.getLogger(AsnVectorClass.class);

    public AsnVectorClass() {
        super();
    }

    public AsnVectorClass(String fieldName) {
        super(fieldName);
    }

    @Override
    protected <T> T newInstance(Class<T> currentClass) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        T currentObject = currentClass.newInstance();
        addElement(currentObject);
        return currentObject;
    }

    @SuppressWarnings("unchecked")
    protected void addElement(Object currentObject) {
        Vector currentVector = (Vector) currentObject;
        String currentTypeName = currentObject.getClass().getName();

        Map<String, String> typeMapping = from(TYPE_MAPPING_FILE_NAME).to(Map.class);

        Class<?> elementClass;
        if (isConfigured(currentTypeName, typeMapping)) {
            elementClass = getElementClassOnConfiguration(currentTypeName, typeMapping);
        } else {
            elementClass = getElementClassOnConvention(currentTypeName);
        }
        addElements(currentVector, elementClass);
    }

    private void addElements(Vector currentVector, Class<?> elementClass) {
        addClassElements(currentVector, elementClass);
        addBasicElements(currentVector, elementClass);
    }

    @SuppressWarnings("unchecked")
    private void addClassElements(Vector currentVector, Class<?> elementClass) {
        for (AsnClass childAsnClass : getChildClasses()) {
            currentVector.addElement(childAsnClass.instantiate(elementClass));
        }
    }

    @SuppressWarnings("unchecked")
    private void addBasicElements(Vector currentVector, Class<?> elementClass) {
        for (BasicElement element : getBasicElements()) {
            try {
                Object filedValue = BasicFields.getFieldValue(elementClass, element.getValue());
                currentVector.addElement(filedValue);
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

    private boolean isConfigured(String currentTypeName, Map<String, String> typeMapping) {
        return typeMapping != null && typeMapping.containsKey(currentTypeName);
    }

    private void logAndRethrowException(String currentTypeName) {
        String errorMessage = String.format("Can not get the class type %s", currentTypeName);
        logger.error(errorMessage);
        throw new FailedDeserializationException(errorMessage);
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
}
