package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.asn.ClassProperty;
import com.github.agiledon.sisyphus.asn.SyntaxParser;

import java.util.List;

import static com.github.agiledon.sisyphus.util.ResourceLoader.loadResource;

public class AsnComposer extends AbstractComposer{
    @Override
    protected <T> T deserialize(Class<T> tClass) {
        List<String> content = loadResource(resourceName);
        ClassProperty classProperty = new SyntaxParser().parseClass(content);
        return classProperty.instantiate(tClass);
    }
}
