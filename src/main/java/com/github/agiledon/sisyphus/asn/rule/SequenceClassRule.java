package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;
import com.github.agiledon.sisyphus.asn.AsnSequenceClass;

public class SequenceClassRule extends SubClassRule{

    protected static boolean isSequence(String line) {
        return line.contains("SEQUENCE");
    }

    public boolean match(String line) {
        return isSequence(line) && line.contains("=");
    }

    protected AsnClass createAsnClass(String line) {
        return new AsnSequenceClass(getFieldName(line));
    }
}
