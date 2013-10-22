package com.github.agiledon.sisyphus.asn.rule;

public class NestedClassRule extends SubClassRule{
    public boolean match(String line) {
        return line.length() != line.trim().length() && line.trim().startsWith("{");
    }
}
