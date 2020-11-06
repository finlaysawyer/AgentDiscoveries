package org.softwire.training.models;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import rst.pdfbox.layout.elements.Paragraph;

import java.io.File;
import java.io.IOException;

public class PDFCreator {
    public static void main(String args[]) {
        try {
            createDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDocument() throws IOException {
        TableCreator tableCreator = new TableCreator();

        //create document
        PDDocument document = new PDDocument();

        //assign information
        PDDocumentInformation pdd = document.getDocumentInformation();

        String author = "will";
        String title = "test";
        String creator = "test";
        String subject = "test";

        pdd.setAuthor(author);
        pdd.setTitle(title);
        pdd.setCreator(creator);
        pdd.setSubject(subject);

        //create page
        PDPage page = new PDPage();
        document.addPage(page);

        //enter information on page 1
        PDPage page_1 = document.getPage(0);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        document.addPage(page_1);


        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(25, 500);

        Paragraph paragraph = new Paragraph();
        paragraph.add(tableCreator.tableCreator());


        contentStream.newLine();
        String text2 = "test";
        contentStream.showText(text2);

        contentStream.endText();
        contentStream.close();

        document.save(new File("C:\\Users\\willr\\downloads\\summary_report.pdf"));

        System.out.println("PDF created");
        document.close();
    }
}
