package com.jobportal.ai;


import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ResumeTextExtractorService {

    public String extractText(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Resume file cannot be empty"
            );
        }

        if (!"application/pdf".equalsIgnoreCase(
                file.getContentType())) {
            throw new IllegalArgumentException(
                    "Only PDF files are allowed"
            );
        }

        try (PDDocument document =
                     Loader.loadPDF(file.getBytes())) {

            PDFTextStripper pdfTextStripper =
                    new PDFTextStripper();

            String extractedText =
                    pdfTextStripper.getText(document);

            if (extractedText == null ||
                    extractedText.isBlank()) {
                throw new IllegalArgumentException(
                        "No readable text found in the PDF"
                );
            }

            return extractedText.trim();

        } catch (IOException exception) {

            throw new IllegalArgumentException(
                    "Unable to read the uploaded PDF",
                    exception
            );
        }
    }
}

