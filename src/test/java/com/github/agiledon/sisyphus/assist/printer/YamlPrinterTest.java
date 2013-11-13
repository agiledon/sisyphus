package com.github.agiledon.sisyphus.assist.printer;

import com.github.agiledon.sisyphus.domain.json.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class YamlPrinterTest {
    @Test
    public void should_serialize_specific_object_to_string() {
        YamlPrinter printer = new YamlPrinter();
        User user = new User();
        user.setGender(User.Gender.MALE);
        User.Name name = new User.Name();
        name.setFirst("Yi");
        name.setLast("Zhang");
        user.setName(name);
        user.setVerified(true);
        String result = printer.serialize(user);
        assertThat(result, is("!!com.github.agiledon.sisyphus.domain.json.User\n" +
                "gender: MALE\n" +
                "name: {first: Yi, last: Zhang}\n" +
                "userImage: null\n" +
                "verified: true\n"));
    }
}
