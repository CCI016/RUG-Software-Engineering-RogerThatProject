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
            

            // TODO: Finish Query
            String sql = "INSERT INTO ...";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            // In case the header line exists
            // line = fileReader.readLine()


            //Read the file line by line
            while ((line = fileReader.readLine()) != null) 
            {
                //Get all tokens available in line
                String[] tokens = line.split(",");
                
                // TODO: Add the parameters to the statement
                // Get paramaters of a transaction from token list


                statement.addBatch();
                statement.executeBatch();
            }
            
            // Check if the file exists already in the database, by means of timestamps
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