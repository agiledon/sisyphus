package com.github.agiledon.sisyphus.sis;

import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SisListClassTest {
    @Test
    public void should_generate_string_for_collection_indicator_without_field_name() {
        SisListClass listClass = new SisListClass();
        assertThat(listClass.toString(), is(
                "<\n" +
                ">"));
    }

    @Test
    public void should_generate_string_for_collection_indicator_with_field_name() {
        SisListClass listClass = new SisListClass("mainClass");
        assertThat(listClass.toString(), is(
                "mainClass = <\n" +
                ">"));
    }

    @Test
    public void should_generate_string_for_collection_with_basic_element() {
        SisListClass listClass = new SisListClass();
        listClass.addBasicElements(newArrayList(
                new BasicElement("value1"),
                new BasicElement("value2")
        ));

        assertThat(listClass.toString(), is(
                "<\n" +
                "    value1,value2\n" +
                ">"));
    }

    @Test
    public void should_generate_string_for_collection_with_class_element() {
        SisListClass listClass = new SisListClass();

        SisNormalClass childClass = new SisNormalClass();
        childClass.addBasicField(new BasicField("fieldName1", "value1"));
        childClass.addBasicField(new BasicField("fieldName2", "value2"));
        listClass.addChildClass(childClass);

        assertThat(listClass.toString(), is(
                "<\n" +
                "    {\n" +
                "        fieldName1 = value1\n" +
                "        fieldName2 = value2\n" +
                "    }\n" +
                ">"));
    }
}
