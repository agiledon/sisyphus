package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static com.github.agiledon.sisyphus.util.StringUtil.spaces;
import static com.google.common.collect.Lists.newArrayList;

public abstract class SisClass {
    protected static final int TAB_SPACES_COUNT = 4;
    protected final Logger logger = LoggerFactory.getLogger(SisClass.class);
    private String fieldName;
    private List<BasicField> basicFields;
    private List<SisClass> childClasses;
    private SisClass parentClass;
    private List<BasicElement> basicElements;
    private int currentLevel = 0;

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

    protected <T> T newInstance(Class<T> currentClass) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
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
            if (childSisClass instanceof SisListClass) {
                SisListClass collectionClass = (SisListClass) childSisClass;
                collectionClass.setElementClass(getListElementClass(childField));
            }
            childField.set(currentObject, childSisClass.instantiate(childField.getType()));
        }
    }

    private Class<?> getListElementClass(Field childField) {
        ParameterizedType integerListType = (ParameterizedType) childField.getGenericType();
        return (Class<?>) integerListType.getActualTypeArguments()[0];
    }

    protected List<BasicField> getBasicFields() {
        return basicFields;
    }

    public void addBasicField(BasicField basicField) {
        basicFields.add(basicField);
    }

    public void addChildClass(SisClass childClass) {
        this.childClasses.add(childClass);
        childClass.setCurrentLevel(this.getCurrentLevel() + 1);
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

    public void addBasicElement(BasicElement basicElement) {
        this.basicElements.add(basicElement);
    }

    public List<BasicElement> getBasicElements() {
        return basicElements;
    }

    protected void printLeftPadding(StringBuilder stringBuilder, boolean forChild) {
        int level = forChild ? 1 : 0;
        stringBuilder.append(spaces((getCurrentLevel() + level) * TAB_SPACES_COUNT));
    }

    protected void printChildClasses(StringBuilder stringBuilder) {
        List<SisClass> childClasses = getChildClasses();
        for (int i = 0; i < childClasses.size(); i++) {
            SisClass sisClass = childClasses.get(i);
            stringBuilder.append(sisClass.toString());
            if (i == childClasses.size() - 1) {
                stringBuilder.append("\n");
            }
        }

    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    protected abstract void printStartIndicator(StringBuilder stringBuilder);

    protected void printStart(StringBuilder stringBuilder) {
        printLeftPadding(stringBuilder, false);
        if (getFieldName() != null) {
            stringBuilder.append(getFieldName() + " = ");
        }
        printStartIndicator(stringBuilder);
    }

    protected abstract void printEndIndicator(StringBuilder stringBuilder);

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        printStart(stringBuilder);
        printBasicElements(stringBuilder);
        printBasicFields(stringBuilder);
        printChildClasses(stringBuilder);
        printEnd(stringBuilder);

        return stringBuilder.toString();
    }

    private void printEnd(StringBuilder builder) {
        printLeftPadding(builder, false);
        printEndIndicator(builder);
    }

    private void printBasicFields(StringBuilder builder) {
        List<BasicField> basicFields = getBasicFields();
        for (int i = 0; i < basicFields.size(); i++) {
            printLeftPadding(builder, true);
            builder.append(basicFields.get(i).toString());
            builder.append("\n");
        }
    }

    protected void printBasicElements(StringBuilder stringBuilder) {
        List<BasicElement> basicElements = getBasicElements();
        if (basicElements.size() == 0) {
            return;
        }
        printLeftPadding(stringBuilder, true);
        for (int i = 0; i < basicElements.size(); i++) {
            BasicElement basicElement = basicElements.get(i);
            stringBuilder.append(basicElement.getValue());
            if (i < basicElements.size() - 1) {
                stringBuilder.append(",");
            } else {
                stringBuilder.append("\n");
            }
        }
    }
}
