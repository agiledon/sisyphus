package com.github.agiledon.sisyphus.asn;

import com.github.agiledon.sisyphus.exception.ElementClassNotFoundException;
import com.github.agiledon.sisyphus.exception.FailedDeserializationException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

import static com.google.common.collect.Lists.newArrayList;

public class ClassProperty {
    private String fieldName;
    private List<BasicField> basicFields;
    private List<ClassProperty> childClassProperties;
    private ClassProperty parentClassProperty;

    public ClassProperty() {
        basicFields = newArrayList();
        childClassProperties = newArrayList();
    }

    public ClassProperty(String fieldName) {
        this();
        this.fieldName = fieldName;
    }

    public <T> T instantiate(Class<T> aClass) {
        T mainObject;

        try {
            mainObject = aClass.newInstance();
            for (BasicField basicField : getBasicFields()) {
                basicField.setField(mainObject, aClass);
            }
            for (ClassProperty property : getChildClassProperties()) {
                property.setField(mainObject, aClass);
            }

        } catch (Throwable t) {
            throw new FailedDeserializationException(t);
        }

        return mainObject;
    }

    private void setField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        if (isClassField()) {
            Field declaredField = aClass.getDeclaredField(getFieldName());
            declaredField.set(mainObject, instantiate(declaredField.getType()));
        } else {
            Vector mainObjectVector = (Vector)mainObject;
            Class<?> elementClass = getElementClass(mainObject);
            mainObjectVector.addElement(instantiate(elementClass));
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

    public void addChildClassProperty(ClassProperty childClassProperty) {
        this.childClassProperties.add(childClassProperty);
        childClassProperty.parentClassProperty = this;
    }

    protected List<ClassProperty> getChildClassProperties() {
        return childClassProperties;
    }

    public ClassProperty getParentClassProperty() {
        return parentClassProperty;
    }

    protected String getFieldName() {
        return fieldName;
    }

    protected boolean isClassField() {
        return fieldName != null;
    }
}
