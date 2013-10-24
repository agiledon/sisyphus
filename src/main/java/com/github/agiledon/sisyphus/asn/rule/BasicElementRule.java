package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;
import com.github.agiledon.sisyphus.asn.BasicElement;
import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class BasicElementRule extends ParsingRule{
    public BasicElementRule() {
    }

    public boolean match(String line) {
        return CharMatcher.anyOf("{}=").matchesNoneOf(line);
    }

    @Override
    public AsnClass asnClassNode(AsnClass currentClass, String line) {
        //todo
        return null;
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
}