import java.util.ArrayList;

/**
 * Creates both word searches and answer key
 *
 * @author David Shapiro
 * @version 1.0
 */
public class WordSearch
{
    SearchUtility searchUtil = new SearchUtility();
    Utility util             = new Utility();
    Display display          = new Display();
    
    char[] letters = util.alphaLetters(); //All alphabet letters

    final int PUZZLE_SIZE = 15; //length and width are equal 
    int level; //level of challenge
    boolean successful; //
    
    // Word Searches
    SearchLetter[][] wordSearch = new SearchLetter[PUZZLE_SIZE][PUZZLE_SIZE];
    SearchLetter[][] searchTest = new SearchLetter[PUZZLE_SIZE][PUZZLE_SIZE];
    String[] finalSearch = new String[PUZZLE_SIZE];

    String[] words = new String[15];
    
    //Word Placement
    SearchLetter searchChar;
    int startPosX; // X position of first letter 
    int startPosY; // Y position of first letter
    int type; //hori, vert, diagRight, diagLeft
    int problems; //How many times placement fails
    boolean doesFit; //Will word fit in position
    ArrayList<Boolean> badOverlap; //Overlap with diff letters

    /**
     * Creates puzzle
     * @param level of difficulty
     * @param words to put in search
     */
    public String[] create(int level, String[] words)
    {
        int randomIndex; //random alphabet pos.
        char letterChoice;
        
        this.words = words;
        this.level = level;
        
        
        /*
         * Randomly create a word search
         */
        for (int x = 0; x < PUZZLE_SIZE; x ++) //each line
            for (int y = 0; y < PUZZLE_SIZE; y ++) //each column
            {
                randomIndex = (int)(Math.random() * util.alphaLetters().length);
                SearchLetter letter = new SearchLetter(letters[randomIndex],
                                                       false); //char, partOfWord
                wordSearch[x][y] = letter; //searchLetter to position
            }
        
        writeSearch();
        return finishedSearch();
    }

    /**
     * Writes entire word search and checks that it works
     */
    public void writeSearch()
    {
        doesFit = false;
        int errors = 0;
        
        boolean setCorrect = true;
        
        boolean[] successful = new boolean[words.length]; //successful search?
        boolean[] orientation = searchUtil.getForms(level); //forward backward
        
        int[] types = searchUtil.getTypes(level); //placement Ex: horiz.
        
        
        while (!doesFit)
        {
            searchTest = wordSearch; //temp word search
            
            for (int i = 0; i < words.length; i ++) //Every word
            {
                int typeIndex = (int)(Math.random() * 8);
                int orientIndex = (int)(Math.random() * 4);
                
                type = types[typeIndex];

                if (orientation[orientIndex] == false) //backwards
                {
                    successful[i] = writeWord(util.toReverse(words[i])); //reverse?
                }
                else 
                {
                    successful[i] = writeWord(words[i]);
                }
            }
            
            setCorrect = util.noFalse(successful); //no false in successful
            
            if (setCorrect)
            {
                doesFit = true;
            }
            else
            {
                setCorrect = true; //reset
                errors ++;
                
                if (errors > PUZZLE_SIZE * PUZZLE_SIZE)
                    display.showError("These words will not fit");
            }
            
        }
        
        wordSearch = searchTest; //save as real search
    }
    
    /**
     * Create a horizontal word in search
     */
    public boolean writeWord(String word)
    {
        setUp();
        while (!doesFit)
        {
            startPosX = (int)(Math.random() * PUZZLE_SIZE); // random X pos
            startPosY = (int)(Math.random() * PUZZLE_SIZE); // random Y pos

            doesFit = searchUtil.enoughSpace(type, startPosX, 
                                 startPosY, PUZZLE_SIZE, word); //Enough room?
            
            if (doesFit) //Enough room
            {
                badOverlap = new ArrayList<Boolean>();
                
                //Adds value of if position is valid
                for (int i = 0; i < word.length(); i ++)
                {
                    badOverlap.add(searchUtil.checkOverlap(i, searchTest, word));
                }
                
                if (util.noTrue(badOverlap))  //No trues in badOverlap
                {
                    //For each letter in word
                    for (int j = 0; j < word.length(); j ++)
                    {
                        switch (type) //Horiz, Vert, Diag Right, Diag Left
                        {
                            case 1:
                                hori(j, word); //horizontal word
                            break;
                            
                            case 2:
                                vertical(j, word); //vertical word
                            break;
                            
                            case 3:
                                diagRight(j, word); //right diagonal word
                            break;
                            
                            case 4:
                                diagLeft(j, word); //left diagonal word
                            break;
                        }
                    }
                    
                    return true; //success
                }

                else //At least one problem
                {
                    doesFit = false;
                    problems ++;

                    if (problems >= PUZZLE_SIZE * PUZZLE_SIZE)
                        return false; //fail, too many problems
                }
            }
        }
        return false; //DEFAULT
        }
      
    /**
     * Puts letter at next horizontal position
     */    
    public void hori(int index, String word)
    {
        searchChar = new SearchLetter(word.charAt(index), true); //new letter
        searchTest[startPosY][startPosX + index] = searchChar; //add letter
    }
    
    /**
     * Puts letter at next vertical position
     */  
    public void vertical(int index, String word)
    {
       searchChar = new SearchLetter(word.charAt(index), true);  //new letter
       searchTest[startPosY + index][startPosX] = searchChar; //add letter
    }
    
    /**
     * Puts letter at next diagonal position, rightwards
     */  
    public void diagRight(int index, String word)
    {
        searchChar = new SearchLetter(word.charAt(index), true); //new letter
        searchTest[startPosY + index][startPosX + index] = searchChar; //add letter
    }
    
    /**
     * Puts letter at next diagonal position, leftwards
     */  
    public void diagLeft(int index, String word)
    {
        searchChar = new SearchLetter(word.charAt(index), true); //new letter 
        searchTest[startPosY - index][startPosX + index] = searchChar; //add letter
    }

    /**
     * Reset before trying to make a word search
     */  
    public void setUp()
    {
        problems = 0; //no problems
        doesFit = false; //Word doesnt fit
    }

     /**
     * Writes just the answers
     * @return Array of word search lines
     */
    public String[] finishedAnswers()
    {
        String line = "";
        
        for (int row = 0; row < PUZZLE_SIZE; row ++)
        {
            for (SearchLetter letter : wordSearch[row])
            {
                
                //is letter a part of a word?
                if (letter.isInWord())
                    line += letter.getLetter();
                else
                    line += " "; 
                
                line += " "; //Spacing
            }
            
            finalSearch[row] = line; //add each line
            line = ""; //reset
        }   
        return finalSearch;
    }
    
    /**
     * creates a finished word search
     * @return array of word search lines
     */
    public String[] finishedSearch()
    {
        String line = "";
        SearchLetter letter;
        for (int row = 0; row < PUZZLE_SIZE; row ++)
        {
            for (int x = 0; x < wordSearch[row].length; x ++)
            {
                
                letter = wordSearch[row][x];
                line += letter.getLetter();
                
                if (x != (wordSearch[row].length - 1) )
                    line += "  "; //Spacing
                 
            }
            finalSearch[row] = line;
            line = "";
            }
        return finalSearch;
    }
    
}
