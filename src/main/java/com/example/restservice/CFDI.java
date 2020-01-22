package com.example.restservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CFDI {
    private long sequentialNumber;
    private String cadenaOriginalSAT;
    private String noCertificadoSAT;
    private String status;
    private String selloSAT;
    private String selloCFDI;
    private Date fechaTimbrado;
    private String qrCode;
    private String invoiceId;
    private String purpose;
    private Invoice invoiceDetails;

}
