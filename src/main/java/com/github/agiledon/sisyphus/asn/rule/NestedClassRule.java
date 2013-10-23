package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;
import com.github.agiledon.sisyphus.asn.AsnSequenceClass;

public class NestedClassRule extends SubClassRule {
    public boolean match(String line) {
        return line.length() != line.trim().length() && line.trim().startsWith("{");
    }

    protected AsnClass createAsnClass(String line) {
        return new AsnSequenceClass();
    }
}
