package com.github.agiledon.sisyphus.sis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;

public class SisListClass extends SisCollectionClass {
    private Class<?> elementClass;

    public SisListClass() {
        super();
    }

    public SisListClass(String fieldName) {
        super(fieldName);
    }

    private void setElementClass(Class<?> elementClass) {
        this.elementClass = elementClass;
    }

    @Override
    protected <T> void setClassField(T currentObject, Class<T> currentClass, SisClass childSisClass) throws NoSuchFieldException, IllegalAccessException {
        Field childField = currentClass.getDeclaredField(this.getFieldName());
        this.setElementClass(getListElementClass(childField));
        childField.set(currentObject, this.instantiate(childField.getType()));
    }

    private Class<?> getListElementClass(Field childField) {
        ParameterizedType integerListType = (ParameterizedType) childField.getGenericType();
        return (Class<?>) integerListType.getActualTypeArguments()[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T newInstance(Class<T> currentClass) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        T currentObject = (T) newArrayList();
        addElements(currentObject);
        return currentObject;
    }

    @SuppressWarnings("unchecked")
    protected void addElements(Object currentObject) {
        Collection currentCollection = (Collection) currentObject;
        addElements(currentCollection, this.elementClass);
    }

    @Override
    protected void printStartIndicator(StringBuilder stringBuilder) {
        stringBuilder.append("<\n");
    }

    @Override
    protected void printEndIndicator(StringBuilder stringBuilder) {
        stringBuilder.append(">");
    }
}
