package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.github.agiledon.sisyphus.sis.ObjectParser.parser;
import static com.github.agiledon.sisyphus.sis.rule.ResourceParserRule.sisClassTree;
import static com.github.agiledon.sisyphus.sis.rule.ResourceParserRule.createRootClass;
import static com.google.common.base.Preconditions.checkArgument;

public class SyntaxParser {
    private final Logger logger = LoggerFactory.getLogger(SyntaxParser.class);
    private static final String LINE_BREAK = "\n";
    private boolean ignoreNullField = true;

    public SyntaxParser() {
    }

    public SyntaxParser(boolean ignoreNullField) {
        this.ignoreNullField = ignoreNullField;
    }

    public SisClass parseClassFromResource(String resource) {
        SisClass rootClass;

        try {
            List<String> lines = splitLines(resource);
            checkArgument(lines != null && lines.size() >= 1, "data file is error");
            rootClass = createRootClass(lines.get(0));
            for (int i = 1; i < lines.size(); i++) {
                rootClass = sisClassTree(rootClass, lines.get(i));
            }
            return rootClass;
        } catch (IllegalArgumentException ex) {
            return logAndRethrowException(ex);
        } catch (Exception ex) {
            return logAndRethrowException(ex);
        }
    }

    public <T> SisClass parseClassFromObject(T sourceObject) {
        return parser(sourceObject, ignoreNullField).parseClass(sourceObject, null, 0);
    }

    private SisClass logAndRethrowException(Exception ex) {
        logger.error(ex.getMessage());
        throw new FailedDeserializationException(ex);
    }

    private List<String> splitLines(String content) {
        Iterable<String> lines = Splitter.on(LINE_BREAK)
                .omitEmptyStrings()
                .split(content);

        return Lists.newArrayList(lines);
    }

}
