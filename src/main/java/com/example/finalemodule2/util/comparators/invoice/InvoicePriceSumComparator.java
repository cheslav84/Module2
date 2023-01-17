package com.example.finalemodule2.util.comparators.invoice;

import com.example.finalemodule2.entity.Device;
import com.example.finalemodule2.entity.Invoice;

import java.math.BigDecimal;
import java.util.Comparator;

public class InvoicePriceSumComparator implements Comparator<Invoice> {

    @Override
    public int compare(Invoice i1, Invoice i2) {
        return getInvoicePrice(i1).compareTo(getInvoicePrice(i2));
    }

    private BigDecimal getInvoicePrice(Invoice invoice){
        return invoice.getDevices().stream()
                .map(Device::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
