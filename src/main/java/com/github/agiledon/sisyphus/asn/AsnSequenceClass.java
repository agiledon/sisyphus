package com.github.agiledon.sisyphus.asn;

import com.github.agiledon.sisyphus.exception.ElementClassNotFoundException;

import java.lang.reflect.Field;
import java.util.Vector;

public class AsnSequenceClass extends AsnClass {
    public AsnSequenceClass() {
    }

    public AsnSequenceClass(String fieldName) {
        super(fieldName);
    }

    @Override
    protected void setField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        if (getParentAsnClass().isVector()) {
            Vector mainObjectVector = (Vector)mainObject;
            Class<?> elementClass = getElementClass(mainObject);
            mainObjectVector.addElement(instantiate(elementClass));
        } else {
            Field declaredField = aClass.getDeclaredField(getFieldName());
            declaredField.set(mainObject, instantiate(declaredField.getType()));
        }
    }

    @Override
    public boolean isVector() {
        return false;
    }

    private Class<?> getElementClass(Object mainObject) {
        String elementType = mainObject.getClass().getName().replace("LIST", "");
        try {
            return Class.forName(elementType);
        } catch (ClassNotFoundException e) {
            throw new ElementClassNotFoundException();
        }
    }
}
