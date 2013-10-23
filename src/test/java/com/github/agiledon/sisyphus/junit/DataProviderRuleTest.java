package com.github.agiledon.sisyphus.junit;

import com.github.agiledon.sisyphus.domain.json.User;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

public class DataProviderRuleTest {
    @Rule
    public DataProviderRule dataProvider = new DataProviderRule();

    @Test
    @TestDataResource(resourceName = "user.json", targetClass = User.class)
    public void should_compose_User_data_with_json_format() {
        User user = dataProvider.provideData();
        assertThat(user, not(nullValue()));
        assertThat(user.getName().getFirst(), is("Joe"));
    }

    @Test
    @TestDataResource(resourceName = "userWithTemplate.json",
            templateName = "template/user.template",
            targetClass = User.class)
    public void should_compose_user_data_by_parsing_template_file() {
        User user = dataProvider.provideData();
        assertThat(user, not(nullValue()));
        assertThat(user.getName().getFirst(), is("Joe"));
        assertThat(user.getName().getLast(), is("Sixpack"));
    }
}
