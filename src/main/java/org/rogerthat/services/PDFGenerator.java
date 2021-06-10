package org.rogerthat.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.text.Document;
import java.awt.*;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDFGenerator {

    public void pdfGen() {
        // Adding RogerThat logo to the document
        Path path = Paths.get(ClassLoader.getSystemResource("roger_that_logo.png").toURI());

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("overview.pdf"));
        document.open();
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        document.add(img);
        // Add query to pull interval overview
        // Make a PDFtable and add it to the document

        document.close();
        // Send document to the front end
    }

}
