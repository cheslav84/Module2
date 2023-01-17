package com.example.finalemodule2.util.comparators.invoice;

import com.example.finalemodule2.entity.Device;
import com.example.finalemodule2.entity.Invoice;

import java.math.BigDecimal;
import java.util.Comparator;

public class InvoiceDevicesAmountComparator implements Comparator<Invoice> {

    @Override
    public int compare(Invoice i1, Invoice i2) {
        return Integer.compare(i1.getDevices().size(), i2.getDevices().size());
    }
}
