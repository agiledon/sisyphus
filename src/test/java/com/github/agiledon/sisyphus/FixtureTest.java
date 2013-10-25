package com.github.agiledon.sisyphus;

import com.github.agiledon.sisyphus.domain.json.StandardVariable;
import com.github.agiledon.sisyphus.domain.json.User;
import com.github.agiledon.sisyphus.domain.yaml.Invoice;
import com.google.common.base.Objects;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.github.agiledon.sisyphus.Fixture.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

public class FixtureTest {
    @Test
    public void should_compose_invoice_data_with_yaml_format() {
        Invoice invoice = from("invoice.yaml").to(Invoice.class);
        assertThat(invoice, not(nullValue()));
        assertThat(invoice.date, is("2001-01-23"));
        assertThat(invoice.products.length, is(2));
        assertThat(invoice.products[0].description, is("Basketball"));
        assertThat(invoice.billTo.given, is("Chris"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_compose_Type_data_with_yaml_format() {
        Map<String, List<String>> types = from("type.yaml").to(Map.class);
        assertThat(types, not(nullValue()));
        assertThat(types.size(), is(2));
        assertThat(types.get("Type1").get(0), is("Type1sub1"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_compose_Type_data_by_parsing_template_file() {
        Map<String, List<String>> types = from("typeWithTemplate.yaml")
                .withTemplate("template/type.template")
                .to(Map.class);
        assertThat(types, not(nullValue()));
        assertThat(types.size(), is(2));
        assertThat(types.get("Type1").get(0), is("Type1sub1"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_compose_Type_data_from_cache_with_yaml_format() {
        Map<String, List<String>> types = from("type.yaml").to(Map.class);
        assertThat(types, not(nullValue()));

        Map<String, List<String>> cachedTypes = from("type.yaml").to(Map.class);
        assertThat(cachedTypes, not(nullValue()));

        assertThat(Objects.equal(types, cachedTypes), is(true));
    }

    @Test
    public void should_compose_User_data_with_json_format() {
        User user = from("user.json").to(User.class);
        assertThat(user, not(nullValue()));
        assertThat(user.getName().getFirst(), is("Joe"));
    }

    @Test
    public void should_compose_User_data_with_gson_format() {
        User user = from("user.gson").to(User.class);
        assertThat(user, not(nullValue()));

        //gson can't support the case which fieldname is not match get method
        //gson can't convert "Rm9vYmFyIQ==" to byte[]
        assertThat(user.getName(), is(nullValue()));
    }

    @Test
    public void should_compose_User_data_from_cache_with_json_format() {
        User user = from("user.json").to(User.class);
        assertThat(user, not(nullValue()));

        User cachedUser = from("user.json").to(User.class);
        assertThat(cachedUser, not(nullValue()));

        assertThat(Objects.equal(user, cachedUser), is(true));
    }

    @Test
    public void should_compose_StandardVariables_data_with_json_format() {
        StandardVariable[] standardVariables = from("standardVariable.json").to(StandardVariable[].class);

        //Jackson can't support the class without default constructor if not using annotation
        assertThat(standardVariables, is(nullValue()));
    }

    @Test
    public void should_compose_StandardVariables_data_with_gson_format() {
        StandardVariable[] standardVariables = from("standardVariable.gson").to(StandardVariable[].class);
        assertThat(standardVariables, not(nullValue()));
        assertThat(standardVariables[0].result, is("0"));
        assertThat(standardVariables[1].result, is("1"));
        assertThat(standardVariables[2].result, is("2"));
    }

    @Test
    public void should_compose_StandardVariables_data_by_parsing_template_file() {
        StandardVariable[] standardVariables = from("standardVariableWithTemplate.gson")
                .withTemplate("template/standardVariable.template")
                .to(StandardVariable[].class);
        assertThat(standardVariables, not(nullValue()));
        assertThat(standardVariables[0].result, is("0"));
        assertThat(standardVariables[1].result, is("1"));
        assertThat(standardVariables[2].result, is("2"));
    }

    @Test
    public void should_compose_User_data_from_cache_with_gson_format() {
        StandardVariable[] standardVariables = from("standardVariable.gson").to(StandardVariable[].class);
        assertThat(standardVariables, not(nullValue()));

        StandardVariable[] cachedStandardVariables = from("standardVariable.gson").to(StandardVariable[].class);
        assertThat(cachedStandardVariables, not(nullValue()));

        assertThat(Objects.equal(standardVariables, cachedStandardVariables), is(true));
    }

    @Test
    public void should_get_null_given_unknown_file() {
        Object obj = from("data.unknown").to(null);
        assertThat(obj, is(nullValue()));
    }

    @Test
    public void should_compose_user_data_by_parsing_template_file() {
        User user = from("userWithTemplate.json")
                .withTemplate("template/user.template")
                .to(User.class);
        assertThat(user, not(nullValue()));
        assertThat(user.getName().getFirst(), is("Joe"));
        assertThat(user.getName().getLast(), is("Sixpack"));
    }

    @Test
    public void should_compose_multi_user_data_by_parsing_template_file() {
        List<User> users = from("userWithMultiSections.json")
                .withTemplate("template/user.template")
                .toList(User.class);
        assertThat(users, not(nullValue()));
        assertThat(users.get(0).getName().getFirst(), is("Joe"));
        assertThat(users.get(0).getName().getLast(), is("Sixpack"));
        assertThat(users.get(2).getName().getFirst(), is("Yi"));
        assertThat(users.get(2).getName().getLast(), is("Zhang"));
    }

    @Test
    public void should_be_null_if_missing_variable_by_parsing_template_file() {
        User user = from("userWithTemplateMissingVariable.json")
                .withTemplate("template/user.template")
                .to(User.class);
        assertThat(user, is(nullValue()));
    }
}
