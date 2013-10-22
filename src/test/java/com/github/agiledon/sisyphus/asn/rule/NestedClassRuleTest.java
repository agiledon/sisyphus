package com.github.agiledon.sisyphus.asn.rule;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NestedClassRuleTest {
    public static final String NESTED_SEQUENCE_DATA = "      { -- SEQUENCE --";

    @Test
    public void should_be_nested_class_if_start_with_white_space_then_left_brace() {
        assertThat(new NestedClassRule().match(NESTED_SEQUENCE_DATA), is(true));
    }
}
