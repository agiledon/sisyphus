package com.github.agiledon.sisyphus.composer;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.InputStream;

import static com.github.agiledon.sisyphus.util.ResourceFilePath.compensatePath;

public class YamlComposer extends AbstractComposer {
    @Override
    public <T> T deserialize(Class<T> tClass) {
        Yaml yaml = new Yaml();
        yaml.setBeanAccess(BeanAccess.FIELD);
        InputStream resourceStream = YamlComposer.class.getResourceAsStream(compensatePath(resourceName));
        return yaml.loadAs(resourceStream, tClass);
    }
}
