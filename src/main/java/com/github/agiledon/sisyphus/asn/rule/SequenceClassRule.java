package com.github.agiledon.sisyphus.asn.rule;

public class SequenceClassRule extends SubClassRule{

    protected static boolean isSequence(String line) {
        return line.contains("SEQUENCE");
    }

    public boolean match(String line) {
        return isSequence(line) && line.contains("=");
    }
}
