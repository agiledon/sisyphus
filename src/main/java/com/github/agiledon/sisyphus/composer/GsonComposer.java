package com.github.agiledon.sisyphus.composer;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import static com.github.agiledon.sisyphus.util.ResourceFilePath.compensatePath;

public class GsonComposer extends AbstractComposer {
    @Override
    public <T> T deserialize(Class<T> tClass) {
        try {
            InputStream resourceStream = GsonComposer.class.getResourceAsStream(compensatePath(resourceName));
            Reader reader = new InputStreamReader(resourceStream, "UTF-8");
            Gson gson = new Gson();
            return gson.fromJson(reader, tClass);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
