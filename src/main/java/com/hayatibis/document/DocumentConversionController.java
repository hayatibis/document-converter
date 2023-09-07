package com.hayatibis.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/converter")
public class DocumentConversionController {

    @Autowired
    private DocumentConversionService conversionService;

    @PostMapping(value = "/docx-to-pdf", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] convertDocxToPdf(@RequestBody byte[] docxBytes) throws IOException {
        return conversionService.convertDocxToPdf(docxBytes);
    }

    @PostMapping(value = "/ppt-to-pdf", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] convertPptToPdf(@RequestBody byte[] pptBytes) throws IOException {
        return conversionService.convertPptToPdf(pptBytes);
    }
}
