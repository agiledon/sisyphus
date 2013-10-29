package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;
import com.github.agiledon.sisyphus.asn.AsnCollectionClass;

public class CollectionClassRule extends AsnClassRule {
    public static final String COLLECTION_INDICATOR = "SEQUENCE OF";

    @Override
    protected AsnClass createAsnClass(String line) {
        if (isNestedClass(line)) {
            return new AsnCollectionClass();
        }
        return new AsnCollectionClass(getFieldName(line));
    }

    @Override
    public boolean match(String line) {
        return line.contains(COLLECTION_INDICATOR);
    }

    @Override
    public AsnClass generate() {
        return new AsnCollectionClass();
    }
}
