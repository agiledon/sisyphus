package com.github.agiledon.sisyphus.domain.sis;

public class InvoiceWithArray {
    public Integer invoice; // invoice
    public String date; // date
    public Person billTo;// bill-to
    public Person shipTo;// ship-to
    public Product[] products;
    public Float tax;
    public Float total;
    public String comments;

}
