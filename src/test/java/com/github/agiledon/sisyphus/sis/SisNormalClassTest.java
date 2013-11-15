package com.github.agiledon.sisyphus.sis;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SisNormalClassTest {

    private final BasicField basicField = new BasicField("fieldName1", "value");

    @Test
    public void should_generate_string_for_normal_class_without_field_name() {
        SisNormalClass normalClass = new SisNormalClass();
        normalClass.addBasicField(basicField);

        assertThat(normalClass.toString(), is(
                "{\n" +
                "    fieldName1 = value\n" +
                "}"));
    }

    @Test
    public void should_generate_string_for_normal_class_with_field_name() {
        SisNormalClass normalClass = new SisNormalClass("currentField");
        normalClass.addBasicField(basicField);

        assertThat(normalClass.toString(), is(
                "currentField = {\n" +
                "    fieldName1 = value\n" +
                "}"));
    }

    @Test
    public void should_generate_string_for_normal_class_with_inner_class() {
        SisNormalClass normalClass = new SisNormalClass("currentField");
        normalClass.addBasicField(basicField);

        SisNormalClass childNormalClass = new SisNormalClass("childField");
        childNormalClass.addBasicField(basicField);
        normalClass.addChildClass(childNormalClass);

        assertThat(normalClass.toString(), is(
                "currentField = {\n" +
                "    fieldName1 = value\n" +
                "    childField = {\n" +
                "        fieldName1 = value\n" +
                "    }\n" +
                "}"));
    }

    @Test
    public void should_generate_string_for_normal_class_with_collection_class() {
        SisNormalClass normalClass = new SisNormalClass("currentField");
        SisCollectionClass collectionClass = new SisCollectionClass("collectionField");

        normalClass.addBasicField(basicField);
        normalClass.addChildClass(collectionClass);

        SisNormalClass childClass = new SisNormalClass();
        childClass.addBasicField(new BasicField("fieldName1", "value1"));
        childClass.addBasicField(new BasicField("fieldName2", "value2"));
        collectionClass.addChildClass(childClass);

        assertThat(normalClass.toString(), is(
                "currentField = {\n" +
                "    fieldName1 = value\n" +
                "    collectionField = [\n" +
                "        {\n" +
                "            fieldName1 = value1\n" +
                "            fieldName2 = value2\n" +
                "        }\n" +
                "    ]\n" +
                "}"));
    }
}
