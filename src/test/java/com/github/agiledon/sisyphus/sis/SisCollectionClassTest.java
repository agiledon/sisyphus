package com.github.agiledon.sisyphus.sis;

import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SisCollectionClassTest {
    @Test
    public void should_generate_string_for_collection_indicator_without_field_name() {
        SisCollectionClass collectionClass = new SisCollectionClass();
        assertThat(collectionClass.toString(), is(
                "[\n" +
                "]"));
    }

    @Test
    public void should_generate_string_for_collection_indicator_with_field_name() {
        SisCollectionClass collectionClass = new SisCollectionClass("mainClass");
        assertThat(collectionClass.toString(), is(
                "mainClass = [\n" +
                "]"));
    }

    @Test
    public void should_generate_string_for_collection_with_basic_element() {
        SisCollectionClass collectionClass = new SisCollectionClass();
        collectionClass.addBasicElements(newArrayList(
                new BasicElement("value1"),
                new BasicElement("value2")
        ));

        assertThat(collectionClass.toString(), is(
                "[\n" +
                "    value1,value2\n" +
                "]"));
    }

    @Test
    public void should_generate_string_for_collection_with_class_element() {
        SisCollectionClass collectionClass = new SisCollectionClass();
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
                "]"));
    }
}
