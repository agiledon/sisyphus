package com.github.agiledon.sisyphus.composer;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

import static com.github.agiledon.sisyphus.util.ResourceFilePath.getAbsolutePath;

public class JsonComposer extends AbstractComposer {

    @Override
    protected <T> T deserialize(Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(getAbsolutePath(resourceName));
        try {
            return mapper.readValue(file, tClass);
        } catch (JsonMappingException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
