package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.asn.ClassProperty;
import com.github.agiledon.sisyphus.asn.SyntaxParser;

public class AsnComposer extends AbstractComposer{
    @Override
    protected <T> T deserialize(Class<T> tClass) {
        ClassProperty classProperty = new SyntaxParser().parseClass(getContent());
        return classProperty.instantiate(tClass);
    }
}
