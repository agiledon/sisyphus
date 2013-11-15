package com.github.agiledon.sisyphus.sis;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.agiledon.sisyphus.util.ResourceLoader.loadResource;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SyntaxParserTest {
    private SyntaxParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new SyntaxParser();
    }

    @Test
    public void should_parse_completed_segment_for_one_class_with_nested_class() {
        SisClass sisClass = parser.parseClassFromResource(loadResource("invoice.sis"));
        assertThat(sisClass.getBasicFields().size(), is(5));
        assertThat(sisClass.getBasicFields().get(0), is(new BasicField("invoice", "34843")));

        List<SisClass> childClassProperties = sisClass.getChildClasses();
        assertThat(childClassProperties.size(), is(3));
        assertThat(childClassProperties.get(0).getFieldName(), is("billTo"));


        List<SisClass> grandsonProperties = childClassProperties.get(0).getChildClasses();
        assertThat(grandsonProperties.size(), is(1));
        assertThat(grandsonProperties.get(0).getFieldName(), is("address"));
        assertThat(grandsonProperties.get(0).getBasicFields().size(), is(4));
    }
}
