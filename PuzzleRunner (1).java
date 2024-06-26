
/**
 * Creates Puzzles... [ADD MORE]
 *
 * @author David Shapiro
 * @version 1.0
 */
public class PuzzleRunner
{
    public static void main (String [] args)
    {
        Display display       = new Display();
        WordSearch wordSearch = new WordSearch();
        
        final int WORD_AMOUNT = 10;
        //String[] words = new String[WORD_AMOUNT];
        int level;
        
        int choice = display.start();  
        
       String[] words = {"HELLO", "QQQQQ", "WWWWW", "LLLLL", "OOOOO",
                "PPPPP", "GGGGG", "SSSSS", "AAAAA", "JJJJJ" };
                
        switch (choice)
       {
           case 0: //Start Game
                level = display.getLevel();
                display.getWords(WORD_AMOUNT);//SET TO WORDS LATER
                wordSearch.create(level, words);
           break;
           
           case 1: //About
                display.about();
           break;
           
           case 2: //Settings
           break;
           
           case 3: //Exit
           break;
            
       }
        
    }
}
