package com.github.agiledon.sisyphus.assist.printer;

import com.github.agiledon.sisyphus.domain.json.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GsonPrinterTest extends UserDataFixture {
    @Test
    public void should_serialize_specific_object_to_string_with_json_format() {
        GsonPrinter printer = new GsonPrinter();
        User user = createUser();
        String result = printer.serialize(user);
        assertThat(result, is("{\"_gender\":\"MALE\",\"_name\":{\"_first\":\"Yi\",\"_last\":\"Zhang\"},\"_isVerified\":true,\"_userImage\":[48,48,48,48,49,49,49,49]}"));
    }
}
