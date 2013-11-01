package com.github.agiledon.sisyphus.sis.rule;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChoiceClassRuleTest {
    @Test
    public void should_be_choice_class_if_line_contains_sequence_and_equal_character() {
        assertThat(new ChoiceClassRule().match("      foo = { -- CHOICE"), is(true));
    }
}
