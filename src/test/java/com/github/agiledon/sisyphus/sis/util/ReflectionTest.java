package com.github.agiledon.sisyphus.sis.util;

import com.github.agiledon.sisyphus.sis.BasicField;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReflectionTest {
    @Test
    public void should_parse_String_value() {
        String result = (String) Reflection.getFieldValue(String.class, "hello sisyphus");
        assertThat(result, is("hello sisyphus"));

        result = (String) Reflection.getFieldValue(String.class, "");
        assertThat(result, is(""));
    }

    @Test
    public void should_parse_Integer_value() {
        Integer result = (Integer) Reflection.getFieldValue(Integer.class, "111");
        assertThat(result, is(111));

        result = (Integer) Reflection.getFieldValue(Integer.class, "");
        assertThat(result, is(0));
    }

    @Test
    public void should_parse_int_value() {
        Integer result = (Integer) Reflection.getFieldValue(int.class, "111");
        assertThat(result, is(111));

        result = (Integer) Reflection.getFieldValue(int.class, "");
        assertThat(result, is(0));
    }

    @Test
    public void should_parse_BigInteger_value() {
        BigInteger result = (BigInteger) Reflection.getFieldValue(BigInteger.class, "436777777777");
        assertThat(result, is(BigInteger.valueOf(436777777777L)));

        result = (BigInteger) Reflection.getFieldValue(BigInteger.class, "");
        assertThat(result, is(BigInteger.valueOf(0)));
    }

    @Test
    public void should_parse_float_value() {
        Float result = (Float) Reflection.getFieldValue(Float.class, "88.88");
        assertThat(result, is(88.88f));

        result = (Float) Reflection.getFieldValue(Float.class, "");
        assertThat(result, is(0f));
    }

    @Test
    public void should_parse_double_value() {
        Double result = (Double) Reflection.getFieldValue(Double.class, "88.88");
        assertThat(result, is(88.88d));

        result = (Double) Reflection.getFieldValue(Double.class, "");
        assertThat(result, is(0d));
    }

    @Test
    public void should_parse_byte_array_value() {
        byte[] result = (byte[]) Reflection.getFieldValue(byte[].class, "12345678");
        assertThat(result, is("12345678".getBytes()));

        result = (byte[]) Reflection.getFieldValue(byte[].class, "");
        assertThat(result, is("0".getBytes()));
    }

    @Test
    public void should_generate_string() {
        BasicField basicField = new BasicField("fieldName", "value");
        assertThat(basicField.toString(), is("fieldName = value"));
    }
}