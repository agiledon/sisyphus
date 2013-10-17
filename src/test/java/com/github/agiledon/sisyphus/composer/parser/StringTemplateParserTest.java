package com.github.agiledon.sisyphus.composer.parser;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringTemplateParserTest {
    @Test
    public void should_parse_template_file() {
        StringTemplateParser templateParser = new StringTemplateParser("template/testST.txt");
        List<String> variables = newArrayList("name = Sisyphus", "product = StringTemplate");
        String result = templateParser.evaluate(variables);

        assertThat(result, is("Hello Sisyphus\n" +
                "StringTemplate can work."));

    }
}
