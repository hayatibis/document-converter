package com.hayatibis.document;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class DocumentConversionService {

    public byte[] convertDocxToPdf(byte[] docxBytes) throws IOException {
        try (ByteArrayInputStream docxInputStream = new ByteArrayInputStream(docxBytes);
             ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {
            XWPFDocument doc = new XWPFDocument(docxInputStream);
            PDDocument pdfDoc = new PDDocument();

            PDPageTree pdfPageTree = pdfDoc.getPages();
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                PDPage pdfPage = new PDPage();
                pdfPage.setMediaBox(PDRectangle.A4);
                pdfPageTree.add(pdfPage);
                try (PDPageContentStream contentStream = new PDPageContentStream(pdfDoc, pdfPage)) {
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 700); // Adjust coordinates as needed
                    contentStream.showText(paragraph.getText());
                    contentStream.endText();
                }
            }

            pdfDoc.save(pdfOutputStream);
            pdfDoc.close();
            return pdfOutputStream.toByteArray();
        }
    }

    public byte[] convertPptToPdf(byte[] pptBytes) throws IOException {
        try (ByteArrayInputStream pptInputStream = new ByteArrayInputStream(pptBytes);
             ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {
            XMLSlideShow ppt = new XMLSlideShow(pptInputStream);
            PDDocument pdfDoc = new PDDocument();

            PDPageTree pdfPageTree = pdfDoc.getPages();
            int slideNumber = 1;
            for (XSLFSlide slide : ppt.getSlides()) {
                PDPage pdfPage = new PDPage();
                pdfPage.setMediaBox(PDRectangle.A4);
                pdfPageTree.add(pdfPage);
                try (PDPageContentStream contentStream = new PDPageContentStream(pdfDoc, pdfPage)) {
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 700); // Adjust coordinates as needed
                    contentStream.showText("Slide " + slideNumber);
                    contentStream.endText();
                }
                slideNumber++;
            }

            pdfDoc.save(pdfOutputStream);
            pdfDoc.close();
            return pdfOutputStream.toByteArray();
        }
    }
}
