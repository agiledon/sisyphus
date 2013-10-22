package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.BasicField;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BasicFieldRuleTest {

    private BasicFieldRule basicFieldRule;

    @Before
    public void setUp() throws Exception {
        basicFieldRule = new BasicFieldRule();
    }

    @Test
    public void should_parse_basic_field_given_equal_expression() {
        BasicField field = basicFieldRule.parseBasicField("          foo = 256");
        assertThat(field, is(new BasicField("foo", "256")));
    }

    @Test
    public void should_parse_basic_field_given_equal_expression_end_with_comma() {
        BasicField field = basicFieldRule.parseBasicField("     ss = 1,");
        assertThat(field, is(new BasicField("ss", "1")));
    }

    @Test
    public void should_parse_basic_field_given_equal_expression_with_negative_number() {
        BasicField field = basicFieldRule.parseBasicField("     num = -2,");
        assertThat(field, is(new BasicField("num", "-2")));
    }

    @Test
    public void should_parse_basic_field_given_equal_expression_with_multi_values() {
        BasicField field = basicFieldRule.parseBasicField("     num = -2, 3,");
        assertThat(field, is(new BasicField("num", "-2, 3")));
    }

    @Test
    public void should_parse_basic_field_splitted_by_first_equal_operator() {
        BasicField field = basicFieldRule.parseBasicField("     num = eq={2}, fq={123}");
        assertThat(field, is(new BasicField("num", "eq={2}, fq={123}")));
    }
}
