package org.rogerthat.services;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import org.rogerthat.orm.IntervalOverview;
import org.rogerthat.orm.Person;
import org.rogerthat.orm.TransactionCategory;
import org.rogerthat.orm.Transactions;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class PDFGenerator {

    public int generatePDF(Long personID) {

        PdfWriter writer = null;
        Document document = new Document();
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream("overview.pdf"));
            document.open();

        } catch (Exception e) {
            System.out.println("Could not create pdf file");
        }

        try {
            document.add(new Paragraph("Roger That: Financial Overview"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Query for person from Analyzer
        Person person = Person.findById(personID);
        List<IntervalOverview> intervalOverviews = IntervalOverview.listAll();
        IntervalOverview interval = intervalOverviews.stream().filter(io -> io.person ==
                person).findFirst().orElse(null);

        // Create a table for interval overview and add it to the document

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(90);
        addTableHeader(table);
        PdfPCell cell = new PdfPCell();
        // Add the values for each month
        String months[] = {interval.month_0, interval.month_1, interval.month_2, interval.month_3, interval.month_4, interval.month_5};
        for (String month : months) {
            List<String> parse = Arrays.asList(month.split(":"));
            String strDouble = String.format("%.2f", Float.parseFloat(parse.get(1)));
            cell = new PdfPCell(new Phrase(strDouble));
            if(Float.parseFloat(parse.get(1)) < 0) {
                cell.setBackgroundColor(BaseColor.RED);
            } else {
                cell.setBackgroundColor(BaseColor.GREEN);
            }
            table.addCell(cell);
        }

        // Add first table
        try {
            document.add(new Paragraph(" "));
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Withdarals and incasso sum table
        PdfPTable table1 = new PdfPTable(2);
        addTable1Header(table1);


        cell = new PdfPCell(new Phrase(interval.withdrawalsSum));
        table1.addCell(cell);
        cell = new PdfPCell(new Phrase(interval.incassoSum));
        table1.addCell(cell);

        // Add second table
        try {
            document.add(new Paragraph(" "));
            document.add(table1);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Spending Category - Percentage table
        PdfPTable table2 = new PdfPTable(2);
        addTable2Header(table2);

        // List of all transactions of the person
        List<Transactions> transactions = person.transactions;

        List<Transactions> spendingTransactions = new ArrayList<>();
        for (Transactions transaction : transactions) {
            if(transaction.transactionCategory == TransactionCategory.SPENDING) {
                spendingTransactions.add(transaction);
            }
        }


        int totalNumOfTransactions = spendingTransactions.size();
        double entertainmentTransactions = 0.0, housingTransactions = 0.0, groceriesTransactions = 0.0,
                eatingOutTransactions = 0.0, clothesTransactions = 0.0, unknownTransactions = 0.0, totalSpendings = 0.0;

        for (Transactions transaction : spendingTransactions) {
            switch (transaction.spendingCategory) {
                case CLOTHES:
                    clothesTransactions = clothesTransactions + Double.parseDouble(transaction.amount);
                case HOUSING:
                    housingTransactions = housingTransactions + Double.parseDouble(transaction.amount);
                case GROCERIES:
                    groceriesTransactions = groceriesTransactions + Double.parseDouble(transaction.amount);
                case EATING_OUT:
                    eatingOutTransactions = eatingOutTransactions + Double.parseDouble(transaction.amount);
                case ENTERTAINMENT:
                    entertainmentTransactions = entertainmentTransactions + Double.parseDouble(transaction.amount);
                default:
                    if(!(transaction.transactionType.equals("\"Betaalautomaat\"") && transaction.accountTo.equals("\"\""))){
                        unknownTransactions = unknownTransactions + Double.parseDouble(transaction.amount
                                .replace(",", "."));
                    }
            }
        }
        // Calculate total spendings
        totalSpendings = clothesTransactions + housingTransactions + groceriesTransactions + eatingOutTransactions +
                entertainmentTransactions + unknownTransactions;

        // Populate the table with spending categories and their percentages
        cell = new PdfPCell(new Phrase("Clothes"));
        table2.addCell(cell);

        double clothesPercentage = (clothesTransactions / totalSpendings) * 100;
        cell = new PdfPCell(new Phrase(String.valueOf(clothesPercentage) + "%"));
        table2.addCell(cell);

        cell = new PdfPCell(new Phrase("Housing"));
        table2.addCell(cell);

        double housingPercentage = (housingTransactions / totalSpendings) * 100;
        cell = new PdfPCell(new Phrase(String.valueOf(housingPercentage) + "%"));
        table2.addCell(cell);

        cell = new PdfPCell(new Phrase("Groceries"));
        table2.addCell(cell);

        double groceriesPercentage = (groceriesTransactions / totalSpendings) * 100;
        cell = new PdfPCell(new Phrase(String.valueOf(groceriesPercentage) + "%"));
        table2.addCell(cell);

        cell = new PdfPCell(new Phrase("Eating out"));
        table2.addCell(cell);

        double eatingOutPercentage = (eatingOutTransactions / totalSpendings) * 100;
        cell = new PdfPCell(new Phrase(String.valueOf(eatingOutPercentage) + "%"));
        table2.addCell(cell);

        cell = new PdfPCell(new Phrase("Entertainment"));
        table2.addCell(cell);

        double entertainmentPercentage = (entertainmentTransactions / totalSpendings) * 100;
        cell = new PdfPCell(new Phrase(String.valueOf(entertainmentPercentage) + "%"));
        table2.addCell(cell);

        cell = new PdfPCell(new Phrase("Unknown"));
        table2.addCell(cell);

        double unknownPercentage = (unknownTransactions / totalSpendings) * 100;
        cell = new PdfPCell(new Phrase(String.valueOf(unknownPercentage) + "%"));
        table2.addCell(cell);

        // Add third table
        try {
            document.add(new Paragraph(" "));
            document.add(table2);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();
        writer.close();
        return 0;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Month 6", "Month 5", "Month 4", "Month 3", "Month 2", "Month 1")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addTable1Header(PdfPTable table) {
        Stream.of("Withdrawals", "Incasso")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addTable2Header(PdfPTable table) {
        Stream.of("Category", "Percentage")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
}
