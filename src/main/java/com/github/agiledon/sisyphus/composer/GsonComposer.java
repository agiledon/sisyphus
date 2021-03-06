package com.github.agiledon.sisyphus.composer;

import com.google.gson.Gson;

public class GsonComposer extends AbstractComposer {
    @Override
    public <T> T deserialize(Class<T> tClass, String resource) {
        Gson gson = new Gson();
        return gson.fromJson(resource, tClass);
    }
}
