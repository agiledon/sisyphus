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
        AsnClass childAsnClass = getChildClasses().get(0);
        if (childAsnClass == null) {
            logger.warn("There is no child class");
            throw new FailedDeserializationException();
        }

        T currentObject = currentClass.newInstance();
        String choiceIdConstFieldName = childAsnClass.getFieldName().toUpperCase() + "_CID";
        Field choiceIdConstField = currentObject.getClass().getDeclaredField(choiceIdConstFieldName);
        int choiceIdValue = choiceIdConstField.getInt(currentObject);
        Field choiceIdField = currentObject.getClass().getDeclaredField("choiceId");
        choiceIdField.set(currentObject, choiceIdValue);

        setBasicFields(currentObject, currentClass);
        setClassFields(currentObject, currentClass);

        return currentObject;
    }

}
