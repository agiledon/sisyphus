package com.github.agiledon.sisyphus.domain.yaml;

public class Invoice {
    public Integer invoice; // invoice
    public String date; // date
    public Person billTo;// bill-to
    public Person shipTo;// ship-to
    public Product[] products;
    public Float tax;
    public Float total;
    public String comments;

}
