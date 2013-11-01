package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;

import java.lang.reflect.Field;

public class SisChoiceClass extends SisClass {
    public SisChoiceClass(String fieldName) {
        super(fieldName);
    }

    public SisChoiceClass() {
        super();
    }

    @Override
    protected <T> T newInstance(Class<T> currentClass) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        T currentObject = currentClass.newInstance();

        setChoiceIdField(currentObject);
        setBasicFields(currentObject, currentClass);
        setClassFields(currentObject, currentClass);

        return currentObject;
    }

    private <T> void setChoiceIdField(T currentObject) throws NoSuchFieldException, IllegalAccessException {
        SisClass childSisClass = getChildClasses().get(0);
        if (childSisClass == null) {
            String errorMessage = String.format("No child class for %s", this);
            logger.error(errorMessage);
            throw new FailedDeserializationException(errorMessage);
        }

        setChoiceIdValue(childSisClass, currentObject);
    }

    private <T> void setChoiceIdValue(SisClass childSisClass, T currentObject) throws NoSuchFieldException, IllegalAccessException {
        int choiceIdValue = getChoiceId(childSisClass, currentObject);
        Field choiceIdField = currentObject.getClass().getDeclaredField("choiceId");
        choiceIdField.set(currentObject, choiceIdValue);
    }

    private <T> int getChoiceId(SisClass childSisClass, T currentObject) throws NoSuchFieldException, IllegalAccessException {
        String choiceIdConstFieldName = childSisClass.getFieldName().toUpperCase() + "_CID";
        Field choiceIdConstField = currentObject.getClass().getDeclaredField(choiceIdConstFieldName);
        return choiceIdConstField.getInt(currentObject);
    }

}
