package com.github.agiledon.sisyphus.util;

import java.util.List;

public final class ListUtil {
    private ListUtil() {}
    public static <E> List<List<E>> partition(List<E> target, Predicates<E> predicate) {
        List<List<E>> partitions = com.google.common.collect.Lists.newArrayList();
        List<E> elementList = com.google.common.collect.Lists.newArrayList();
        for (E element : target) {
            if (predicate.apply(element)) {
                partitions.add(elementList);
                elementList = com.google.common.collect.Lists.newArrayList();
            } else {
                elementList.add(element);
            }
        }
        if (elementList.size() > 0) {
            partitions.add(elementList);
        }
        return partitions;
    }
}