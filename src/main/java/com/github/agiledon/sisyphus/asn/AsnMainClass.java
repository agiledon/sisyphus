package com.github.agiledon.sisyphus.asn;

public class AsnMainClass extends AsnClass {
    public AsnMainClass() {
    }

    @Override
    protected void setField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        //do nothing
    }

    @Override
    public boolean isVector() {
        return false;
    }
}
