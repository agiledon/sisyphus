package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.exception.FailedDeserializationException;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class ResourceParserRule {
    private static List<ResourceParserRule> rules = newArrayList(
            new EndingIndicatorRule(),
            new SisArrayClassRule(),
            new SisListClassRule(),
            new SisNormalClassRule(),
            new BasicElementRule());
    private static List<SisClassRule> generators = newArrayList(
            new SisArrayClassRule(),
            new SisListClassRule(),
            new SisNormalClassRule());

    public static SisClass sisClassTree(SisClass currentClass, String line) {
        for (ResourceParserRule rule : rules) {
            if (rule.match(line)) {
                return rule.sisClassNode(currentClass, line);
            }
        }

        return new BasicFieldRule().sisClassNode(currentClass, line);
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
    public abstract SisClass sisClassNode(SisClass currentClass, String line);
}
