package com.github.agiledon.sisyphus;

import com.github.agiledon.sisyphus.domain.yaml.Invoice;
import com.google.common.base.Objects;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class YamlFixtureTest {
    @Test
    public void should_compose_invoice_data_with_yaml_format() {
        Invoice invoice = Fixture.from("invoice.yaml").to(Invoice.class);
        MatcherAssert.assertThat(invoice, IsNot.not(CoreMatchers.nullValue()));
        MatcherAssert.assertThat(invoice.date, CoreMatchers.is("2001-01-23"));
        MatcherAssert.assertThat(invoice.products.length, CoreMatchers.is(2));
        MatcherAssert.assertThat(invoice.products[0].description, CoreMatchers.is("Basketball"));
        MatcherAssert.assertThat(invoice.billTo.given, CoreMatchers.is("Chris"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_compose_Type_data_with_yaml_format() {
        Map<String, List<String>> types = Fixture.from("type.yaml").to(Map.class);
        MatcherAssert.assertThat(types, IsNot.not(CoreMatchers.nullValue()));
        MatcherAssert.assertThat(types.size(), CoreMatchers.is(2));
        MatcherAssert.assertThat(types.get("Type1").get(0), CoreMatchers.is("Type1sub1"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_compose_Type_data_by_parsing_template_file() {
        Map<String, List<String>> types = Fixture.from("typeWithTemplate.yaml")
                .withTemplate("template/type.template")
                .to(Map.class);
        MatcherAssert.assertThat(types, IsNot.not(CoreMatchers.nullValue()));
        MatcherAssert.assertThat(types.size(), CoreMatchers.is(2));
        MatcherAssert.assertThat(types.get("Type1").get(0), CoreMatchers.is("Type1sub1"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_compose_Type_data_from_cache_with_yaml_format() {
        Map<String, List<String>> types = Fixture.from("type.yaml").to(Map.class);
        MatcherAssert.assertThat(types, IsNot.not(CoreMatchers.nullValue()));

        Map<String, List<String>> cachedTypes = Fixture.from("type.yaml").to(Map.class);
        MatcherAssert.assertThat(cachedTypes, IsNot.not(CoreMatchers.nullValue()));

        MatcherAssert.assertThat(Objects.equal(types, cachedTypes), CoreMatchers.is(true));
    }
}