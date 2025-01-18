package org.alicansadeler.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PdfReportUtils {
    private static final List<TestResult> testResults = new ArrayList<>();

    private PdfReportUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void addTestResult(String testName, String result, String responseCode) {
        testResults.add(new TestResult(testName, result, responseCode));
    }

    public static void generateFinalReport() {
        try {
            Document document = new Document();
            String fileName = "API_Test_Report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();

            // Başlık
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("PetStore API Test Raporu", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Test zamanı
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph dateTime = new Paragraph("Test Tarihi: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), normalFont);
            document.add(dateTime);
            document.add(Chunk.NEWLINE);

            // Tablo
            PdfPTable table = new PdfPTable(4); // Test No, Test Adı, Sonuç, Response Code
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 3, 2, 2});

            addTableHeader(table);

            // Test sonuçlarını ekle
            int testNo = 1;
            for (TestResult result : testResults) {
                addTableRow(table, String.valueOf(testNo++), result);
            }

            document.add(table);

            // Özet bilgiler
            document.add(Chunk.NEWLINE);
            addSummary(document);

            document.close();
            log.info("PDF rapor oluşturuldu: " + fileName);

        } catch (Exception e) {
            log.error("PDF rapor oluşturulurken hata oluştu: " + e.getMessage());
        }
    }

    private static void addTableHeader(PdfPTable table) {
        String[] headers = {"No", "Test Adı", "Sonuç", "Response Code"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }
    }

    private static void addTableRow(PdfPTable table, String testNo, TestResult result) {
        table.addCell(testNo);
        table.addCell(result.testName);

        PdfPCell resultCell = new PdfPCell(new Phrase(result.result));
        resultCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        if ("PASSED".equals(result.result)) {
            resultCell.setBackgroundColor(new BaseColor(144, 238, 144)); // Light green
        } else {
            resultCell.setBackgroundColor(new BaseColor(255, 182, 193)); // Light red
        }
        table.addCell(resultCell);

        table.addCell(result.responseCode);
    }

    private static void addSummary(Document document) throws DocumentException {
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

        long passCount = testResults.stream().filter(r -> "PASSED".equals(r.result)).count();
        long failCount = testResults.size() - passCount;

        document.add(new Paragraph("Test Özeti:", boldFont));
        document.add(new Paragraph("Toplam Test Sayısı: " + testResults.size()));
        document.add(new Paragraph("Başarılı Test Sayısı: " + passCount));
        document.add(new Paragraph("Başarısız Test Sayısı: " + failCount));
        document.add(new Paragraph("Başarı Oranı: %" +
                String.format("%.2f", (double) passCount / testResults.size() * 100)));
    }

    private static class TestResult {
        private final String testName;
        private final String result;
        private final String responseCode;

        TestResult(String testName, String result, String responseCode) {
            this.testName = testName;
            this.result = result;
            this.responseCode = responseCode;
        }
    }
}
