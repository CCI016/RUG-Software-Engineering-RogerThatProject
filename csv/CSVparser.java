import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.hibernate.*; 

public class CSVParser
{
    private String fileToParse;
    private long id;

    private String date;
    private String accNumFrom;
    private String accNumTo;
    private long amount;

    public CSVParser()
    {
        fileToParse = "test.csv";
    }

    public static void main(String[] args)
    {
        BufferedReader fileReader = null;
        CSVParser parser = new CSVParser();
        Thread thread = new Thread();

        String jdbcURL = "jdbc:mysql://127.0.0.1:3306/rogerthat";
        String username = "root";
        String password = "rogerthat";

        Connection connection = null;
        
        try
        {
            thread.start();
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(parser.fileToParse));
            
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
            
            String sql = "INSERT INTO transactions (date, name, category, spending_classification, income_classification, amount) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

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

                statement.setString(1, date);
                statement.setString(2, name);
                statement.setString(3, transactionCategory);
                statement.setString(4, spendingClassification);
                statement.setString(5, incomeClassification);
                statement.setDouble(6, amount);

                int i = statement.executeUpdate();
            }
            
            // TODO: Check if the file exists already in the database, by means of timestamps
            
            fileReader.close();
            
            connection.commit();
            connection.close();
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