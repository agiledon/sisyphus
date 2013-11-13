package com.github.agiledon.sisyphus.assist;

import com.github.agiledon.sisyphus.assist.printer.*;
import com.github.agiledon.sisyphus.domain.json.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FixtureAssistTest {

    private User serializedObject;

    @Before
    public void setUp() throws Exception {
        serializedObject = new User();
    }

    @Test
    public void should_get_JsonPrinter_and_pass_object_to_it() {
        Printer printer = FixtureAssist.json(serializedObject);
        assertThat(printer, instanceOf(JsonPrinter.class));
        assertEquals(serializedObject, printer.sourceObject());
    }

    @Test
    public void should_get_GsonPrinter_and_pass_object_to_it() {
        Printer printer = FixtureAssist.gson(serializedObject);
        assertThat(printer, instanceOf(GsonPrinter.class));
        assertEquals(serializedObject, printer.sourceObject());
    }

    @Test
    public void should_get_YamlPrinter_and_pass_object_to_it() {
        Printer printer = FixtureAssist.yaml(serializedObject);
        assertThat(printer, instanceOf(YamlPrinter.class));
        assertEquals(serializedObject, printer.sourceObject());
    }

    @Test
    public void should_get_SisPrinter_and_pass_object_to_it() {
        Printer printer = FixtureAssist.sis(serializedObject);
        assertThat(printer, instanceOf(SisPrinter.class));
        assertEquals(serializedObject, printer.sourceObject());
    }
}
