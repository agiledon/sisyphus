package com.github.agiledon.sisyphus.asn;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

import static com.github.agiledon.sisyphus.asn.rule.ParsingRule.generateAsnClassTree;

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
}
