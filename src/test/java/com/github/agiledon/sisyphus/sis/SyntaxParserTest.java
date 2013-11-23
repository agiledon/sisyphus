package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.domain.sis.Invoice;
import com.github.agiledon.sisyphus.domain.sis.ProductList;
import com.github.agiledon.sisyphus.exception.FailedSerializationException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.agiledon.sisyphus.Fixture.from;
import static com.github.agiledon.sisyphus.util.ResourceLoader.loadResource;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SyntaxParserTest {
    private SyntaxParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new SyntaxParser();
    }

    @Test
    public void should_parse_completed_segment_for_one_class_with_nested_class() {
        SisClass sisClass = parser.parseClassFromResource(loadResource("invoice.sis"));
        assertThat(sisClass.getBasicFields().size(), is(5));
        assertThat(sisClass.getBasicFields().get(0), is(new BasicField("invoice", "34843")));

        List<SisClass> childClassProperties = sisClass.getChildClasses();
        assertThat(childClassProperties.size(), is(3));
        assertThat(childClassProperties.get(0).getFieldName(), is("billTo"));


        List<SisClass> grandsonProperties = childClassProperties.get(0).getChildClasses();
        assertThat(grandsonProperties.size(), is(1));
        assertThat(grandsonProperties.get(0).getFieldName(), is("address"));
        assertThat(grandsonProperties.get(0).getBasicFields().size(), is(4));
    }

    @Test
    public void should_parse_sis_class_from_invoice_object() {
        Invoice invoice = from("invoice.sis").to(Invoice.class);

        SisClass sisClass = parser.parseClassFromObject(invoice);
        assertThat(sisClass, instanceOf(SisNormalClass.class));
        assertThat(sisClass.getBasicFields().size(), is(5));

        List<SisClass> childClasses = sisClass.getChildClasses();
        assertThat(childClasses.size(), is(3));
        assertThat(childClasses.get(0), instanceOf(SisNormalClass.class));
        assertThat(childClasses.get(1), instanceOf(SisNormalClass.class));
        assertThat(childClasses.get(2), instanceOf(SisListClass.class));

        SisClass personClass = childClasses.get(0);
        assertThat(personClass.getBasicFields().size(), is(2));
        assertThat(personClass.getChildClasses().size(), is(1));
        assertThat(personClass.getChildClasses().get(0), instanceOf(SisNormalClass.class));

        SisClass addressClass = personClass.getChildClasses().get(0);
        assertThat(addressClass.getBasicFields().size(), is(4));
        assertThat(addressClass.getChildClasses().size(), is(0));

        SisClass productListClass = childClasses.get(2);
        assertThat(productListClass.getChildClasses().size(), is(2));
        assertThat(productListClass.getChildClasses().get(0), instanceOf(SisNormalClass.class));
    }

    @Test(expected = FailedSerializationException.class)
    public void should_throw_exception_if_given_object_is_null() {
        parser.parseClassFromObject(null);
    }

    @Test
    public void should_parse_to_SisListClass_from_list_object_with_primitive_type() {
        SisClass sisClass = parser.parseClassFromObject(newArrayList("line"));
        assertThat(sisClass, instanceOf(SisListClass.class));
        assertThat(sisClass.getBasicElements().size(), is(1));
    }

    @Test
    public void should_parse_to_SisListClass_from_customized_list_object() {
        SisClass sisClass = parser.parseClassFromObject(new ProductList());
        assertThat(sisClass, instanceOf(SisListClass.class));
        assertThat(sisClass.getBasicElements().size(), is(0));
    }

    @Test
    public void should_parse_to_SisListClass_from_list_object_with_customized_type() {
        Invoice invoice = from("invoice.sis").to(Invoice.class);
        SisClass sisClass = parser.parseClassFromObject(newArrayList(invoice));
        assertThat(sisClass, instanceOf(SisListClass.class));
        assertThat(sisClass.getBasicElements().size(), is(0));

        List<SisClass> childClasses = sisClass.getChildClasses();
        assertThat(childClasses.size(), is(1));
        assertThat(childClasses.get(0), instanceOf(SisNormalClass.class));
        assertThat(childClasses.get(0).getBasicFields().size(), is(5));
        assertThat(childClasses.get(0).getChildClasses().size(), is(3));
    }

    @Test
    public void should_parse_to_SisCollectionClass_from_array_object() {
        SisClass sisClass = parser.parseClassFromObject(new String[] {"line"});
        assertThat(sisClass, instanceOf(SisArrayClass.class));
    }

    @Test
    public void should_parse_to_SisNormalClass_from_customized_object() {
        SisClass sisClass = parser.parseClassFromObject(new Invoice());
        assertThat(sisClass, instanceOf(SisNormalClass.class));
    }
}
