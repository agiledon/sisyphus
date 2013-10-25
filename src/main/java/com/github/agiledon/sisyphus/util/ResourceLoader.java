package com.github.agiledon.sisyphus.util;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.github.agiledon.sisyphus.util.ResourceFilePath.compensatePath;
import static com.github.agiledon.sisyphus.util.ResourceFilePath.getAbsolutePath;
import static com.google.common.collect.Lists.newArrayList;

public class ResourceLoader {
    private static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);
    public static final String COMMENT_INDICATOR = "#";

    public static String loadResource(String resourceName) {
        List<String> resource = loadTextLines(resourceName);
        return Joiner.on("\n").join(resource);
    }

    public static List<String> loadTextLines(String textFileName) {
        try {
            File file = new File(getAbsolutePath(compensatePath(textFileName)));
            List<String> strings = Files.readLines(file, Charsets.UTF_8);
            return filterInvalidLines(strings);
        } catch (IOException e) {
            return logAndReturnEmptyList(textFileName);
        }catch (Exception e) {
            return logAndReturnEmptyList(textFileName);
        }
    }

    private static List<String> logAndReturnEmptyList(String textFileName) {
        logger.warn("Failed to read file {}.", textFileName);
        return newArrayList();
    }

    private static List<String> filterInvalidLines(Iterable<String> lines) {
        Iterable<String> filteredLines = Iterables.filter(lines, new Predicate<String>() {
            @Override
            public boolean apply(String line) {
                return notSpaceLine(line) && notComment(line);
            }
        });
        return Lists.newArrayList(filteredLines);
    }

    private static boolean notComment(String line) {
        return !line.trim().startsWith(COMMENT_INDICATOR);
    }

    private static boolean notSpaceLine(String line) {
        return !Strings.isNullOrEmpty(line.trim());
    }


}
