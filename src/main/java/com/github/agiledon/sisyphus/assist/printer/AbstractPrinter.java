package com.github.agiledon.sisyphus.assist.printer;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static com.github.agiledon.sisyphus.util.ResourceFilePath.compensatePath;
import static com.github.agiledon.sisyphus.util.ResourceFilePath.getAbsolutePath;

public abstract class AbstractPrinter implements Printer {
    protected static Logger logger = LoggerFactory.getLogger(AbstractPrinter.class);

    @Override
    public <T> void print(T sourceObject, String dataFileName) {
        File file = new File(String.format("%s.%s", dataFileName, getExtension()));
        try {
            Files.write(serialize(sourceObject), file, Charsets.UTF_8);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected abstract String getExtension();

    protected abstract <T> String serialize(T sourceObject);
}
