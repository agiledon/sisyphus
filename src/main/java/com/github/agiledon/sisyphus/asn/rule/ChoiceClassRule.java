package com.github.agiledon.sisyphus.asn.rule;

public class ChoiceClassRule extends SubClassRule {
    protected static boolean isChoice(String line) {
        return line.contains("CHOICE");
    }

    public boolean match(String line) {
        return isChoice(line) && line.contains("=");
    }
}
