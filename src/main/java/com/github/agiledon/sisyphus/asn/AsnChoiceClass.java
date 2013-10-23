package com.github.agiledon.sisyphus.asn;

import java.lang.reflect.Field;

public class AsnChoiceClass extends AsnSubClass {
    public AsnChoiceClass(String fieldName) {
        super(fieldName);
    }

    @Override
    protected void setField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        if (getParentAsnClass() != null && getParentAsnClass().isVector()) {
            addElement(mainObject);
        } else {
            setCurrentField(mainObject, aClass);
        }
    }

    @Override
    protected void setCurrentField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        AsnClass asnClass = getChildClasses().get(0);
        if (asnClass != null && getFieldName() != null) {
            Field currentField = aClass.getDeclaredField(getFieldName());
            Object currentObj = instantiate(currentField.getType());
            setChoiceId(asnClass, currentObj);
            currentField.set(mainObject, currentObj);
        } else {
            logger.warn("There is no child field for choice class.");
        }
    }

    private void setChoiceId(AsnClass asnClass, Object currentObj) throws NoSuchFieldException, IllegalAccessException {
        Field choiceIdConstField = currentObj.getClass().getDeclaredField(buildChoiceIdConstFieldName(asnClass));
        int choiceId = choiceIdConstField.getInt(currentObj);
        Field choiceIdField = currentObj.getClass().getDeclaredField("choiceId");
        choiceIdField.set(currentObj, choiceId);
    }

    private String buildChoiceIdConstFieldName(AsnClass asnClass) {
        return asnClass.getFieldName().toUpperCase() + "_CID";
    }

    @Override
    public boolean isVector() {
        return false;
    }

}
