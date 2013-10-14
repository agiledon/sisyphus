package com.github.agiledon.sisyphus.composer;

public class UnknownComposer extends AbstractComposer {
    @Override
    protected <T> T deserialize(Class<T> tClass) {
        return null;
    }
}
