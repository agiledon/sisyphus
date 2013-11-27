package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.sis.BasicElement;
import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class BasicElementRule extends ResourceParserRule {
    public BasicElementRule() {
    }

    public boolean match(String line) {
        return CharMatcher.anyOf("{}=").matchesNoneOf(line);
    }

    @Override
    public SisClass sisClassNode(SisClass currentClass, String line) {
        currentClass.addBasicElements(parseBasicElements(line));
        return currentClass;
    }

    public List<BasicElement> parseBasicElements(String line) {
        List<String> elements = Splitter.on(",")
                .trimResults()
                .omitEmptyStrings()
                .splitToList(line);
        return Lists.transform(elements, new Function<String, BasicElement>() {
            @Override
            public BasicElement apply(String element) {
                return new BasicElement(element);
            }
        });
    }
}