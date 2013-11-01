package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class SisClass {
    protected final Logger logger = LoggerFactory.getLogger(SisClass.class);
    private String fieldName;
    private List<BasicField> basicFields;
    private List<SisClass> childClasses;
    private SisClass parentClass;
    private List<BasicElement> basicElements;

    public SisClass() {
        basicFields = newArrayList();
        childClasses = newArrayList();
        basicElements = newArrayList();
    }

    public SisClass(String fieldName) {
        this();
        this.fieldName = fieldName;
    }

    public final <T> T instantiate(Class<T> currentClass) {
        T currentObject;
        try {
            currentObject = newInstance(currentClass);
        } catch (Throwable t) {
            logger.error("Failed to de-serialize and the error message is {}", t.getMessage());
            throw new FailedDeserializationException(t);
        }
        return currentObject;
    }

    protected  <T> T newInstance(Class<T> currentClass) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        T currentObject;
        currentObject = currentClass.newInstance();
        setBasicFields(currentObject, currentClass);
        setClassFields(currentObject, currentClass);
        return currentObject;
    }

    protected final <T> void setBasicFields(T currentObject, Class<T> currentClass) throws IllegalAccessException {
        for (BasicField basicField : getBasicFields()) {
            basicField.setField(currentObject, currentClass);
        }

    }

    protected final <T> void setClassFields(T currentObject, Class<T> currentClass) throws NoSuchFieldException, IllegalAccessException {
        for (SisClass childSisClass : getChildClasses()) {
            Field childField = currentClass.getDeclaredField(childSisClass.getFieldName());
            childField.set(currentObject, childSisClass.instantiate(childField.getType()));
        }
    }

    protected List<BasicField> getBasicFields() {
        return basicFields;
    }

    public void addBasicField(BasicField basicField) {
        basicFields.add(basicField);
    }

    public void addChildClass(SisClass childClass) {
        this.childClasses.add(childClass);
        childClass.parentClass = this;
    }

    protected List<SisClass> getChildClasses() {
        return childClasses;
    }

    public SisClass getParentClass() {
        return parentClass;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void addBasicElements(List<BasicElement> basicElements) {
        this.basicElements.addAll(basicElements);
    }

    public List<BasicElement> getBasicElements() {
        return basicElements;
    }
}
