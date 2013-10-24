package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;
import com.github.agiledon.sisyphus.asn.AsnVectorClass;

public class VectorClassRule extends SubClassRule{
    public static final String VECTOR_INDICATOR = "SEQUENCE OF";

    @Override
    protected AsnClass createAsnClass(String line) {
        if (noFieldName(line)) {
            return new AsnVectorClass();
        }
        return new AsnVectorClass(getFieldName(line));
    }

    @Override
    public boolean match(String line) {
        return line.contains(VECTOR_INDICATOR);
    }
}
