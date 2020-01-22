package com.example.restservice.services;

import com.example.restservice.CFDI;
import com.example.restservice.Invoice;

public interface CfdiService {

    CFDI stampInvoice(Invoice invoice);

    CFDI dummyStampInvoice(Invoice invoice);

    CFDI getDummyCfdi(Long id);
}
