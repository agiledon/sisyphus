package com.github.agiledon.sisyphus.asn;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class AsnClass {
    protected final Logger logger = LoggerFactory.getLogger(AsnClass.class);
    private String fieldName;
    private List<BasicField> basicFields;
    private List<AsnClass> childClasses;
    private AsnClass parentClass;

    public AsnClass() {
        basicFields = newArrayList();
        childClasses = newArrayList();
    }

    public AsnClass(String fieldName) {
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

    protected final <T> void setBasicFields(T currentObject, Class<T> currentClass) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        for (BasicField basicField : getBasicFields()) {
            basicField.setField(currentObject, currentClass);
        }

    }

    protected final <T> void setClassFields(T currentObject, Class<T> currentClass) throws NoSuchFieldException, IllegalAccessException {
        for (AsnClass childAsnClass : getChildClasses()) {
            Field childField = currentClass.getDeclaredField(childAsnClass.getFieldName());
            childField.set(currentObject, childAsnClass.instantiate(childField.getType()));
        }
    }

    protected List<BasicField> getBasicFields() {
        return basicFields;
    }

    public void addBasicField(BasicField basicField) {
        basicFields.add(basicField);
    }

    public void addChildClass(AsnClass childClass) {
        this.childClasses.add(childClass);
        childClass.parentClass = this;
    }

    protected List<AsnClass> getChildClasses() {
        return childClasses;
    }

    public AsnClass getParentClass() {
        return parentClass;
    }

    public String getFieldName() {
        return fieldName;
    }
}
