package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.asn.AsnClass;
import com.github.agiledon.sisyphus.asn.SyntaxParser;

public class AsnComposer extends AbstractComposer{
    @Override
    protected <T> T deserialize(Class<T> currentClass) {
        AsnClass currentAsnClass = new SyntaxParser().parseClass(getContent());
        return currentAsnClass.instantiate(currentClass);
    }
}
