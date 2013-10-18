package com.github.agiledon.sisyphus.asn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsnMainClass extends AsnClass {
    private final Logger logger = LoggerFactory.getLogger(AsnMainClass.class);
    public AsnMainClass() {
    }

    @Override
    protected void setField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        logger.info("Main class {} doesn't need set any class field", aClass.getSimpleName());
    }

    @Override
    public boolean isVector() {
        return false;
    }
}
