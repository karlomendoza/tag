# Tag example
Submit a post to the endpoint /tag

An example of the body is as follows:
 
`
{
	"invoiceId": 1,
    "issuingCompany": "DonUs",
    "receiverName": "Armando Montoya",
    "amount": 100,
    "taxAmount": 16,
    "purpose": "Testing"
    }
`

Response:

`
{
    "sequentialNumber": 0,
    "cadenaOriginalSAT": "Dummy original string",
    "noCertificadoSAT": "Dummy government certificate number",
    "status": "Simulated",
    "selloSAT": "Dummy government stamp",
    "selloCFDI": "Invoice unique stamp simulated",
    "fechaTimbrado": "2020-01-22T23:09:53.932+0000",
    "qrCode": "No QR code",
    "invoiceId": null,
    "purpose": "Invoice simulation testing",
    "invoiceDetails": {
        "invoiceId": "1",
        "issuingCompany": "DonUs",
        "receiverName": "Armando Montoya",
        "amount": 100,
        "taxAmount": 16,
        "purpose": "Testing"
    }
}
`