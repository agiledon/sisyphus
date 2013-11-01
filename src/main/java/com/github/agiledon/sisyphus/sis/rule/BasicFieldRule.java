package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.sis.BasicField;
import com.google.common.base.Splitter;

import static com.google.common.base.CharMatcher.anyOf;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.getLast;

public class BasicFieldRule extends ParsingRule {
    @Override
    public boolean match(String line) {
        return false;
    }

    @Override
    public SisClass asnClassNode(SisClass currentClass, String line) {
        currentClass.addBasicField(this.parseBasicField(line));
        return currentClass;
    }

    protected BasicField parseBasicField(String line) {
        Iterable<String> fieldInfo = Splitter.on("=")
                .trimResults(anyOf(" ,\r"))
                .limit(2)
                .split(line);

        return new BasicField(getFirst(fieldInfo, "ERROR_FIELD_NAME"), getLast(fieldInfo, ""));
    }
}
