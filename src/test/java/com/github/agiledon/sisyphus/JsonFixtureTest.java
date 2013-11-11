package com.github.agiledon.sisyphus;

import com.github.agiledon.sisyphus.domain.json.StandardVariable;
import com.github.agiledon.sisyphus.domain.json.User;
import com.google.common.base.Objects;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import java.util.List;

import static com.github.agiledon.sisyphus.Fixture.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonFixtureTest {
    @Test
    public void should_compose_User_data_with_json_format() {
        User user = Fixture.from("user.json").to(User.class);
        MatcherAssert.assertThat(user, IsNot.not(CoreMatchers.nullValue()));
        MatcherAssert.assertThat(user.getName().getFirst(), CoreMatchers.is("Joe"));
    }

    @Test
    public void should_compose_User_data_with_gson_format() {
        User user = Fixture.from("user.gson").to(User.class);
        MatcherAssert.assertThat(user, IsNot.not(CoreMatchers.nullValue()));

        //gson can't support the case which fieldname is not match get method
        //gson can't convert "Rm9vYmFyIQ==" to byte[]
        MatcherAssert.assertThat(user.getName(), CoreMatchers.is(CoreMatchers.nullValue()));
    }

    @Test
    public void should_compose_User_data_from_cache_with_json_format() {
        User user = Fixture.from("user.json").to(User.class);
        MatcherAssert.assertThat(user, IsNot.not(CoreMatchers.nullValue()));

        User cachedUser = Fixture.from("user.json").to(User.class);
        MatcherAssert.assertThat(cachedUser, IsNot.not(CoreMatchers.nullValue()));

        MatcherAssert.assertThat(Objects.equal(user, cachedUser), CoreMatchers.is(true));
    }

    @Test
    public void should_compose_StandardVariables_data_with_json_format() {
        StandardVariable[] standardVariables = Fixture.from("standardVariable.json").to(StandardVariable[].class);

        //Jackson can't support the class without default constructor if not using annotation
        MatcherAssert.assertThat(standardVariables, CoreMatchers.is(CoreMatchers.nullValue()));
    }

    @Test
    public void should_compose_StandardVariables_data_with_gson_format() {
        StandardVariable[] standardVariables = Fixture.from("standardVariable.gson").to(StandardVariable[].class);
        MatcherAssert.assertThat(standardVariables, IsNot.not(CoreMatchers.nullValue()));
        MatcherAssert.assertThat(standardVariables[0].result, CoreMatchers.is("0"));
        MatcherAssert.assertThat(standardVariables[1].result, CoreMatchers.is("1"));
        MatcherAssert.assertThat(standardVariables[2].result, CoreMatchers.is("2"));
    }

    @Test
    public void should_compose_StandardVariables_data_by_parsing_template_file() {
        StandardVariable[] standardVariables = Fixture.from("standardVariableWithTemplate.gson")
                .withTemplate("template/standardVariable.template")
                .to(StandardVariable[].class);
        MatcherAssert.assertThat(standardVariables, IsNot.not(CoreMatchers.nullValue()));
        MatcherAssert.assertThat(standardVariables[0].result, CoreMatchers.is("0"));
        MatcherAssert.assertThat(standardVariables[1].result, CoreMatchers.is("1"));
        MatcherAssert.assertThat(standardVariables[2].result, CoreMatchers.is("2"));
    }

    @Test
    public void should_compose_User_data_from_cache_with_gson_format() {
        StandardVariable[] standardVariables = Fixture.from("standardVariable.gson").to(StandardVariable[].class);
        MatcherAssert.assertThat(standardVariables, IsNot.not(CoreMatchers.nullValue()));

        StandardVariable[] cachedStandardVariables = Fixture.from("standardVariable.gson").to(StandardVariable[].class);
        MatcherAssert.assertThat(cachedStandardVariables, IsNot.not(CoreMatchers.nullValue()));

        MatcherAssert.assertThat(Objects.equal(standardVariables, cachedStandardVariables), CoreMatchers.is(true));
    }

    @Test
    public void should_compose_user_data_by_parsing_template_file() {
        User user = Fixture.from("userWithTemplate.json")
                .withTemplate("template/user.template")
                .to(User.class);
        MatcherAssert.assertThat(user, IsNot.not(CoreMatchers.nullValue()));
        MatcherAssert.assertThat(user.getName().getFirst(), CoreMatchers.is("Joe"));
        MatcherAssert.assertThat(user.getName().getLast(), CoreMatchers.is("Sixpack"));
    }

    @Test
    public void should_compose_multi_user_data_by_parsing_template_file() {
        List<User> users = Fixture.from("userWithMultiSections.json")
                .withTemplate("template/user.template")
                .toList(User.class);
        MatcherAssert.assertThat(users, IsNot.not(CoreMatchers.nullValue()));
        MatcherAssert.assertThat(users.get(0).getName().getFirst(), CoreMatchers.is("Joe"));
        MatcherAssert.assertThat(users.get(0).getName().getLast(), CoreMatchers.is("Sixpack"));
        MatcherAssert.assertThat(users.get(2).getName().getFirst(), CoreMatchers.is("Yi"));
        MatcherAssert.assertThat(users.get(2).getName().getLast(), CoreMatchers.is("Zhang"));
    }


    @Test
    public void should_be_null_if_missing_variable_by_parsing_template_file() {
        User user = from("userWithTemplateMissingVariable.json")
                .withTemplate("template/user.template")
                .to(User.class);
        assertThat(user, is(nullValue()));
    }
}