package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.sis.SisCollectionClass;

public class CollectionClassRule extends SisClassRule {
    public static final String COLLECTION_INDICATOR = "[";

    @Override
    protected SisClass createSisClass(String line) {
        if (isNestedClass(line)) {
            return new SisCollectionClass();
        }
        return new SisCollectionClass(getFieldName(line));
    }

    @Override
    public boolean match(String line) {
        return line.contains(COLLECTION_INDICATOR);
    }

    @Override
    public SisClass generate() {
        return new SisCollectionClass();
    }
}
