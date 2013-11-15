package com.github.agiledon.sisyphus;

import com.github.agiledon.sisyphus.domain.yaml.Invoice;
import com.google.common.base.Objects;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class YamlFixtureTest {
    @Test
    public void should_compose_invoice_data_with_yaml_format() {
        Invoice invoice = Fixture.from("invoice.yaml").to(Invoice.class);
        assertThat(invoice, not(nullValue()));
        assertThat(invoice.date, is("2001-01-23"));
        assertThat(invoice.products.length, is(2));
        assertThat(invoice.products[0].description, is("Basketball"));
        assertThat(invoice.billTo.given, is("Chris"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_compose_Type_data_with_yaml_format() {
        Map<String, List<String>> types = Fixture.from("type.yaml").to(Map.class);
        assertThat(types, not(nullValue()));
        assertThat(types.size(), is(2));
        assertThat(types.get("Type1").get(0), is("Type1sub1"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_compose_Type_data_by_parsing_template_file() {
        Map<String, List<String>> types = Fixture.from("typeWithTemplate.yaml")
                .withTemplate("template/type.template")
                .to(Map.class);
        assertThat(types, not(nullValue()));
        assertThat(types.size(), is(2));
        assertThat(types.get("Type1").get(0), is("Type1sub1"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_compose_Type_data_from_cache_with_yaml_format() {
        Map<String, List<String>> types = Fixture.from("type.yaml").to(Map.class);
        assertThat(types, not(nullValue()));

        Map<String, List<String>> cachedTypes = Fixture.from("type.yaml").to(Map.class);
        assertThat(cachedTypes, not(nullValue()));

        assertThat(Objects.equal(types, cachedTypes), is(true));
    }
}