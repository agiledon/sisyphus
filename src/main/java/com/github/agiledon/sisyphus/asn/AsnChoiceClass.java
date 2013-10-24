package com.github.agiledon.sisyphus.asn;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;

import java.lang.reflect.Field;

public class AsnChoiceClass extends AsnClass {
    public AsnChoiceClass(String fieldName) {
        super(fieldName);
    }

    public AsnChoiceClass() {
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
        AsnClass childAsnClass = getChildClasses().get(0);
        if (childAsnClass == null) {
            String errorMessage = String.format("No child class for %s", this);
            logger.error(errorMessage);
            throw new FailedDeserializationException(errorMessage);
        }

        setChoiceIdValue(childAsnClass, currentObject);
    }

    private <T> void setChoiceIdValue(AsnClass childAsnClass, T currentObject) throws NoSuchFieldException, IllegalAccessException {
        int choiceIdValue = getChoiceId(childAsnClass, currentObject);
        Field choiceIdField = currentObject.getClass().getDeclaredField("choiceId");
        choiceIdField.set(currentObject, choiceIdValue);
    }

    private <T> int getChoiceId(AsnClass childAsnClass, T currentObject) throws NoSuchFieldException, IllegalAccessException {
        String choiceIdConstFieldName = childAsnClass.getFieldName().toUpperCase() + "_CID";
        Field choiceIdConstField = currentObject.getClass().getDeclaredField(choiceIdConstFieldName);
        return choiceIdConstField.getInt(currentObject);
    }

}
