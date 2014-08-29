package com.github.agiledon.sisyphus;

import com.github.agiledon.sisyphus.domain.json.StandardVariable;
import com.github.agiledon.sisyphus.domain.json.User;
import com.google.common.base.Objects;
import org.junit.Test;

import java.util.List;

import static com.github.agiledon.sisyphus.Fixture.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class JsonFixtureTest {
    @Test
    public void should_compose_User_data_with_json_format() {
        User user = Fixture.from("user.json").to(User.class);
        assertThat(user, not(nullValue()));
        assertUserName(user, "Joe", "Sixpack");
        assertThat(user.getGender(), is(User.Gender.MALE));
        assertThat(user.isVerified(), is(false));
    }

    @Test
    public void should_compose_User_data_with_gson_format() {
        User user = Fixture.from("user.gson").to(User.class);
        assertThat(user, not(nullValue()));

        //gson can't support the case which fieldname is not match get method
        //gson can't convert "Rm9vYmFyIQ==" to byte[]
        assertThat(user.getName(), is(nullValue()));
    }

    @Test
    public void should_compose_User_data_from_cache_with_json_format() {
        User user = Fixture.from("user.json").to(User.class);
        assertThat(user, not(nullValue()));

        User cachedUser = Fixture.from("user.json").to(User.class);
        assertThat(cachedUser, not(nullValue()));

        assertThat(Objects.equal(user, cachedUser), is(true));
    }

    @Test
    public void should_compose_StandardVariables_data_with_json_format() {
        StandardVariable[] standardVariables = Fixture.from("standardVariable.json").to(StandardVariable[].class);

        //Jackson can't support the class without default constructor if not using annotation
        assertThat(standardVariables, is(nullValue()));
    }

    @Test
    public void should_compose_StandardVariables_data_with_gson_format() {
        StandardVariable[] standardVariables = Fixture.from("standardVariable.gson").to(StandardVariable[].class);
        assertThat(standardVariables, not(nullValue()));
        assertThat(standardVariables[0].result, is("0"));
        assertThat(standardVariables[1].result, is("1"));
        assertThat(standardVariables[2].result, is("2"));
    }

    @Test
    public void should_compose_StandardVariables_data_by_parsing_template_file() {
        StandardVariable[] standardVariables = Fixture.from("standardVariableWithTemplate.gson")
                .withTemplate("template/standardVariable.template")
                .to(StandardVariable[].class);
        assertThat(standardVariables, not(nullValue()));
        assertThat(standardVariables[0].result, is("0"));
        assertThat(standardVariables[1].result, is("1"));
        assertThat(standardVariables[2].result, is("2"));
    }

    @Test
    public void should_compose_User_data_from_cache_with_gson_format() {
        StandardVariable[] standardVariables =
                Fixture.from("standardVariable.gson")
                       .to(StandardVariable[].class);
        assertThat(standardVariables, not(nullValue()));

        StandardVariable[] cachedStandardVariables = Fixture.from("standardVariable.gson").to(StandardVariable[].class);
        assertThat(cachedStandardVariables, not(nullValue()));

        assertThat(Objects.equal(standardVariables, cachedStandardVariables), is(true));
    }

    @Test
    public void should_compose_user_data_by_parsing_template_file() {
        User user = Fixture.from("userWithTemplate.json")
                .withTemplate("template/user.template")
                .to(User.class);
        assertThat(user, not(nullValue()));
        assertUserName(user, "Joe", "Sixpack");
    }

    @Test
    public void should_compose_multi_user_data_by_parsing_template_file() {
        List<User> users = Fixture.from("userWithMultiSections.json")
                                  .withTemplate("template/user.template")
                                  .toList(User.class);
        assertThat(users, not(nullValue()));

        User firstUser = users.get(0);
        assertUserName(firstUser, "Joe", "Sixpack");

        User thirdUser = users.get(2);
        assertUserName(thirdUser, "Yi", "Zhang");
    }

    private void assertUserName(User thirdUser, String yi, String zhang) {
        assertThat(thirdUser.getName().getFirst(), is(yi));
        assertThat(thirdUser.getName().getLast(), is(zhang));
    }


    @Test
    public void should_be_null_if_missing_variable_by_parsing_template_file() {
        User user = from("userWithTemplateMissingVariable.json")
                .withTemplate("template/user.template")
                .to(User.class);
        assertThat(user, is(nullValue()));

        assertNotNull(user);
    }
}