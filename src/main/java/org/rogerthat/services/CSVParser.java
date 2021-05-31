package org.rogerthat.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.rogerthat.orm.*;

import javax.transaction.Transaction;
import javax.transaction.Transactional;

public class CSVParser  {

    List<Transactions> transactions;

    public CSVParser() {
        this.transactions = new ArrayList<>();
    }

    @Transactional
    public void parse(String fileToParse, Long userID) {
        List<Transactions> newTransactions = new ArrayList<>();

        BufferedReader fileReader = null;

        userID = 1L;

        User user = User.findById(userID);

        if (user == null) {
            return;
        }

        Person person = user.person;
        this.transactions = person.transactions;

        try {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));

            // Line counter for testing
            int count = 0;

            //Read the file line by line
            while ((line = fileReader.readLine()) != null) {
                if (count != 0) {

                    //Get all tokens available in line
                    String[] tokens = line.split(",");

                    // Create an instance of a transaction
                    Transactions transaction = new Transactions();

                    // NOTE: These values are not going to work with SNS bank's CSV files
                    // This would only work with ING transactions CSV viles
                    // Pass all values to the transaction instance
                    transaction.dateTime = tokens[0];
                    transaction.name = tokens[1];
                    transaction.accountFrom = tokens[2];
                    transaction.accountTo = tokens[3];
                    transaction.code = tokens[4];
                    transaction.inOrOut = tokens[5];
                    transaction.amount = tokens[6];
                    transaction.transactionType = tokens[7];
                    transaction.notes = tokens[8];

                    // Persist the instance with added values
                    newTransactions.add(transaction);

                    // Testing if the parser works

                    // Increment line counter
                }
                count += 1;
            }
            fileReader.close();

            checkForDuplicates(this.transactions, newTransactions, person);
            // TODO: Check if the file exists already in the database, by means of timestamps and userId
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param oldTransactions The list of previous transactions of this user
     * @param newTransactions The list of incoming transactions for this user
     */
    @Transactional
    public void checkForDuplicates(List<Transactions> oldTransactions, List<Transactions> newTransactions, Person person) {
        System.out.println(TransactionCategory.INCOME);
        System.out.println(TransactionCategory.SPENDING);

        if (!oldTransactions.isEmpty()) { // if there are transactions we will check the duplicates by the time stamps.
            List<String> oldTimeStamps = new ArrayList<>();

            for (Transactions transaction : oldTransactions) {
                oldTimeStamps.add(transaction.dateTime);
            }

            for (Transactions transaction : newTransactions) {
                if (!oldTimeStamps.contains(transaction.dateTime)) {
                    transaction = classifyTransaction(transaction);
                    person.transactions.add(transaction);

                    transaction.persist();
                    System.out.println(transaction.transactionCategory);

                }
            }
        } else {
            for (Transactions transaction : newTransactions) {
                transaction = classifyTransaction(transaction);
                person.transactions.add(transaction);
                System.out.println(transaction.transactionCategory);
                transaction.persist();
            }
        }
        Analyzer analyzer = new Analyzer(person.id, person);
        analyzer.analyze();


    }

    private Transactions classifyTransaction(Transactions transaction) {
        if (transaction.inOrOut.contains("\"Af\"")) {
            transaction.transactionCategory = TransactionCategory.SPENDING;
            categorizeTransactions(transaction);
        } else {
            transaction.transactionCategory = TransactionCategory.INCOME;
        }

        return transaction;
    }

    private Transactions categorizeTransactions(Transactions transaction) {
        List<SpendingClassification> spendingClassifications = new ArrayList<>();

        if (transaction.transactionCategory == TransactionCategory.SPENDING) {
            spendingClassifications = SpendingClassification.listAll();

            for (SpendingClassification spendingClassification : spendingClassifications) {
                if (transaction.notes.toLowerCase().contains(spendingClassification.wordAssociated.toLowerCase())) {
                    transaction.spendingCategory = spendingClassification.category;
                }
            }
        }
        return transaction;
    }
}