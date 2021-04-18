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

                // Create an instance of a transaction
                Transaction transaction = new Transaction();

                // NOTE: These values are not going to work with SNS bank's CSV files
                // This would only work with ING transactions CSV viles
                // Pass all values to the transaction instance
                transaction.dateTime = tokens[0];
                transaction.name = tokens[1];
                transaction.accountFrom = tokens[2];
                transactions.accountTo = tokens[3];
                transaction.code = tokens[4];
                transaction.inOrOut = tokens [5];
                transactions.amount = tokens[6];
                transactions.transactionType = tokens[7];
                transaction.notes = tokens [8];

                // Persist the instance with added values
                transaction.persist();
            }
            
            // TODO: Check if the file exists already in the database, by means of timestamps and userId
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