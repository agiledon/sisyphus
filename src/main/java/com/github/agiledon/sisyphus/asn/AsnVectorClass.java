package com.github.agiledon.sisyphus.asn;

import com.github.agiledon.sisyphus.exception.ElementClassNotFoundException;

import java.util.Vector;

public class AsnVectorClass extends AsnClass {

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

    protected void addElement(Object currentObject) {
        Vector currentVector = (Vector) currentObject;
        String currentTypeName = currentObject.getClass().getName();

        if (currentTypeName.contains("StringList_T")) {
            for (BasicElement element : getBasicElements()) {
                currentVector.addElement(element.getValue());
            }
        } else {
            Class<?> elementClass = getElementClass(currentTypeName);
            for (AsnClass childAsnClass : getChildClasses()) {
                currentVector.addElement(childAsnClass.instantiate(elementClass));
            }
        }
    }

    private Class<?> getElementClass(String currentTypeName) {
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
