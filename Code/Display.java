import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;

/**
 * Uses JOptionPane to put out and recieve information from users
 *
 * @author David Shapiro
 * @version 1.0
 **/
public class Display
{
    String instructions;

    String title = "Puzzle Maker";
    String searchName = "My Search";
    String ext = ".pdf";

    final int CHAR_LIMIT = 40;
    final int MAX_WORD_SIZE = 10;

    Utility util = new Utility();
    UIManager um = new UIManager();
    Settings setting;

    Font font1 = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    Font font2 = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    Font font3 = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

    /**
     * Welcomes User with option to start, exit, or learn more
     * @return index of puzzle chocie
     **/
    public int start()
    {
        um.put("OptionPane.messageFont", font1); //set font

        instructions = "Welcome to Word Search-Le! Let's Begin!";
        String[] options = {"Start Project", "About", "Settings", "Exit"};

        ImageIcon img1 = new ImageIcon(Display.class.getResource("mainPage.jpg"));

        int decision = JOptionPane.showOptionDialog(null,
                instructions,       //text that appears in window
                title,              //text that appears in titlebar
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,      //sets icon type
                img1,               //user icon
                options,            //list of options to appear on buttons
                options[0]);        //Default
        return decision;

    }

    /**
     * About section: tell user about project
     */
    public void about()
    {
        um.put("OptionPane.messageFont", font3); //set font

        instructions = "This is a Word Search Creator! \n" + 
        "First, You must write up to 15 words. \n" +
        "Once that is complete, name your word search, save it, and Done! \n" +
        "A PDF of your Word Search along with an answer key will be made! \n" +
        "It will be then be put your selected folder \n" +
        "For adjustments, visit the settings page! \n"  +
        "Now, go out and CREATE!";

        JOptionPane.showMessageDialog(null,
            instructions,    //text that appears in the window
            title,           //text that appears in the title bar
            JOptionPane.PLAIN_MESSAGE); //see below

    }

    /**
     * Receives words from user
     * @return words user inputed
     */
    public String[] getWords(int numWords)
    {
        final int WORD_AMOUNT = numWords; //Max words needed for crossword

        boolean wrongSize = true;
        boolean inRange; //Not too short or long

        boolean[] doesFit = new boolean[WORD_AMOUNT];

        String message = "Enter words for your search:";

        String[] words = new String[WORD_AMOUNT]; //All words;

        JTextField[] textBoxes = new JTextField[WORD_AMOUNT]; //All text boxes

        //Create all text boxes
        for (int i = 0; i < textBoxes.length; i ++)
            textBoxes[i] = new JTextField("", 10);

        while (wrongSize)
        {
            JPanel wordInput = new JPanel(); //Panel

            //Vertical list of words
            wordInput.setLayout(new BoxLayout(wordInput, BoxLayout.PAGE_AXIS));

            //Directions
            JLabel label = new JLabel(message);
            label.setFont(font3);
            wordInput.add(label);

            //Add text boxes to display
            for (JTextField entry : textBoxes)
                wordInput.add(entry);

            //Run display
            JOptionPane.showConfirmDialog(null, wordInput, 
                title, JOptionPane.OK_CANCEL_OPTION);

            //Save words
            for (int i = 0; i < textBoxes.length; i ++)
            {
                words[i] = textBoxes[i].getText();
                inRange = (words[i].length() > 2 &&
                    words[i].length() <= MAX_WORD_SIZE) ||
                words[i].length() == 0;
                doesFit[i] = inRange;
            }

            if (util.noFalse(doesFit))
                wrongSize = false;
            else
            {
                um.put("OptionPane.messageForeground", Color.red);//text COLOR
                message = "All words must have between 3 and 10 CHARs:";  
            }
        }
        um.put("OptionPane.messageForeground", Color.black); //reset
        return words;
    }

    /**
     * receives name of title
     * @return title of word search
     */
    public String getTitle()
    {
        um.put("OptionPane.messageFont", font3); //set font

        boolean tooBig = true;
        String message = "What would you like your " +
                         "Word Search title to be?";
        
        while (tooBig)
        {
            searchName = JOptionPane.showInputDialog(message);
            
            if (searchName.length() > CHAR_LIMIT)
            {
                um.put("OptionPane.messageForeground", Color.red);
                message = "That title is too long. Please try again. " +
                CHAR_LIMIT + " char limit";
            }
            else
                tooBig = false;
        }
        
        um.put("OptionPane.messageForeground", Color.black); //reset
        return searchName;                                           
    }

    /**
     * Gets file name from user
     * @return file name with extension
     */
    public String getFileName()
    {
        JPanel nameInput = new JPanel(); //Panel
        String fullFileName;
        
        if (searchName.length() == 0)
            fullFileName = "myProject" + ext;
        else
            fullFileName = searchName + ext;

        JTextField fileInput = new JTextField(fullFileName, 10);
        JLabel label = new JLabel("File Name: ");
        label.setFont(font3);
        nameInput.add(label);
        nameInput.add(fileInput);

        JOptionPane.showConfirmDialog(null, nameInput, 
            title, JOptionPane.OK_CANCEL_OPTION);

        return util.format(fileInput.getText(), ext);                                           
    }

    /**
     * shows settings panel with all settings to change
     * @return Settings for word search
     */
    public Settings settings()
    {
        Boolean tooBig = true;
        String message = "Author Name";
        String[] amounts = {"6", "10", "12","15"};
        
        ImageIcon img1 = new ImageIcon(Display.class.getResource("settings.jpg"));
        JLabel imageLabel = new JLabel(img1);
        
        while (tooBig)
        {

            String[] setup = Settings.retrieve();
            JPanel settingsInput = new JPanel();
            settingsInput.setLayout(new BoxLayout(settingsInput, BoxLayout.PAGE_AXIS));

            JLabel space = new JLabel("     ");
            space.setFont(font2);

            //hide word bank setup
            JCheckBox hideWordBankBox = new JCheckBox("Hide Word Bank");
            if (Boolean.parseBoolean(setup[1]))
                hideWordBankBox.setSelected(true);
            else
                hideWordBankBox.setSelected(false);

            //Open PDF setup
            JCheckBox openWordSearchBox = new JCheckBox("Automatically Open PDF");
            if (Boolean.parseBoolean(setup[3]))
                openWordSearchBox.setSelected(true);
            else
                openWordSearchBox.setSelected(false);

            //Author setup
            JLabel label1 = new JLabel(message);
            label1.setFont(font2);
            JTextField author = new JTextField(setup[0], 10);

            //Location setup
            JLabel label2 = new JLabel("Set saving location");
            label2.setFont(font2);
            JTextField locationBox = new JTextField(setup[2], 10);

            //Word Bank Display
            settingsInput.add(hideWordBankBox);
            settingsInput.add(space);

            //Open Word Search
            settingsInput.add(openWordSearchBox);

            //Author Display
            settingsInput.add(label1);
            settingsInput.add(author);

            //Location Display
            settingsInput.add(label2);
            settingsInput.add(locationBox);
            settingsInput.add(imageLabel);
            JOptionPane.showConfirmDialog(null, settingsInput, 
                title, JOptionPane.OK_CANCEL_OPTION);

            //Update settings
            setting = new Settings(author.getText(), hideWordBankBox.isSelected(),
                locationBox.getText(), openWordSearchBox.isSelected());

            if (author.getText().length() < 25)
                tooBig = false;
            else
            {
                um.put("OptionPane.messageForeground", Color.red);
                message = "Author name is too big. (25 char max)";
            }
            
        }
        
        um.put("OptionPane.messageForeground", Color.black); //reset
        setting.save();
        return setting;
    }

    /**
     * Recieves input from user about how tough puzzle should be
     * @return level of game
     */
    public int getLevel()
    {
        um.put("OptionPane.messageFont", font3); //set font
        instructions = "How challenging would you like the puzzle to be?";
        String[] options = {"Ultra Tough", "Hard", "Moderate", "Easy"};
        
        int level = JOptionPane.showOptionDialog(null,
                instructions,       //text that appears in window
                title,          //text that appears in titlebar
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,      //sets icon type
                null,         //user icon
                options,     //list of options to appear on buttons
                options[3]);                    //Default
        
        return level;

    }

    /**
     * Display that puzzle is finished
     * @return decision on how to proceed
     */
    public int finish(String filePath)
    {
        um.put("OptionPane.messageFont", font1); //set font

        instructions = "Congratulations! Your PDF has been made! \n" +
                       "You can find it at \n" + filePath;

        ImageIcon img1 = new ImageIcon(Display.class.getResource("end.jpg"));

        String[] options = {"Make Another", "Exit"};
        
        int decision = JOptionPane.showOptionDialog(null,
                instructions,       //text that appears in window
                title,          //text that appears in titlebar
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,      //sets icon type
                img1,               //user icon
                options,            //list of options to appear on buttons
                options[1]);        //Default
                
        return decision;
    }

    /**
     * Displays if an error occurred
     * @return error that occurred
     */
    public void showError(String message)
    {
        um.put("OptionPane.messageFont", font1); //set font
        
        JOptionPane.showMessageDialog(null,
            message,    //text that appears in the window
            title,           //text that appears in the title bar
            JOptionPane.PLAIN_MESSAGE); //see below 
    }

}