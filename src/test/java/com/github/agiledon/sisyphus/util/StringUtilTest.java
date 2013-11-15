package com.github.agiledon.sisyphus.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringUtilTest {
    @Test
    public void should_generate_empty_string_if_count_equal_to_zero() {
        assertThat(StringUtil.spaces(0), is(""));
    }

    @Test
    public void should_generate_space_string_if_count_equal_to_zero() {
        assertThat(StringUtil.spaces(5), is("     "));
    }
}
