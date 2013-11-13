package com.github.agiledon.sisyphus.assist;

import com.github.agiledon.sisyphus.assist.printer.*;
import com.github.agiledon.sisyphus.domain.json.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FixtureAssistTest {
    @Test
    public void should_get_JsonPrinter_and_pass_class_to_it() {
        Printer printer = FixtureAssist.json(User.class);
        assertThat(printer, instanceOf(JsonPrinter.class));
        assertEquals(User.class, printer.sourceClass());
    }

    @Test
    public void should_get_GsonPrinter_and_pass_class_to_it() {
        Printer printer = FixtureAssist.gson(User.class);
        assertThat(printer, instanceOf(GsonPrinter.class));
        assertEquals(User.class, printer.sourceClass());
    }

    @Test
    public void should_get_YamlPrinter_and_pass_class_to_it() {
        Printer printer = FixtureAssist.yaml(User.class);
        assertThat(printer, instanceOf(YamlPrinter.class));
        assertEquals(User.class, printer.sourceClass());
    }

    @Test
    public void should_get_SisPrinter_and_pass_class_to_it() {
        Printer printer = FixtureAssist.sis(User.class);
        assertThat(printer, instanceOf(SisPrinter.class));
        assertEquals(User.class, printer.sourceClass());
    }
}
