package com.github.agiledon.sisyphus.asn;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class AsnClass {
    private String fieldName;
    private List<BasicField> basicFields;
    private List<AsnClass> childClassProperties;
    private AsnClass parentAsnClass;

    public AsnClass() {
        basicFields = newArrayList();
        childClassProperties = newArrayList();
    }

    public AsnClass(String fieldName) {
        this();
        this.fieldName = fieldName;
    }

    public <T> T instantiate(Class<T> aParentClass) {
        T parentObject;

        try {
            parentObject = aParentClass.newInstance();
            for (BasicField basicField : getBasicFields()) {
                basicField.setField(parentObject, aParentClass);
            }
            for (AsnClass asnClass : getChildClasses()) {
                asnClass.setField(parentObject, aParentClass);
            }

        } catch (Throwable t) {
            throw new FailedDeserializationException(t);
        }

        return parentObject;
    }

    protected abstract void setField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException;

    protected List<BasicField> getBasicFields() {
        return basicFields;
    }

    public void addBasicField(BasicField basicField) {
        basicFields.add(basicField);
    }

    public void addChildClass(AsnClass childAsnClass) {
        this.childClassProperties.add(childAsnClass);
        childAsnClass.parentAsnClass = this;
    }

    protected List<AsnClass> getChildClasses() {
        return childClassProperties;
    }

    public AsnClass getParentAsnClass() {
        return parentAsnClass;
    }

    protected String getFieldName() {
        return fieldName;
    }

    public abstract boolean isVector();
}
