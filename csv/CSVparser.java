import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.hibernate.*; 

public class CSVParser
{
    @ConfigProperty(name = "csv.directory")
	String csvDirectory;

    public CSVParser() {
    }

    public void parse(String fileToParse, Long userID) {
        BufferedReader fileReader = null;
        // Thread thread = new Thread();

        userID = 1;
        
        try {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(csvDirectory + fileToParse));

            //Read the file line by line
            while ((line = fileReader.readLine()) != null) 
            {
                //Get all tokens available in line
                String[] tokens = line.split(",");

                // Get paramaters of a transaction from token list
                String date = tokens[0];
                String name = tokens[1];
                String transactionCategory = tokens[2];
                String spendingClassification = tokens[3];
                String incomeClassification = tokens[4];
                double amount = tokens[5];
            
                Transaction transactions = new Transaction();
                transactions.name = name;
                transactions.date = date;
                
                transactions.persist();
            }
            
            // TODO: Check if the file exists already in the database, by means of timestamps
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}