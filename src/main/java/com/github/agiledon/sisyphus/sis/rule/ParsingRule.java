package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.exception.FailedDeserializationException;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class ParsingRule {
    private static List<ParsingRule> rules = newArrayList(
            new EndingIndicatorRule(),
            new CollectionClassRule(),
            new NormalClassRule(),
            new ChoiceClassRule(),
            new BasicElementRule());
    private static List<SisClassRule> generators = newArrayList(
            new CollectionClassRule(),
            new NormalClassRule(),
            new ChoiceClassRule());

    public static SisClass asnClassTree(SisClass currentClass, String line) {
        for (ParsingRule rule : rules) {
            if (rule.match(line)) {
                return rule.asnClassNode(currentClass, line);
            }
        }

        return new BasicFieldRule().asnClassNode(currentClass, line);
    }

    public static SisClass createRootClass(String firstLine) {
        for (SisClassRule generator : generators) {
            if (generator.match(firstLine)) {
                return generator.generate();
            }
        }
        throw new FailedDeserializationException("Wrong Data file");
    }

    public abstract boolean match(String line);
    public abstract SisClass asnClassNode(SisClass currentClass, String line);
}
