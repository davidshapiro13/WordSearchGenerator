
/**
 * Controls displays, creation of Word Search, and PDF writing
 * 
 * @author David Shapiro
 * @version 1.0
 */
public class PuzzleRunner
{
    public static void main (String [] args)
    {   
        //Default settings
        String[] setup;
        String author;
        String filePath;

        //Word Search Names
        String fileName;
        String answerFileName;
        String title; //Name of Word Search

        boolean hideWordBank;
        boolean openWordSearch;
        boolean readyToExit = false; 

        int wordAmount; //num words hidden
        int level;
        int choice; //Which start tab
        
        final int NUM_WORDS = 15;
        
        Display display       = new Display(); //Display input/output
        WordSearch wordSearch = new WordSearch(); //Create search

        PDFwriter searchWriter; //writer for search
        PDFwriter answerWriter; //writer for answers

        while (!readyToExit)
        {

            choice = display.start(); //starting tab

            switch (choice)
            {
                case 0: //Start Game

                //Update settings
                setup = Settings.retrieve();
                author = setup[0];
                hideWordBank = Boolean.parseBoolean(setup[1]);
                filePath = setup[2];
                openWordSearch = Boolean.parseBoolean(setup[3]);

                //Get needed info from user
                level = display.getLevel(); //save level
                String[] words = display.getWords(NUM_WORDS); //save words given
                title = display.getTitle();
                fileName = display.getFileName();

                // fileName + "ANSWERS" + .extension
                answerFileName = fileName.substring(0, fileName.length() -4) + 
                "ANSWERS" + fileName.substring(fileName.length() -4);

                //Make searches
                String[] search = wordSearch.create(level, words);

                //Write search
                searchWriter = new PDFwriter(search, title, words, filePath,
                    fileName, author, hideWordBank);
                searchWriter.start(); //start writing
                
                //Write answer key
                String[] answers = wordSearch.finishedAnswers();
                answerWriter = new PDFwriter(answers, title, words, filePath,
                    answerFileName, author, false);
                answerWriter.start(); //start write

                if (openWordSearch)
                    searchWriter.openFile(); //Open created search

                choice = display.finish(filePath + fileName); //PDF completed
                if (choice == 1)
                    readyToExit = true;
                
                break;

                case 1: //About
                display.about();

                break;

                case 2: //Settings
                
                //Save settings
                Settings setting = display.settings();
                author = setting.author;
                hideWordBank = setting.hideWordBank;
                openWordSearch = setting.openWordSearch;
                filePath = setting.filePath;
                
                break;

                case 3: //Exit
                readyToExit = true; //Exit loop
                break;

            }

        }
    }
}
