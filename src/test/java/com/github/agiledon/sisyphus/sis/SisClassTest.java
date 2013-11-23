package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.domain.sis.Invoice;
import org.junit.Ignore;
import org.junit.Test;

import static com.github.agiledon.sisyphus.Fixture.from;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SisClassTest {

    @Test
    @Ignore
    public void should_generate_string_for_invoice_with_ProductList_object() {
        Invoice invoice = from("invoice.sis").to(Invoice.class);
        SisClass sisClass = new SyntaxParser().parseClassFromObject(invoice);

        assertThat(sisClass.toString(), is(
                "{\n" +
                "    invoice = 34843\n" +
                "    date = 2001-01-23\n" +
                "    tax = 251.42\n" +
                "    total = 4443.52\n" +
                "    comments = Late afternoon is best.Backup contact is Nancy Billsmer @ 338-4338.\n" +
                "    billTo = {\n" +
                "        given = Chris\n" +
                "        family = Dumars\n" +
                "        address = {\n" +
                "                lines  = 458 Walkman Dr. Suite #292\n" +
                "                city   = Royal Oak\n" +
                "                state  = MI\n" +
                "                postal = 48046\n" +
                "        }\n" +
                "    }\n" +
                "    shipTo = {\n" +
                "        given = Chris\n" +
                "        family = Dumars\n" +
                "        address = {\n" +
                "                lines = 458 Walkman Dr. Suite #292\n" +
                "                city  = Royal Oak\n" +
                "                state = MI\n" +
                "                postal   = 48046\n" +
                "        }\n" +
                "    }\n" +
                "    products = <\n" +
                "        {\n" +
                "            sku = BL394D\n" +
                "            quantity = 4\n" +
                "            description = Basketball\n" +
                "            price = 450.00\n" +
                "        }\n" +
                "        {\n" +
                "            sku = BL4438H\n" +
                "            quantity = 1\n" +
                "            description = Super Hoop\n" +
                "            price = 2392.00\n" +
                "        }\n" +
                "    >\n" +
                "}\n"));
    }
}
