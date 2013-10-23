package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class ParsingRule implements AsnClassGenerator {
    private static List<ParsingRule> rules = newArrayList(
            new MainClassRule(),
            new EndingIndicatorRule(),
            new VectorClassRule(),
            new SequenceClassRule(),
            new ChoiceClassRule(),
            new NestedClassRule());

    public static AsnClass generateAsnClassTree(AsnClass currentClass, String line) {
        for (ParsingRule rule : rules) {
            if (rule.match(line)) {
                return rule.generate(currentClass, line);
            }
        }

        return new BasicFieldRule().generate(currentClass, line);
    }

    public abstract boolean match(String line);
}
