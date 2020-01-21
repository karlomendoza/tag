package com.example.restservice;

import java.util.Date;



public class CFDI {
    private String cadenaOriginalSAT;
    private String noCertificadoSAT;
    private String status;
    private String selloSAT;
    private String selloCFDI;
    private Date fechaTimbrado;
    private String qrCode;
    private String invoiceId;

    public CFDI(String cadenaOriginalSAT, String noCertificadoSAT, String status, String selloSAT, String selloCFDI, Date fechaTimbrado, String qrCode, String invoiceId) {
        this.cadenaOriginalSAT = cadenaOriginalSAT;
        this.noCertificadoSAT = noCertificadoSAT;
        this.status = status;
        this.selloSAT = selloSAT;
        this.selloCFDI = selloCFDI;
        this.fechaTimbrado = fechaTimbrado;
        this.qrCode = qrCode;
        this.invoiceId = invoiceId;
    }

    public String getCadenaOriginalSAT() {
        return cadenaOriginalSAT;
    }

    public void setCadenaOriginalSAT(String cadenaOriginalSAT) {
        this.cadenaOriginalSAT = cadenaOriginalSAT;
    }

    public String getNoCertificadoSAT() {
        return noCertificadoSAT;
    }

    public void setNoCertificadoSAT(String noCertificadoSAT) {
        this.noCertificadoSAT = noCertificadoSAT;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSelloSAT() {
        return selloSAT;
    }

    public void setSelloSAT(String selloSAT) {
        this.selloSAT = selloSAT;
    }

    public String getSelloCFDI() {
        return selloCFDI;
    }

    public void setSelloCFDI(String selloCFDI) {
        this.selloCFDI = selloCFDI;
    }

    public Date getFechaTimbrado() {
        return fechaTimbrado;
    }

    public void setFechaTimbrado(Date fechaTimbrado) {
        this.fechaTimbrado = fechaTimbrado;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}
