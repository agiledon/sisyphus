package com.github.agiledon.sisyphus.junit;

import com.github.agiledon.sisyphus.domain.json.User;
import com.github.agiledon.sisyphus.exception.NotSupportedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

public class DataProviderTest {
    @Rule
    public DataProvider dataProvider = new DataProvider();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    @DataResource(resourceName = "user.json", targetClass = User.class)
    public void should_compose_User_data_with_json_format() {
        User user = dataProvider.provideData();
        assertThat(user, not(nullValue()));
        assertThat(user.getName().getFirst(), is("Joe"));
    }

    @Test
    @DataResource(resourceName = "userWithTemplate.json",
            templateName = "template/user.template",
            targetClass = User.class)
    public void should_compose_user_data_by_parsing_template_file() {
        User user = dataProvider.provideData();
        assertThat(user, not(nullValue()));
        assertThat(user.getName().getFirst(), is("Joe"));
        assertThat(user.getName().getLast(), is("Sixpack"));
    }

    @Test
    @DataResource(resourceName = "userWithMultiSections.json",
            templateName = "template/user.template",
            targetClass = User.class)
    public void should_compose_user_data_list_by_parsing_template_file() {
        List<User> users = dataProvider.provideDataList();
        assertThat(users, not(nullValue()));
        assertThat(users.get(0).getName().getFirst(), is("Joe"));
        assertThat(users.get(0).getName().getLast(), is("Sixpack"));
        assertThat(users.get(2).getName().getFirst(), is("Yi"));
        assertThat(users.get(2).getName().getLast(), is("Zhang"));
    }

    @Test
    @DataResource(resourceName = "userWithMultiSections.json",
            targetClass = User.class)
    public void should_throw_NotSupportException_if_missing_template_file_when_parse_to_list() {
        expectedException.expect(NotSupportedException.class);
        expectedException.expectMessage("Must provide template file name");

        dataProvider.provideDataList();
    }
}
