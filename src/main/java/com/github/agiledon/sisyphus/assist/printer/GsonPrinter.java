package com.github.agiledon.sisyphus.assist.printer;

import com.google.gson.Gson;

public class GsonPrinter extends AbstractPrinter {
    @Override
    protected String getExtension() {
        return "gson";
    }

    @Override
    protected <T> String serialize(T sourceObject) {
        Gson gson = new Gson();
        return gson.toJson(sourceObject);
    }
}
