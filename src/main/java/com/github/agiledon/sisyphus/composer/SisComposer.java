package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.sis.SyntaxParser;

public class SisComposer extends AbstractComposer{
    @Override
    protected <T> T deserialize(Class<T> currentClass, String content) {
        SisClass currentSisClass = new SyntaxParser().parseClass(content);
        return currentSisClass.instantiate(currentClass);
    }
}
