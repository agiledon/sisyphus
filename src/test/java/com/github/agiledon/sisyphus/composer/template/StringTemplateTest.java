package com.github.agiledon.sisyphus.composer.template;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringTemplateTest {
    @Test
    public void should_parse_template_file() {
        StringTemplate templateParser = new StringTemplate("template/testST.txt");
        List<String> variables = newArrayList("name = Sisyphus", "product = StringTemplate");
        String result = templateParser.evaluate(variables);

        assertThat(result, is("Hello Sisyphus\n" +
                "StringTemplate can work."));

    }
}
