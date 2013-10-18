package com.github.agiledon.sisyphus.asn;

import com.github.agiledon.sisyphus.exception.ElementClassNotFoundException;
import com.github.agiledon.sisyphus.exception.FailedDeserializationException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

import static com.google.common.collect.Lists.newArrayList;

public class AsnClass {
    private String fieldName;
    private boolean vector;
    private List<BasicField> basicFields;
    private List<AsnClass> childClassProperties;
    private AsnClass parentAsnClass;

    public AsnClass() {
        basicFields = newArrayList();
        childClassProperties = newArrayList();
    }

    public AsnClass(String fieldName) {
        this.fieldName = fieldName;
    }

    public AsnClass(String fieldName, boolean vector) {
        this();
        this.fieldName = fieldName;
        this.vector = vector;
    }

    public <T> T instantiate(Class<T> aClass) {
        T mainObject;

        try {
            mainObject = aClass.newInstance();
            for (BasicField basicField : getBasicFields()) {
                basicField.setField(mainObject, aClass);
            }
            for (AsnClass asnClass : getChildClassProperties()) {
                asnClass.setField(mainObject, aClass);
            }

        } catch (Throwable t) {
            throw new FailedDeserializationException(t);
        }

        return mainObject;
    }

    private void setField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        if (getParentAsnClass().isVector()) {
            Vector mainObjectVector = (Vector)mainObject;
            Class<?> elementClass = getElementClass(mainObject);
            mainObjectVector.addElement(instantiate(elementClass));
        } else {
            Field declaredField = aClass.getDeclaredField(getFieldName());
            declaredField.set(mainObject, instantiate(declaredField.getType()));
        }
    }

    private Class<?> getElementClass(Object mainObject) {
        String elementType = mainObject.getClass().getName().replace("LIST", "");
        try {
            return Class.forName(elementType);
        } catch (ClassNotFoundException e) {
            throw new ElementClassNotFoundException();
        }
    }

    protected List<BasicField> getBasicFields() {
        return basicFields;
    }

    public void addBasicField(BasicField basicField) {
        basicFields.add(basicField);
    }

    public void addChildClassProperty(AsnClass childAsnClass) {
        this.childClassProperties.add(childAsnClass);
        childAsnClass.parentAsnClass = this;
    }

    protected List<AsnClass> getChildClassProperties() {
        return childClassProperties;
    }

    public AsnClass getParentAsnClass() {
        return parentAsnClass;
    }

    protected String getFieldName() {
        return fieldName;
    }

    public boolean isVector() {
        return vector;
    }
}
