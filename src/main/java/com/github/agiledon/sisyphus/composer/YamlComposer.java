package com.github.agiledon.sisyphus.composer;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

public class YamlComposer extends AbstractComposer {
    @Override
    public <T> T deserialize(Class<T> tClass, String content) {
        Yaml yaml = new Yaml();
        yaml.setBeanAccess(BeanAccess.FIELD);
        return yaml.loadAs(content, tClass);
    }
}
