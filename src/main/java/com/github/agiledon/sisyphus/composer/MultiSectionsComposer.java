package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.util.ResourceLoader;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class MultiSectionsComposer extends ComposerDecorator {

    public MultiSectionsComposer(AbstractComposer composer) {
        super(composer);
    }

    @Override
    public <T> T to(Class<T> tClass) {
        return composer.to(tClass);
    }

    public <T> List<T> toList(Class<T> tClass) {
        List<T> results = newArrayList();
        List<List<String>> resources = ResourceLoader.loadResources(resourceName);
        for (List<String> resource : resources) {
            T eachOne = deserialize(tClass, getContent(resource));
            results.add(eachOne);
        }
        return results;
    }

    @Override
    protected String getContent(List<String> resource) {
        return composer.getContent(resource);
    }
}
