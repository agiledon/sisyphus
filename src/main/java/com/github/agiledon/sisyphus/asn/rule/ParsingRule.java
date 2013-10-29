package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;
import com.github.agiledon.sisyphus.exception.FailedDeserializationException;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class ParsingRule {
    private static List<ParsingRule> rules = newArrayList(
            new EndingIndicatorRule(),
            new CollectionClassRule(),
            new SequenceClassRule(),
            new ChoiceClassRule(),
            new BasicElementRule());
    private static List<AsnClassRule> generators = newArrayList(
            new CollectionClassRule(),
            new SequenceClassRule(),
            new ChoiceClassRule());

    public static AsnClass asnClassTree(AsnClass currentClass, String line) {
        for (ParsingRule rule : rules) {
            if (rule.match(line)) {
                return rule.asnClassNode(currentClass, line);
            }
        }

        return new BasicFieldRule().asnClassNode(currentClass, line);
    }

    public static AsnClass createRootClass(String firstLine) {
        for (AsnClassRule generator : generators) {
            if (generator.match(firstLine)) {
                return generator.generate();
            }
        }
        throw new FailedDeserializationException("Wrong Data file");
    }

    public abstract boolean match(String line);
    public abstract AsnClass asnClassNode(AsnClass currentClass, String line);
}
