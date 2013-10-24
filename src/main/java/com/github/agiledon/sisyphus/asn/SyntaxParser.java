package com.github.agiledon.sisyphus.asn;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.agiledon.sisyphus.asn.rule.ParsingRule.generateAsnClassTree;

public class SyntaxParser {
    private final Logger logger = LoggerFactory.getLogger(SyntaxParser.class);
    public static final String LINE_BREAK = "\n";

    public AsnClass parseClass(String content) {
        AsnClass rootClass;
        String[] filteredLines = eachLine(content);

        try {
            Preconditions.checkArgument(
                    filteredLines != null && filteredLines.length >= 1,
                    "data file is error");

            rootClass = createRootClass(filteredLines[0]);
            for (String line : filteredLines) {
                rootClass = generateAsnClassTree(rootClass, line);
            }
            return rootClass;
        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
            throw new FailedDeserializationException(ex);
        }
    }

    private String[] eachLine(String content) {
        Iterable<String> lines = Splitter.on(LINE_BREAK)
                .omitEmptyStrings()
                .split(content);

        return filterSpaceLines(lines);
    }

    private String[] filterSpaceLines(Iterable<String> lines) {
        Iterable<String> filteredLines = Iterables.filter(lines, new Predicate<String>() {
            @Override
            public boolean apply(String line) {
                return !Strings.isNullOrEmpty(line.trim());
            }
        });
        return Iterables.toArray(filteredLines, String.class);
    }

    private AsnClass createRootClass(String firstLine) {
        if (firstLine.contains("SEQUENCE OF")) {
            return new AsnVectorClass();
        }
        if (firstLine.contains("CHOICE")) {
            return new AsnChoiceClass();
        }
        return new AsnMainClass();
    }

}
