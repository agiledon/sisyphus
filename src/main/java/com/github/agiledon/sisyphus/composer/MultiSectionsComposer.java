package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.util.ResourceLoader;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class MultiSectionsComposer extends AbstractComposer {
    private AbstractComposer composer;

    public MultiSectionsComposer(AbstractComposer composer) {
        this.composer = composer;
    }

    @Override
    protected <T> T deserialize(Class<T> tClass, List<String> resource) {
        return composer.deserialize(tClass, resource);
    }


    public <T> List<T> toList(Class<T> tClass) {
        List<T> results = newArrayList();
        List<List<String>> resources = ResourceLoader.loadResources(resourceName);
        for (List<String> resource : resources) {
            T eachOne = deserialize(tClass, resource);
            results.add(eachOne);
        }
        return results;
    }
}
