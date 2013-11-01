package com.github.agiledon.sisyphus.domain.sis;

public class SisInvoice {
    public Integer invoice; // invoice
    public String date; // date
    public Person billTo;// bill-to
    public Person shipTo;// ship-to
    public ProductList products;
    public Float tax;
    public Float total;
    public String comments;

}
