package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SequenceClassRuleTest {

    private SequenceClassRule sequenceClassRule;

    @Before
    public void setUp() throws Exception {
        sequenceClassRule = new SequenceClassRule();
    }

    @Test
    public void should_parse_class_property_given_equal_expression_with_class_name() {
        AsnClass asnClass = sequenceClassRule.createAsnClass("      foo = { -- SEQUENCE ");
        assertThat(asnClass.getFieldName(), is("foo"));
    }

    @Test
    public void should_be_sequence_class_if_line_contains_sequence_and_equal_character() {
        assertThat(sequenceClassRule.match("      foo = { -- SEQUENCE"), is(true));
    }
}
