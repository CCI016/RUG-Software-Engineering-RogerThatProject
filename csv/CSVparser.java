import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.hibernate.*; 

public class CSVParser
{
    String fileToParse;
    long id;

    public CSVParser()
    {
        fileToParse = "test.csv";
    }

    public static void main(String[] args)
    {
        BufferedReader fileReader = null;
        CSVParser parser = new CSVParser();
        Thread thread = new Thread();
        
        try
        {
            thread.start();
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(parser.fileToParse));
            Connection con = null;
             
            //Read the file line by line
            while ((line = fileReader.readLine()) != null) 
            {
                //Get all tokens available in line
                String[] tokens = line.split(",");
                for(String token : tokens)
                {
                    //Print all tokens
                    System.out.println(thread.getId() + token);
                }
            }
            // Check if the file exists already in the database, by using special id
            
            // Open connection with the database and commit the tokens list into it
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