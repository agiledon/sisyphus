package com.github.agiledon.sisyphus.assist;

import com.github.agiledon.sisyphus.assist.printer.*;
import com.github.agiledon.sisyphus.domain.json.User;
import com.github.agiledon.sisyphus.domain.sis.Invoice;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static com.github.agiledon.sisyphus.assist.FixtureAssist.*;
import static com.google.common.io.Files.getNameWithoutExtension;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class FixtureAssistTest extends UserDataFixture {

    private String yamlFileName = "outputUser.yaml";
    private String gsonFileName = "outputUser.gson";
    private String jsonFileName = "outputUser.json";
    private String sisFileName = "outputUser.sis";

    @Before
    public void setUp() throws Exception {
        clearFiles(yamlFileName, gsonFileName, jsonFileName);
    }

    private void clearFiles(String... datafiles) {
        for (String datafile : datafiles) {
            File file = new File(datafile);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Test
    public void should_get_JsonPrinter_and_pass_object_to_it() {
        Printer printer = json();
        assertThat(printer, instanceOf(JsonPrinter.class));
    }

    @Test
    public void should_get_GsonPrinter_and_pass_object_to_it() {
        Printer printer = FixtureAssist.gson();
        assertThat(printer, instanceOf(GsonPrinter.class));
    }

    @Test
    public void should_get_YamlPrinter_and_pass_object_to_it() {
        Printer printer = yaml();
        assertThat(printer, instanceOf(YamlPrinter.class));
    }

    @Test
    public void should_get_SisPrinter_and_pass_object_to_it() {
        Printer printer = sis();
        assertThat(printer, instanceOf(SisPrinter.class));
    }

    @Test
    public void should_print_data_to_specific_file() {
        yaml().print(createUser(), getNameWithoutExtension(yamlFileName));
        gson().print(createUser(), getNameWithoutExtension(gsonFileName));
        json().print(createUser(), getNameWithoutExtension(jsonFileName));
        sis().print(createUser(), getNameWithoutExtension(sisFileName));

        assertFilesExisted(yamlFileName, gsonFileName, jsonFileName, sisFileName);
    }

    @Test
    public void should_create_default_value_with_given_user_class() {
        User user = stub(User.class);
        assertThat(user, not(nullValue()));
        assertThat(user.getGender(), is(User.Gender.MALE));
        assertThat(user.getName().getFirst(), is(""));
        assertThat(user.getName().getLast(), is(""));
        assertThat(user.isVerified(), is(false));
        assertThat(user.getUserImage(), not(nullValue()));
    }

    @Test
    public void should_create_default_value_with_invoice_class() {
        Invoice invoice = stub(Invoice.class);

        assertThat(invoice, not(nullValue()));
        assertThat(invoice.date, is(""));
        assertThat(invoice.products.size(), is(0));
        assertThat(invoice.billTo.given, is(""));
        assertThat(invoice.tax, is(0f));
    }

    private void assertFilesExisted(String... dataFileNames) {
        for (String dataFileName : dataFileNames) {
            File file = new File(dataFileName);
            assertThat(file.exists(), is(true));
        }
    }
}
