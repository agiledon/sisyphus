package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;

public class MainClassRule extends ParsingRule{
    public boolean match(String line) {
        return line.startsWith("{");
    }

    @Override
    public AsnClass generate(AsnClass currentClass, String line) {
        return currentClass;
    }
}
