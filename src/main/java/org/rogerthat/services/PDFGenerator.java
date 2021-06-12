package org.rogerthat.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.rogerthat.orm.IntervalOverview;
import org.rogerthat.orm.Person;

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
        Person person = Person.findById(personID).stream().findFirst().orElse(null);
        IntervalOverview interval = IntervalOverview.findById("person = 1?", person).findFirst();

        // Create a table for interval overview and add it to the document

        // Depending on the amount of columns and their widths initialise the values
        float columnWidth[] = {50f, 50f, 50f, 50f, 50f, 50f};
        Table table = new Table(columnWidth);

        // Do the following columnWidth.length times
        table.addCell(new Cell().add("Month 1").setBackgroundColor(Color.GRAY));
        table.addCell(new Cell().add("Month 2").setBackgroundColor(Color.GRAY));
        table.addCell(new Cell().add("Month 3").setBackgroundColor(Color.GRAY));
        table.addCell(new Cell().add("Month 4").setBackgroundColor(Color.GRAY));
        table.addCell(new Cell().add("Month 5").setBackgroundColor(Color.GRAY));
        table.addCell(new Cell().add("Month 6").setBackgroundColor(Color.GRAY));

        // Add the values for each month
        String months[] = {interval.month_0, interval.month_1, interval.month_2, interval.month_3, interval.month_4};

        for (String month : months) {
            if(Float.parseFloat(month) < 0) {
                table.addCell(new Cell().add(month).setBackGroundColor(Color.RED));
            } else {
                table.addCell(new Cell().add(month).setBackGroundColor(Color.GREEN));
            }
        }
        document.add(table);

        float columnWidth1[] = {50f, 50f};
        Table table1 = new Table(columnWidth1);

        table1.addCell(new Cell().add("Withdrawals").setBackgroundColor(Color.GRAY));
        table1.addCell(new Cell().add("Incasso").setBackgroundColor(Color.GRAY));
        table1.addCell(new Cell().add(interval.withdrawalsSum));
        table1.addCell(new Cell().add(interval.incassoSum));

        document.add(table1);

        document.close();
        uploadPDF(document);
    }

}
