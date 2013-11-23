package com.github.agiledon.sisyphus.assist.printer;

import com.github.agiledon.sisyphus.domain.json.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class YamlPrinterTest extends UserDataFixture {
    @Test
    public void should_serialize_specific_object_to_string_with_yaml_format() {
        YamlPrinter printer = new YamlPrinter();
        User user = createUser();
        String result = printer.serialize(user);
        assertThat(result, is("!!com.github.agiledon.sisyphus.domain.json.User\n" +
                "gender: MALE\n" +
                "name: {first: Yi, last: Zhang}\n" +
                "userImage: !!binary |-\n" +
                "  MDAwMDExMTE=\n" +
                "verified: true\n"));
    }

}
