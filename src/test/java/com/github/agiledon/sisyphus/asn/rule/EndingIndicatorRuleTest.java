package com.github.agiledon.sisyphus.asn.rule;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EndingIndicatorRuleTest {
    @Test
    public void should_be_end_of_class_if_line_contains_right_brace() {
        assertThat(new EndingIndicatorRule().match("     },"), is(true));
    }
}
