package com.github.agiledon.sisyphus;

import com.github.agiledon.sisyphus.domain.sis.Invoice;
import com.github.agiledon.sisyphus.domain.sis.InvoiceWithArray;
import com.github.agiledon.sisyphus.domain.sis.Product;
import com.github.agiledon.sisyphus.domain.sis.ProductList;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.github.agiledon.sisyphus.Fixture.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

public class SisFixtureTest {

    @Test
    public void should_get_null_given_unknown_file() {
        Object obj = from("data.unknown").to(null);
        assertThat(obj, is(nullValue()));
    }

    @Test
    public void should_compose_invoice_data_according_sis_format() {
        Invoice invoice = from("invoice.sis").to(Invoice.class);
        assertThat(invoice, not(nullValue()));
        assertThat(invoice.date, is("2001-01-23"));
        assertThat(invoice.products.size(), is(2));
        assertThat(invoice.products.get(0).description, is("Basketball"));
        assertThat(invoice.billTo.given, is("Chris"));
        assertThat(invoice.tax, is(251.42f));
    }

    @Test
    public void should_compose_invoice_data_with_array_according_sis_format() {
        InvoiceWithArray invoice = from("invoiceWithArray.sis").to(InvoiceWithArray.class);
        assertThat(invoice, not(nullValue()));
        assertThat(invoice.date, is("2001-01-23"));
        assertThat(invoice.products.length, is(2));
        assertThat(invoice.products[0].description, is("Basketball"));
        assertThat(invoice.billTo.given, is("Chris"));
        assertThat(invoice.tax, is(251.42f));
    }
}
