package com.example.finalemodule2.util.comparators.invoice;

import com.example.finalemodule2.entity.Invoice;

import java.util.Comparator;

public class InvoiceUserAgeComparator implements Comparator<Invoice> {

    @Override
    public int compare(Invoice i1, Invoice i2) {
        return Integer.compare(i2.getCustomer().getAge(), i1.getCustomer().getAge());
    }
}
