package org.softwire.training.models;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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

        String text = "text";
        contentStream.showText(text);
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




//        Calendar calendar = new GregorianCalendar();
//        Calendar  = calendar.getTime();
//        date.set(now);
//
//        LocalDate now = LocalDate.now();
//
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        Calendar cal = Calendar.getInstance();
//        pdd.setCreationDate(cal.getTime());

//        setModificationDate(Calendar date);
//        setKeywords(String keywords list);

//        AccessPermission accessPermission = new AccessPermission();
//        StandardProtectionPolicy spp = new StandardProtectionPolicy("1234", "1234", accessPermission);
//        spp.setEncryptionKeyLength(128);
//        spp.setPermissions(ap);
//
//        document.protect(spp);

//        File file = new File("path of the document");
//        PDDocument.load(file);

//    int noOfPages = document.getNumberOfPages();
//    System.out.print("noOfPages");
//    PDImageXObject pdImage = PDImageXObject.createFromFile("C://");
//    PDPageContentStream contents = new PDPageContentStream(doc, page);
//    contents.drawImage(pdImage, 70, 250);
//    contents.close();
