package com.github.agiledon.sisyphus.sis;

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

    public void setElementClass(Class<?> elementClass) {
        this.elementClass = elementClass;
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
    protected void printEndIndicator(StringBuilder stringBuilder) {
        super.printEndIndicator(stringBuilder);
    }
}
