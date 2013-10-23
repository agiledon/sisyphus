package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnChoiceClass;
import com.github.agiledon.sisyphus.asn.AsnClass;

public class ChoiceClassRule extends SubClassRule {
    protected static boolean isChoice(String line) {
        return line.contains("CHOICE");
    }

    public boolean match(String line) {
        return isChoice(line) && line.contains("=");
    }

    protected AsnClass createAsnClass(String line) {
        return new AsnChoiceClass(getFieldName(line));
    }

}
