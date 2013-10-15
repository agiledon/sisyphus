package com.github.agiledon.sisyphus;

import com.github.agiledon.sisyphus.asn.domain.EthPhyPortAttr;
import com.github.agiledon.sisyphus.asn.domain.EthPhyPortAttrList_T;
import com.github.agiledon.sisyphus.composer.Composer;
import com.github.agiledon.sisyphus.domain.json.StandardVariable;
import com.github.agiledon.sisyphus.domain.json.User;
import com.google.common.base.Objects;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.github.agiledon.sisyphus.Fixture.from;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

public class FixtureTest {
    @Test
    public void should_compose_Type_data_with_yaml_format() {
        Map<String, List<String>> types = from("type.yaml").to(Map.class);
        assertThat(types, not(nullValue()));
        assertThat(types.size(), is(2));
        assertThat(types.get("Type1").get(0), is("Type1sub1"));
    }

    @Test
    public void should_compose_Type_data_by_parsing_template_file() {
        Map<String, List<String>> types = from("typeWithTemplate.yaml")
                .withTemplate("template/type.template")
                .to(Map.class);
        assertThat(types, not(nullValue()));
        assertThat(types.size(), is(2));
        assertThat(types.get("Type1").get(0), is("Type1sub1"));
    }

    @Test
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
        Composer from = from("user.json");
        User user = from.to(User.class);
        assertThat(user, not(nullValue()));

        User cachedUser = from.to(User.class);
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
}
