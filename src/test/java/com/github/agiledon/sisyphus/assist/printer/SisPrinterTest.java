package com.github.agiledon.sisyphus.assist.printer;

import com.github.agiledon.sisyphus.domain.sis.Invoice;
import com.github.agiledon.sisyphus.domain.sis.InvoiceWithArray;
import com.github.agiledon.sisyphus.domain.sis.InvoiceWithList;
import org.junit.Before;
import org.junit.Test;

import static com.github.agiledon.sisyphus.Fixture.from;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SisPrinterTest {

    private SisPrinter sisPrinter;

    @Before
    public void setUp() throws Exception {
        sisPrinter = new SisPrinter();
    }

    @Test
    public void should_generate_string_for_invoice_with_ProductList_object() {
        Invoice invoice = from("invoice.sis").to(Invoice.class);

        assertThat(sisPrinter.serialize(invoice), is(
                getExpectedContent("<", ">")));
    }

    @Test
    public void should_generate_string_for_invoice_with_ArrayList() {
        InvoiceWithList invoice = from("invoiceWithList.sis").to(InvoiceWithList.class);

        assertThat(sisPrinter.serialize(invoice), is(
                getExpectedContent("<", ">")));
    }

    @Test
    public void should_generate_string_for_invoice_with_Array() {
        InvoiceWithArray invoice = from("invoiceWithArray.sis").to(InvoiceWithArray.class);

        assertThat(sisPrinter.serialize(invoice), is(
                getExpectedContent("[", "]")));
    }

    private String getExpectedContent(String startCollectionIndicator, String endCollectionIndicator) {
        return String.format("{\n" +
                "    invoice = 34843\n" +
                "    date = 2001-01-23\n" +
                "    tax = 251.42\n" +
                "    total = 4443.52\n" +
                "    comments = Late afternoon is best.Backup contact is Nancy Billsmer @ 338-4338.\n" +
                "    billTo = {\n" +
                "        given = Chris\n" +
                "        family = Dumars\n" +
                "        address = {\n" +
                "            lines = 458 Walkman Dr. Suite #292\n" +
                "            city = Royal Oak\n" +
                "            state = MI\n" +
                "            postal = 48046\n" +
                "        }\n" +
                "    }\n" +
                "    shipTo = {\n" +
                "        given = Chris\n" +
                "        family = Dumars\n" +
                "        address = {\n" +
                "            lines = 458 Walkman Dr. Suite #292\n" +
                "            city = Royal Oak\n" +
                "            state = MI\n" +
                "            postal = 48046\n" +
                "        }\n" +
                "    }\n" +
                "    products = %s\n" +
                "        {\n" +
                "            sku = BL394D\n" +
                "            quantity = 4\n" +
                "            description = Basketball\n" +
                "            price = 450.0\n" +
                "        }\n" +
                "        {\n" +
                "            sku = BL4438H\n" +
                "            quantity = 1\n" +
                "            description = Super Hoop\n" +
                "            price = 2392.0\n" +
                "        }\n" +
                "    %s\n" +
                "}\n", startCollectionIndicator, endCollectionIndicator);
    }
}
