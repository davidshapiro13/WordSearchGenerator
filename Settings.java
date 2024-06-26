import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
/**
 * Controls settings for wring word searches
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Settings
{
    String author;
    String filePath;
    boolean hideWordBank;
    boolean openWordSearch;
    
    
    String settingFile = "settings.txt";

    public Settings(String author, boolean hideWordBank, 
                    String filePath, boolean openWordSearch)
    {
        this.author = author;
        this.hideWordBank = hideWordBank;
        this.filePath = filePath;
        this.openWordSearch = openWordSearch;
    }

    /**
     * Saves all settings to settings file
     */
    public void save()
    {
        try 
        {            
            settingFile = "settings.txt";

            PrintWriter writer = new PrintWriter(settingFile);
            
            //Write settings
            writer.println(author);
            writer.println(hideWordBank);
            writer.println(filePath);
            writer.println(openWordSearch);

            writer.close();
        }
        catch(Exception e){};
    }

    /**
     * Gets all settings from settings file
     * @return string of setting values
     */
    public static String[] retrieve()
    {

        try 
        {   
            String settingFile = "settings.txt";
            File file       = new File(settingFile);
            Scanner scanner = new Scanner(file);

            //Get settings
            String author = scanner.nextLine();
            String hideWordBank = scanner.nextLine();
            String filePath = scanner.nextLine();
            String openWordSearch = scanner.nextLine();
            scanner.close();
            
            String[] setup = {author, hideWordBank, filePath, openWordSearch};
            return setup;
        }
    catch(Exception e)
    {
        String[] setup = {"Word Search-LE", "false", 
                           System.getProperty("user.home") + "/Desktop/",
                           "true"}; //DEFAULT
        return setup;
    }
}
}
