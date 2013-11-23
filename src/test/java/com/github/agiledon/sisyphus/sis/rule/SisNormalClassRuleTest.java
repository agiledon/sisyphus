package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisClass;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SisNormalClassRuleTest {

    private SisNormalClassRule sisNormalClassRule;

    @Before
    public void setUp() throws Exception {
        sisNormalClassRule = new SisNormalClassRule();
    }

    @Test
    public void should_parse_class_property_given_equal_expression_with_class_name() {
        SisClass sisClass = sisNormalClassRule.createSisClass("      foo = { ");
        assertThat(sisClass.getFieldName(), is("foo"));
    }

    @Test
    public void should_be_sequence_class_if_line_contains_sequence_and_equal_character() {
        assertThat(sisNormalClassRule.match("      foo = { "), is(true));
    }
}
