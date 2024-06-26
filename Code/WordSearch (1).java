
/**
 * Write a description of class WordSearch here.
 *
 * @author David Shapiro
 * @version 1.0
 */
public class WordSearch
{
    SearchUtility searchUtil = new SearchUtility();
    Utility util = new Utility();
    Display display = new Display();
    char[] letters = util.alphaLetters(); //All alphabet letters

    final int PUZZLE_SIZE = 10; //length and width are equal 
    int level;
    boolean successful;
    SearchLetter[][] wordSearch = new SearchLetter[PUZZLE_SIZE][PUZZLE_SIZE];
    SearchLetter[][] searchTest = new SearchLetter[PUZZLE_SIZE][PUZZLE_SIZE];

    String[] words = new String[10];
    
    //Word Placement
    SearchLetter searchChar;
    int startPosX; // X position of first letter 
    int startPosY; // Y position of first letter
    int type; //hori, vert, diagRight, diagLeft
    int problems; //How many times placement fails
    boolean doesFit; //Will word fit in position
    boolean[] badOverlap = new boolean[5]; //Overlap with diff letters

    /**
     * Creates puzzle
     * @param level of difficulty
     * @param words to put in search
     */
    public void create(int level, String[] words)
    {
        int randomIndex; 
        char letterChoice; 
        this.words = words;
        this.level = level;

        for (int x = 0; x < PUZZLE_SIZE; x ++)
            for (int y = 0; y < PUZZLE_SIZE; y ++)
            {
                randomIndex = (int)(Math.random() * 26);
                SearchLetter letter = new SearchLetter(letters[randomIndex], false);
                wordSearch[x][y] = letter;
            }
        
        organize();
        test();
    }

    /*
     * Purely for testing word search, will not be needed in end
     */
    public void test()
    {
        for (SearchLetter[] row : wordSearch)
        {
            for (SearchLetter item : row) 
                System.out.print(item.getLetter() + " ");
            System.out.println();
        }
    }
    
    /*
     * Purely for testing word search, will not be needed in end
     */
    public void test2()
    {
        for (SearchLetter[] row : searchTest)
        {
            for (SearchLetter item : row) 
                System.out.print(item.getLetter() + " ");
            System.out.println();
        }
        System.out.println("\n\n\n\n");
    }
    
    /**
     * Create a horizontal word in search
     */
    public boolean writeWord(String word)
    {
        setUp();
        System.out.println(word);
        while (!doesFit)
        {
            startPosX = (int)(Math.random() * PUZZLE_SIZE); // random X pos
            startPosY = (int)(Math.random() * PUZZLE_SIZE); // random Y pos

            doesFit = searchUtil.enoughSpace(type, startPosX, 
                                             startPosY, PUZZLE_SIZE, word); //Enough room?
            
            if (doesFit) //Enough room
            {
                
                //Adds if postion is valid for each letter
                for (int i = 0; i < word.length(); i ++)
                {
                    badOverlap[i] = searchUtil.checkOverlap(i, searchTest);
                }
                
                
                if (util.noTrue(badOverlap))  //No true in badOverlap
                {
                    
                    //For each letter in word
                    for (int j = 0; j < word.length(); j ++)
                    {
                        switch (type) //Horiz, Vert, Diag Right, Diag Left
                        {
                            case 1:
                                hori(j, word);
                            break;
                            
                            case 2:
                                vertical(j, word);
                            break;
                            
                            case 3:
                                diagRight(j, word);
                            break;
                            
                            case 4:
                                diagLeft(j, word);
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
                        return false; //too many problems
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
        searchChar = new SearchLetter(word.charAt(index), true); 
        searchTest[startPosY][startPosX + index] = searchChar;
    }
    
    /**
     * Puts letter at next vertical position
     */  
    public void vertical(int index, String word)
    {
       searchChar = new SearchLetter(word.charAt(index), true); 
       searchTest[startPosY + index][startPosX] = searchChar; 
    }
    
    /**
     * Puts letter at next diagonal position, rightwards
     */  
    public void diagRight(int index, String word)
    {
        searchChar = new SearchLetter(word.charAt(index), true); 
        searchTest[startPosY + index][startPosX + index] = searchChar;
    }
    
    /**
     * Puts letter at next diagonal position, leftwards
     */  
    public void diagLeft(int index, String word)
    {
        searchChar = new SearchLetter(word.charAt(index), true); 
        searchTest[startPosY - index][startPosX + index] = searchChar;
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
     * Determine level of difficulty
     */
    public void organize()
    {

        switch (level)
        {
            case 3: //easy
            easy();
            break;

            case 2: //moderate
            moderate();
            break;

            case 1: //hard
            hard();
            break;

            case 0: //ultra tough
            ultraTough();
            break;
        }
    }

    
    
    
    
    /**
     * Easy level
     */
    public void easy()
    {
        doesFit = false;
        int errors = 0;
        boolean setCorrect = true;
        boolean[] successful = new boolean[words.length];
        
        while (!doesFit)
        {
            searchTest = wordSearch; 
            
            for (int i = 0; i < words.length; i ++) //Every word
            {
                type = 1;
                successful[i] = writeWord(words[i]); //was wordsearch placement a success
                System.out.println(successful[i]);
                //util.sleep(1);
            }
            
            setCorrect = util.noFalse(successful); //no false in successful
            
            if (setCorrect)
            {
                doesFit = true;
            }
            else
            {
                setCorrect = true; //reset
                System.out.print("PROBLRM");
                errors ++;
                if (errors > PUZZLE_SIZE * PUZZLE_SIZE)
                    display.showError("These words will not fit");
            }
            
        }
        
        wordSearch = searchTest;
    }

    /**
     * Moderate level
     */
    public void moderate()
    {
        searchTest = wordSearch;
        for (int i = 0; i < words.length; i ++)
        {
            //vertical();
        } 
    }

    /**
     * Hard level
     */
    public void hard()
    {
        searchTest = wordSearch;
        for (int i = 0; i < words.length; i ++)
        {
            //diagRight();
        } 
    }

    /**
     * Ultra Tough level
     */
    public void ultraTough()
    {
        searchTest = wordSearch;
        for (int i = 0; i < words.length; i ++)
        {
            //diagLeft();
        } 
    }
}
