package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.sis.SyntaxParser;

public class SisComposer extends AbstractComposer{
    @Override
    protected <T> T deserialize(Class<T> currentClass, String resource) {
        SisClass currentSisClass = new SyntaxParser().parseClassFromResource(resource);
        return currentSisClass.instantiate(currentClass);
    }
}
