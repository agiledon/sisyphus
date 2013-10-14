package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.asn.ClassProperty;
import com.github.agiledon.sisyphus.asn.SyntaxParser;
import com.google.common.base.Joiner;

import java.util.List;

import static com.github.agiledon.sisyphus.util.ResourceLoader.loadResource;

public class AsnComposer extends AbstractComposer{
    @Override
    protected <T> T deserialize(Class<T> tClass) {
        ClassProperty classProperty = new SyntaxParser().parseClass(getContent());
        return classProperty.instantiate(tClass);
    }

}
