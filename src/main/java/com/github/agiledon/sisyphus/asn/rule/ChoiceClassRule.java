package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnChoiceClass;
import com.github.agiledon.sisyphus.asn.AsnClass;

public class ChoiceClassRule extends AsnClassRule {

    public boolean match(String line) {
        return line.contains("CHOICE");
    }

    protected AsnClass createAsnClass(String line) {
        if (isNestedClass(line)) {
            return new AsnChoiceClass();
        }
        return new AsnChoiceClass(getFieldName(line));
    }

    @Override
    public AsnClass generate() {
        return new AsnChoiceClass();
    }
}
