package com.github.agiledon.sisyphus.assist.printer;

import com.github.agiledon.sisyphus.domain.json.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonPrinterTest extends UserDataFixture {
    @Test
    public void should_serialize_specific_object_to_string_with_json_format() {
        JsonPrinter printer = new JsonPrinter();
        User user = createUser();
        String result = printer.serialize(user);
        assertThat(result, is("{\"name\":{\"first\":\"Yi\",\"last\":\"Zhang\"},\"verified\":true,\"gender\":\"MALE\",\"userImage\":null}"));
    }
}
