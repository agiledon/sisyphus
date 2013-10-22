package com.github.agiledon.sisyphus.asn.rule;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MainClassRuleTest {
    public static final String SEQUENCE_DATA = "{ -- SEQUENCE --";

    @Test
    public void should_be_main_class_if_start_with_left_brace() {
        assertThat(new MainClassRule().match(SEQUENCE_DATA), is(true));
    }

}
