package org.rogerthat.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.persistence.Table;
import javax.swing.text.Document;
import java.awt.*;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDFGenerator {

    private void uploadPDF(Document document) {
        // POST the generated document
    }

    public void generatePDF(Long personID) {
        // Adding RogerThat logo to the document
        Path path = Paths.get(ClassLoader.getSystemResource("roger_that_logo.png").toURI());

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("overview.pdf"));
        document.open();
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        document.add(img);

        // Query for person from Analyzer


        // Create a table for monthly overview and add it to the document

        // Depending on the amount of columns and their widths initialise the values
        //float columnWidth[] = {};
        //Table table = new Table(columnWidth);

        // Do the following columnWidth.length times
        // table.addCell(new Cell().add("Name of the column n").setBackgroundColor(Color.GRAY));

        document.close();
        uploadPDF(document);
    }

}
