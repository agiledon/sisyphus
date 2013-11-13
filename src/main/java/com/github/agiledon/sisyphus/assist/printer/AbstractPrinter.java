package com.github.agiledon.sisyphus.assist.printer;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static com.github.agiledon.sisyphus.util.ResourceFilePath.compensatePath;
import static com.github.agiledon.sisyphus.util.ResourceFilePath.getAbsolutePath;

@SuppressWarnings("unchecked")
public abstract class AbstractPrinter<T> implements Printer {
    private static Logger logger = LoggerFactory.getLogger(AbstractPrinter.class);

    private T sourceObject;

    protected AbstractPrinter(T sourceObject) {
        this.sourceObject = sourceObject;
    }

    @Override
    public T sourceObject() {
        return sourceObject;
    }

    @Override
    public void print(String dataFileName) {
        File file = new File(getAbsolutePath(compensatePath(dataFileName)));
        try {
            Files.write(serialize(sourceObject()), file, Charsets.UTF_8);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected abstract String serialize(T sourceObject);
}
