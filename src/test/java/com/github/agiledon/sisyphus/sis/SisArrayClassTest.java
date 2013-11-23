package com.github.agiledon.sisyphus.sis;

import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SisArrayClassTest {
    @Test
    public void should_generate_string_for_collection_indicator_without_field_name() {
        SisArrayClass collectionClass = new SisArrayClass();
        assertThat(collectionClass.toString(), is(
                "[\n" +
                "]\n"));
    }

    @Test
    public void should_generate_string_for_collection_indicator_with_field_name() {
        SisArrayClass collectionClass = new SisArrayClass("mainClass");
        assertThat(collectionClass.toString(), is(
                "mainClass = [\n" +
                "]\n"));
    }

    @Test
    public void should_generate_string_for_collection_with_basic_element() {
        SisArrayClass collectionClass = new SisArrayClass();
        collectionClass.addBasicElements(newArrayList(
                new BasicElement("value1"),
                new BasicElement("value2")
        ));

        assertThat(collectionClass.toString(), is(
                "[\n" +
                "    value1,value2\n" +
                "]\n"));
    }

    @Test
    public void should_generate_string_for_collection_with_class_element() {
        SisArrayClass collectionClass = new SisArrayClass();
        SisNormalClass childClass = new SisNormalClass();
        childClass.addBasicField(new BasicField("fieldName1", "value1"));
        childClass.addBasicField(new BasicField("fieldName2", "value2"));
        collectionClass.addChildClass(childClass);

        assertThat(collectionClass.toString(), is(
                "[\n" +
                "    {\n" +
                "        fieldName1 = value1\n" +
                "        fieldName2 = value2\n" +
                "    }\n" +
                "]\n"));
    }
}
