package com.github.agiledon.sisyphus.composer;

import java.util.List;

import static com.github.agiledon.sisyphus.util.ResourceLoader.loadResources;
import static com.google.common.collect.Lists.newArrayList;

public class MultiSectionsComposer extends ComposerDecorator {

    public MultiSectionsComposer(AbstractComposer composer) {
        super(composer);
    }

    @Override
    public <T> T to(Class<T> tClass) {
        return decoratedComposer.to(tClass);
    }

    public <T> List<T> toList(Class<T> tClass) {
        List<T> results = newArrayList();
        for (List<String> resource : loadResources(resourceName)) {
            T eachOne = deserialize(tClass, evaluate(resource));
            results.add(eachOne);
        }
        return results;
    }
}
