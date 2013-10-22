package com.github.agiledon.sisyphus.asn;

import com.github.agiledon.sisyphus.asn.rule.*;
import com.google.common.base.*;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

import static com.github.agiledon.sisyphus.asn.rule.ParsingRule.generateAsnClassTree;
import static com.google.common.collect.Iterables.getLast;

public class SyntaxParser {
    public static final String LINE_BREAK = "\n";

    public AsnClass parseClass(String content) {
        AsnClass rootClass = new AsnMainClass();
        Iterable<String> lines = Splitter.on(LINE_BREAK)
                .omitEmptyStrings()
                .split(content);

        for (String line : filterSpaceLines(lines)) {
            rootClass = generateAsnClassTree(rootClass, line);
        }
        return rootClass;
    }

    private Iterable<String> filterSpaceLines(Iterable<String> lines) {
        return Iterables.filter(lines, new Predicate<String>() {
            @Override
            public boolean apply(String line) {
                return !Strings.isNullOrEmpty(line.trim());
            }
        });
    }

    public List<BasicElement> parseBasicElements(String line) {
        List<String> elements = Splitter.on(",")
                .trimResults()
                .splitToList(line);
        return Lists.transform(elements, new Function<String, BasicElement>() {
            @Override
            public BasicElement apply(String element) {
                return new BasicElement(element);
            }
        });
    }

    public boolean isBasicElement(String line) {
        return CharMatcher.anyOf("{}=").matchesNoneOf(line);
    }
}
