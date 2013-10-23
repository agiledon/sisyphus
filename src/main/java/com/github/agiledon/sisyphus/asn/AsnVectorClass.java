package com.github.agiledon.sisyphus.asn;

public class AsnVectorClass extends AsnSubClass {

    public AsnVectorClass(String fieldName) {
        super(fieldName);
    }

    @Override
    protected void setField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        setCurrentField(mainObject, aClass);
    }

    @Override
    public boolean isVector() {
        return true;
    }
}
