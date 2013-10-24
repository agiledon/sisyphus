package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;
import com.github.agiledon.sisyphus.asn.AsnSequenceClass;

public class SequenceClassRule extends AsnClassRule {

    public boolean match(String line) {
        return line.contains("SEQUENCE");
    }

    protected AsnClass createAsnClass(String line) {
        if (isNestedClass(line)) {
            return new AsnSequenceClass();
        }
        return new AsnSequenceClass(getFieldName(line));
    }
}
