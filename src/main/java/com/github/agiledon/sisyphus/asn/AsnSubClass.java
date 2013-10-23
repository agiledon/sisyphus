package com.github.agiledon.sisyphus.asn;

import com.github.agiledon.sisyphus.exception.ElementClassNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Vector;

public abstract class AsnSubClass extends AsnClass{
    protected final Logger logger = LoggerFactory.getLogger(AsnSubClass.class);

    protected AsnSubClass() {
    }

    protected AsnSubClass(String fieldName) {
        super(fieldName);
    }

    protected Class<?> getElementClass(Object mainObject) {
        String elementType = mainObject.getClass().getName().replace("LIST", "");
        try {
            return Class.forName(elementType);
        } catch (ClassNotFoundException e) {
            logger.error("Class {} is not found. The cause is {}", elementType, e.getMessage());
            throw new ElementClassNotFoundException(e);
        }
    }

    protected void setCurrentField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = aClass.getDeclaredField(getFieldName());
        declaredField.set(mainObject, instantiate(declaredField.getType()));
    }

    protected void addElement(Object mainObject) {
        Vector mainObjectVector = (Vector)mainObject;
        Class<?> elementClass = getElementClass(mainObject);
        mainObjectVector.addElement(instantiate(elementClass));
    }
}
