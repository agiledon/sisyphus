package com.github.agiledon.sisyphus.assist;

import com.github.agiledon.sisyphus.assist.printer.*;
import com.github.agiledon.sisyphus.domain.json.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class FixtureAssistTest {

    private User serializedObject;

    @Before
    public void setUp() throws Exception {
        serializedObject = new User();
    }

    @Test
    public void should_get_JsonPrinter_and_pass_object_to_it() {
        Printer printer = FixtureAssist.json();
        assertThat(printer, instanceOf(JsonPrinter.class));
    }

    @Test
    public void should_get_GsonPrinter_and_pass_object_to_it() {
        Printer printer = FixtureAssist.gson();
        assertThat(printer, instanceOf(GsonPrinter.class));
    }

    @Test
    public void should_get_YamlPrinter_and_pass_object_to_it() {
        Printer printer = FixtureAssist.yaml();
        assertThat(printer, instanceOf(YamlPrinter.class));
    }

    @Test
    public void should_get_SisPrinter_and_pass_object_to_it() {
        Printer printer = FixtureAssist.sis();
        assertThat(printer, instanceOf(SisPrinter.class));
    }
}
