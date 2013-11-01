package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.BasicElement;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BasicElementRuleTest {
    @Test
    public void should_be_basic_element_if_line_has_no_equator_and_brace() {
        assertTrue(new BasicElementRule().match("   value1, value2, value 3"));
    }

    @Test
    public void should_be_basic_element_with_basic_type_of_collection() {
        List<BasicElement> basicElements = new BasicElementRule().parseBasicElements("   value1, value2, value 3");
        assertThat(basicElements.size(), is(3));
        assertThat(basicElements.get(0).getValue(), is("value1"));
    }
}
