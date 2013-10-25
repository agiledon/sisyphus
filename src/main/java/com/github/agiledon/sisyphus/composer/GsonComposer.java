package com.github.agiledon.sisyphus.composer;

import com.google.gson.Gson;

import java.util.List;

public class GsonComposer extends AbstractComposer {
    @Override
    public <T> T deserialize(Class<T> tClass, List<String> resource) {
        Gson gson = new Gson();
        return gson.fromJson(getContent(resource), tClass);
    }
}
