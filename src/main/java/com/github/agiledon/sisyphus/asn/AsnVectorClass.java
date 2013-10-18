package com.github.agiledon.sisyphus.asn;

import java.lang.reflect.Field;

public class AsnVectorClass extends AsnClass {

    public AsnVectorClass(String fieldName) {
        super(fieldName);
    }

    @Override
    protected void setField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = aClass.getDeclaredField(getFieldName());
        declaredField.set(mainObject, instantiate(declaredField.getType()));
    }

    @Override
    public boolean isVector() {
        return true;
    }
}
