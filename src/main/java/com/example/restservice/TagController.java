package com.example.restservice;

import com.example.restservice.services.CfdiService;
import com.launchdarkly.client.LDClientInterface;
import com.launchdarkly.client.LDUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RestController
// Swagger URL will be at: <proxy url>/v2/api-docs
public class TagController {

	private final CfdiService cfdiService;
	private final LDClientInterface ldClient;

	public TagController(CfdiService cfdiService, LDClientInterface ldClient) {
		this.cfdiService = cfdiService;
		this.ldClient = ldClient;
	}

	/*
	@GetMapping("/tag")
	public CFDI tag(@RequestParam(value = "cfdiId") Long cfdiId) throws IOException {


		//LDClient ldClient = new LDClient("sdk-d3e4544e-0461-4340-86a2-77d6f70fbc06");
		LDUser user = getLdUser();

		boolean showFeature = ldClient.boolVariation("test-1", user, false);

		ldClient.close();

		if (showFeature) {
			//return new CFDI( "123", "456", "Test", "Sello de prueba", "Sello CFDI", new Date(), "qrCode", "123");
			return CFDI
					.builder()
					.cadenaOriginalSAT("Dummy original string")
					.noCertificadoSAT("Dummy government certificate number")
					.fechaTimbrado(new Date())
					.status("Simulated")
					.selloSAT("Dummy government stamp")
					.selloCFDI("Invoice unique stamp simulated")
					.qrCode("No QR code")
					.purpose("Invoice simulation testing")
					.build();

		} else {
			String cadenaOriginalSat = "||1.1|8d844a26-f4ee-4b45-bf77-ad0b3cf16ee3|2018-08-29T13:38:15|AAA010101AAA|aIwMYuU8vO9x5M1IDjbK1Tjv5Wlwj30C8Oom6Nbx1NFK6H1KUUD7p57NNVrbr7xJWL5PHm97VpUfouQfhSBHZb33OO5GBelcg7TVQ2Oedq/ZciD/LYtiBzkA+DRE+y57FzaM5ccUAqJsbuoPTjeF3eWeNcLIgseBTXN3o/k+ccQQltyNABiJpFAuIhj3IM9vV5pnAtuBu/wapTbODmrTLiQcGK9abZ+GI5/NYpEUs+xT/2xHq6GiminB/OOFCLYv6J+DCrZp0JOfEWLqfo0mZVYg3tfrezYSB84PkwWSDw4l5Gpv+CuXl+AMQMIojf+688kO85ytqq65i5CewsARtw==|20001000000300022323||";
			String noCertificadoSAT = "20001000000300022323";
			String status = "Success";
			String selloSAT="BPRJOOv0DrLyxIApE/NvJpg0yG75VNtCgWfQevpXrHL8XmWedv6ST0vMoMuXLbcAmcOei5e4aoHHqiwuC2lBiFXmvJhaIqCfBNzAZdgvt2LOwBekZXWySarcN/MfIl8Ti4FdyFIM5kmSfaATq3T5eAw2PbTpg9mx+OIL0nLjCIbBT4ck+AweGZMu9p0W+6HjKfYvcnbkUnOkgHrpMhZLfB05vBr4+z7ihrQx0xKCO6amzBVzgvQqUPvHvd2mem6iCGehHpryj87aH3y1+CATiYNJhX3zPS017+18oVQ05SIPyvZFkpEJ0/Yjut/TVys9nHP2SvK+HOMgplexaWClXg==";
			String selloCFDI="aIwMYuU8vO9x5M1IDjbK1Tjv5Wlwj30C8Oom6Nbx1NFK6H1KUUD7p57NNVrbr7xJWL5PHm97VpUfouQfhSBHZb33OO5GBelcg7TVQ2Oedq/ZciD/LYtiBzkA+DRE+y57FzaM5ccUAqJsbuoPTjeF3eWeNcLIgseBTXN3o/k+ccQQltyNABiJpFAuIhj3IM9vV5pnAtuBu/wapTbODmrTLiQcGK9abZ+GI5/NYpEUs+xT/2xHq6GiminB/OOFCLYv6J+DCrZp0JOfEWLqfo0mZVYg3tfrezYSB84PkwWSDw4l5Gpv+CuXl+AMQMIojf+688kO85ytqq65i5CewsARtw==";
			String qrCode="iVBORw0KGgoAAAANSUhEUgAAAIwAAACMCAYAAACuwEE+AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAyQSURBVHhe7ZLRqiS7DkPn/396Lg0tEIuo7FTch+GSBXqQrHi7N/Xn7+Wywf1gLlvcD+ayxf1gLlvcD+ayxf1gLlvcD+ayxf1gLlvcD+ayxf1gLlvcD+ayxf1gLlvcD+ayxf1gLlvcD+ayxf1gLlvcD+ayxcgH8+fPn1dKrLodiSonVV6poupx3u3vaoKRLavjOkqsuh2JKidVXqmi6nHe7e9qgpEtuwdVfc7lmYvunPgbn1d5wt+sJFIuuvMuu/0nRrZM/wDO5ZmL7pz4G59XecLfrCRSLrrzLrv9J0a28CB5Sux60c3f9ugT6iUJevG2J5jLU4L+hJEt6UBK7HrRzd/26BPqJQl68bYnmMtTgv6EkS3pQEokz1wwTz7lidSnEtVc+K6dvuh6StCfMLIlHUiJ5JkL5smnPJH6VKKaC9+10xddTwn6E0a2pAMpkTxz0c2TT7nwzpPEavZRIvWYUwnO/Y1L0J8wsiUdSInkmYtunnzKhXeeJFazjxKpx5xKcO5vXIL+hJEtuwexn3zKu6T3KU/4m6eeYK/yQnmai2pOdvtPjGw5/QHJp7xLep/yhL956gn2Ki+Up7mo5mS3/8TIFh20K3H9O7+rCUa2rI7rSFz/zu9qgpkth1Q/iHN55oQ9SaSccO5vXAnOk6f+Rf6Jq6p/EOfyzAl7kkg54dzfuBKcJ0/9i4xcxR/oP/pJFalfeZFyol4lQU/8jYukXPhb7zFP+gUjW3mgH/2kitSvvEg5Ua+SoCf+xkVSLvyt95gn/YLfbD2k+sH+T/FeygXnkqAXqZdy4R3PBXPvei6Yn/beMLNlmOoHas5eygXnkqAXqZdy4R3PBXPvei6Yn/beMLIlHchcMPfuKhfeWSlRzQV7b33KBX0i9ZjLM/8FI9t5qB/vuWDu3VUuvLNSopoL9t76lAv6ROoxl2f+C0a2p4PpSZorT3OS+swpkXLCub9xicoL5RRhvusnGNmmw3ggPUlz5WlOUp85JVJOOPc3LlF5oZwizHf9BLPbvlSHJ89cMPfuSl1S33f5POUV3XdpzrzrqQlmtgAe2PXMBXPvrtQl9X2Xz1Ne0X2X5sy7nppgZEt1WJrTi24uX+WSSL7Kk0TlK9SnKqp+yt8wssWPXR2W5vSim8tXuSSSr/IkUfkK9amKqp/yN8xsAdXhSYS5dz0XaV7lxLs+p0/4W5eovEi9lJOUnzC77Uv1A5IIc+96LtK8yol3fU6f8LcuUXmReiknKT9hdtuX6UPTvipPIrtzSaS8gu+SSJpXfoLZbV+mD037qjyJ7M4lkfIKvksiaV75CUa2pUOZC+apR9SjRMoFc++ucpF8yoV3Vjnxrs/pSeozn2BkGw/zYz0XzFOPqEeJlAvm3l3lIvmUC++scuJdn9OT1Gc+wcg2Hvb2UL2jSJpXufDOKhfeWYmkXHAu380J5/7GNcnINh729lC9o0iaV7nwzioX3lmJpFxwLt/NCef+xjXJ6LZ0IHN55oJzqYI9f9vJxW5O1KvUZfV2pf+C0b+SDmcuz1xwLlWw5287udjNiXqVuqzervRf8JO/wh/gP8pzkXKxO0995ZUq2PO3LpLmzCslqvkEP9nOw+WZi5SL3XnqK69UwZ6/dZE0Z14pUc0nGNnuP8YPTj7lXXzH07s097c788oTzdmrcuEdF0m5qOY7jGzRQTws+ZR38R1P79Lc3+7MK080Z6/KhXdcJOWimu8ws+WLDuOBb/Muqa+cItVcpF7KK1Lfd/mcvsvbdytmtnzRYTzwbd4l9ZVTpJqL1Et5Rer7Lp/Td3n7bsXMlkA6VDmV6PYEe/52lZNuLn8qkXyVSyTlJ8xuA9UPoRLdnmDP365y0s3lTyWSr3KJpPyEkW08TJ5KVD3OkxKr7kci5Qn20ruUC83ZY04JelLN3zCyjYfJU4mqx3lSYtX9SKQ8wV56l3KhOXvMKUFPqvkbRrfpQB7KPClRzUXqVfmuBH3C365EVp03+gWjW9PBzJMS1VykXpXvStAn/O1KZNV5o1/wm62g+gGcy1cSXU8JeuJvVqo47UuJ1Ev5CTNbCqqDOZevJLqeEvTE36xUcdqXEqmX8hNmtnxJB+56UvXlmYsq57zyCfXYr7zo5pX/JaN/RYfzB+x6UvXlmYsq57zyCfXYr7zo5pX/JSN/Jf0AKlH1OJcSq65LpJxw/tZTYjX7SNATf+O9lJ8wsoUH+ZGuRNXjXEqsui6RcsL5W0+J1ewjQU/8jfdSfsLMli/pQOZJIvkkQS+863Pmb5VYdVcS3bwrQX/CzJYvfqwfyDxJJJ8k6IV3fc78rRKr7kqim3cl6E8Y2eJHPh2W5sy7PdJ9J9+VqHLiXZdYzVyEuXc9F9X8DSNbuoelOfNuj3TfyXclqpx41yVWMxdh7l3PRTV/w8gWHuRHPuVSl/SOXnjXRdKcXnjX51UuvLPKiXdXc9HtnTCylQf60U+51CW9oxfedZE0pxfe9XmVC++scuLd1Vx0eyeMbOWBfvQqT/gb7yXPXFR5mlfwfZJIvpKgF971Of0vGNmeDk95wt94L3nmosrTvILvk0TylQS98K7P6X/B6Hb/ES6R8i5831WXqv92rjzNRXdOkWp+wug2HiqJlHfh+666VP23c+VpLrpzilTzE2a3fXl7qP/IJ4lufiqRcpFywbl8Ell1XIlqvsPMFvD2QL2rJLr5qUTKRcoF5/JJZNVxJar5DjNbgP8IP5R5pUTq7eYk9VKeYF8SlRe7Pc7pJ5jd9sWP94OZV0qk3m5OUi/lCfYlUXmx2+OcfoKRbX6sH5hywTz1SOoppwR9RXpfqcvq7UdiNXMJepHyE0a26TAemHLBPPVI6imnBH1Fel+py+rtR2I1cwl6kfITRrZVh3Ge+qnXlVjNXAnO/c1KhLl3Vznx7tNcJE9NMrKtOozz1E+9rsRq5kpw7m9WIsy9u8qJd5/mInlqktFtq2NdCc5TP/WSEqvuR4JeePdpLrzreQX71fvT+Q4zW77osKQE56mfekmJVfcjQS+8+zQX3vW8gv3q/el8h5EtPEieEvTE33gveeaJ1GOefMqFd060S3pPP8HItnQoJeiJv/Fe8swTqcc8+ZQL75xol/SefoKRbekw5WkuOPc3TyLMUy/RfV/16CvSe+aCuXc9/wUj29Oh/iNWc8G5v3kSYZ56ie77qkdfkd4zF8y96/kvGNnOg6XEqvtRIvWYd0XSnF5UeZKo8i67/QlG/poOpxKr7keJ1GPeFUlzelHlSaLKu+z2Jxj5azxcnnki9ZOnCPNuTyivVFH1fNdKYjX7SNCTar7DyBYeJM88kfrJU4R5tyeUV6qoer5rJbGafSToSTXfYWSLDuJhzLtKVD3OJUFPUp8SyVe5RLrzLrv9DiPbdBgPZN5VoupxLgl6kvqUSL7KJdKdd9ntdxjdpgPfHtp9539j1WfuXc9JNRfsJc88wZ6/9VxUc1HN3zC6zX/Em0O77/xvrPrMves5qeaCveSZJ9jzt56Lai6q+Rtmt4F0cJUnJTj3NyuRVedJovJCOef0IvVONcHMlkA6tMqTEpz7m5XIqvMkUXmhnHN6kXqnmmBmyyGrH7eSoK9I76nEqvuRoCf+xnvdXEqkXspPmNlyCH9YkqCvSO+pxKr7kaAn/sZ73VxKpF7KTxjZwsO6SnDubzwXzL3rEqvZR6Lyopsnn3LhnVUu6H/JyF/RwbtKcO5vPBfMvesSq9lHovKimyefcuGdVS7of8nIX9k9mP3kU0686xKr2UeCnlR9eeYizbu5lKh6KX/DyJbdg9hPPuXEuy6xmn0k6EnVl2cu0rybS4mql/I3jGzhQfKUSD7lovJd0p63SnR7Cb7flaA/YWRLOpASyadcVL5L2vNWiW4vwfe7EvQnjGxJB1Ii+W5O2JMI865PEvTCuy6RfCVBn+j2Ooxs4UHylEi+mxP2JMK865MEvfCuSyRfSdAnur0OI1t4kDwlkk8izL3rEqvZSiLlIs2ZSyTlFdU+zlN+wsgWHuRHukTySYS5d11iNVtJpFykOXOJpLyi2sd5yk8Y2bJ7ULevXpLYzQXzygvlSV3SO3qR8sRuv8PItl/9EPWSxG4umFdeKE/qkt7Ri5QndvsdRrbpsF0JetKdp16apzzR7Qnf7e9S3oXvKj/JyFYduCtBT7rz1EvzlCe6PeG7/V3Ku/Bd5Sf5zdbL/y33g7lscT+Yyxb3g7lscT+Yyxb3g7lscT+Yyxb3g7lscT+Yyxb3g7lscT+Yyxb3g7lscT+Yyxb3g7lscT+Yyxb3g7lscT+YywZ///4PGU0t8ggSmSIAAAAASUVORK5CYII=";
			return CFDI
					.builder()
					.sequentialNumber(cfdiId)
					.cadenaOriginalSAT(cadenaOriginalSat)
					.noCertificadoSAT(noCertificadoSAT)
					.fechaTimbrado(new Date())
					.status(status)
					.selloSAT(selloSAT)
					.selloCFDI(selloCFDI)
					.qrCode(qrCode)
					.purpose("Tax reporting")
					.build();
		}

	}*/

	@ApiOperation(
			value = "Provides mexican government stamp on invoices.",
			response = CFDI.class,
			produces = "application/json",
			consumes = "application/json"
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/tag")
	public ResponseEntity<CFDI> reportInvoiceToGovernment(@RequestBody Invoice invoice) throws IOException {

		LDUser user = getLdUser();

		boolean productionEnabled = ldClient.boolVariation(
				"cfdi-stamping",
				user,
				false
		);

		log.info(String.format("cfdi-stamping flag contains: %b", productionEnabled));
		CFDI result;
		if (productionEnabled) {
			result = cfdiService.stampInvoice(invoice);
		} else {
			result = cfdiService.dummyStampInvoice(invoice);

		}
		//ldClient.close();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", String.format("%s/%s", "/tag/", result.getInvoiceId()));
		return new ResponseEntity<>(result, headers, HttpStatus.ACCEPTED);
	}

	private LDUser getLdUser() {
		return new LDUser.Builder("UNIQUE IDENTIFIER")
				.firstName("Bob")
				.lastName("Loblaw")
				.customString("groups", Arrays.asList("beta_testers"))
				.build();
	}
}
