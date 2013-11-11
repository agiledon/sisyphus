package com.github.agiledon.sisyphus.domain.sis;

import java.util.List;

public class InvoiceWithList {
    public Integer invoice; // invoice
    public String date; // date
    public Person billTo;// bill-to
    public Person shipTo;// ship-to
    public List<Product> products;
    public Float tax;
    public Float total;
    public String comments;

}
