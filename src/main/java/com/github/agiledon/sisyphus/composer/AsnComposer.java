package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.asn.AsnClass;
import com.github.agiledon.sisyphus.asn.SyntaxParser;

import java.util.List;

public class AsnComposer extends AbstractComposer{
    @Override
    protected <T> T deserialize(Class<T> currentClass, List<String> resource) {
        AsnClass currentAsnClass = new SyntaxParser().parseClass(getContent(resource));
        return currentAsnClass.instantiate(currentClass);
    }
}
