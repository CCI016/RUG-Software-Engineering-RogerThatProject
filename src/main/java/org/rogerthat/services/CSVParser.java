package org.rogerthat.services;

import java.io.BufferedReader;
import java.io.FileReader;
import org.rogerthat.orm.Transactions;

import org.eclipse.microprofile.config.inject.ConfigProperty;

public class CSVParser  {

    public CSVParser() {
    }

    public void parse(String fileToParse, Long userID) {
        BufferedReader fileReader = null;
        // Thread thread = new Thread()


        userID = 1L;

        try {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));

            // Line counter for testing
            int count = 0;

            //Read the file line by line
            while ((line = fileReader.readLine()) != null) {
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
                //transaction.persist();

                // Testing if the parser works
                System.out.println("Transaction" + count + ": dateTime = " + transaction.dateTime);
                System.out.println("Transaction" + count + ": name = " + transaction.name);
                System.out.println("Transaction" + count + ": accountFrom = " + transaction.accountFrom);
                System.out.println("Transaction" + count + ": accountTo = " + transaction.accountTo);
                System.out.println("Transaction" + count + ": code = " + transaction.code);
                System.out.println("Transaction" + count + ": inOrOut = " + transaction.inOrOut);
                System.out.println("Transaction" + count + ": amount = " + transaction.amount);
                System.out.println("Transaction" + count + ": transactionType = " + transaction.transactionType);
                System.out.println("Transaction" + count + ": notes = " + transaction.notes);
                System.out.println("\n");


                // Increment line counter
                count += 1;
            }
            fileReader.close();
            // TODO: Check if the file exists already in the database, by means of timestamps and userId
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}