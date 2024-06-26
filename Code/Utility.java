import java.util.Scanner;
import java.util.ArrayList;
/**
 * Provides commonly used methods for efficiency
 *
 * @author David Shapiro
 * @version 1.0
 */
public class Utility
{
   Scanner scan = new Scanner(System.in);
    
    /**
     * Pauses execution of code
     * 
     * @param number of seconds to pause for
     * @return none
     */
   public void sleep(double seconds) 
   {
      long amount = 1000 * (long)(seconds);
      try
      {
         Thread.sleep(amount);
      }
      catch (Exception e){}
   }
    
   /**
     * Determines if user said yes or no
     * @param prompt of what screen should display
     * @return isYes answer is yes or no
     */
   public boolean yesOrNo(String prompt)
   {
      String[] yesAnswers = {"yes", "y", "1", "of course", "yup", "sure", "yeah", 
         "why not", "sounds good", "totally", "ok", "alright"};
   
      boolean isYes = false;
      String message = prompt;
      System.out.println(message);
      String response = scan.nextLine();
      
      //go through all possible yes responses
      for (int i = 0; i < yesAnswers.length; i++)
      {
       
         //if user used a yes word
         if ((response.toLowerCase()).equals(yesAnswers[i]))
         {
            isYes = true;
         }
      }
      return isYes;
   }
   
    /**
     * Determines if user said yes or no
     * @return isYes answer is yes or no
     */
   public boolean trueFalse(String answer)
   {
      String[] yesAnswers = {"yes", "y", "1", "of course", "yup", "sure", "yeah", 
         "why not", "sounds good", "totally", "ok", "alright", "true"};
   
      boolean isYes = false;
      //go through all possible yes responses
      for (int i = 0; i < yesAnswers.length; i++)
      {
       
         //if user used a yes word
         if ((answer.toLowerCase()).equals(yesAnswers[i]))
         {
            isYes = true;
         }
      }
      return isYes;
   }
   
   /**
   *Display a ... to show program is working on something
   *@param message to display
   *@return none
   */
   public void think(String message, double time)
   {
      System.out.print(message);
      
      for (int i = 0; i < 3; i ++) //Three times
      {
         sleep(time);
         System.out.print(".");
      }
      
   }
   
   /**
   * make sure character is part of alphabet
   * @param testChar character to test
   * @return isSymbol symbol or alphabet
   **/
   public boolean symbolCheck(char testChar)
   {
      boolean isSymbol = false;
      
      
      char[] symbols = {' ', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', 
         '{', '}', '[', ']', '\\', '\n', '\'', ';', '\"', ':', '<', '>', '/', '?', '1', '2', '3',
         '4', '5', '6', '7', '8', '9', '0', '~', '`', '+', '=', ',', '.'}; //All possible symbols
      
      for (int i = 0; i < symbols.length; i ++) //Each symbol
      {
        //character a symbol 
         if (testChar == symbols[i]) 
         {
            isSymbol = true;
         }
      }
      
      return isSymbol;
   }
   
   /**
   *Get rid of all non alphabetical symbols
   * @param word that may have symbols
   * @return String without symbols
   **/
   
   public String deleteSymbols(String phrase)
   {
      String newPhrase = ""; //Phrase without symbols
      boolean isSymbol = false; 
      char[] letters = phrase.toCharArray(); //letters in phrase
      char[] symbols = {' ', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', 
         '{', '}', '[', ']', '\\', '\n', '\'', ';', '\"', ':', '<', '>', '/', '?', '1', '2', '3',
         '4', '5', '6', '7', '8', '9', '0', '~', '`', '+', '=', ',', '.'}; //All possible symbols
      
      
      for (int index = 0; index < phrase.length(); index ++) //Each letter position
      {
         for (int i = 0; i < symbols.length; i ++) //Each symbol
         {
            //character a symbol 
            if (letters[index] == symbols[i]) 
            {
               isSymbol = true;
            }
         }
      
         // Not a symbol, add to new phrase
         if (!isSymbol)
            newPhrase += letters[index];
         isSymbol = false;
      }
      
      return newPhrase;
   }
   

   /**
   * Finds first index of item
   * @param choices of things to do
   * @param item in choices
   * @return index of item; -1 if it does not exist
   **/
   public int indexOf(String[] choices, String item)
   {
   
      for (int index = 0; index < choices.length; index ++) // each item in list
      {
         if (item == choices[index])
            return index; //first index of item
      }
       
      return -1; //item is not in choices
   } 
   
   /**
    * provides all letters
    * @return all alphabetical letters in lowercase
    */
   public char[] alphaLetters()
   {
      char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'}; //All alphabet letters
      return letters;
   }
   
   /**
    * checks if any booleans are false
    * returns true if not falses, false if any
    */
   public boolean noTrue(ArrayList<Boolean> items)
   {
       boolean item = false;
       for (int i = 0; i < items.size(); i ++)
       {
          item = items.get(i);
          if (item == true)
            return false;
        }
       return true;
    }
    
    /**
    * checks if any booleans are false
    * returns true if not falses, false if any
    */
   public boolean noFalse(boolean[] items)
   {
       for (boolean item : items)
        if (item == false)
            return false;
       return true;
    }
    
    /**
    * checks if any booleans are false in Array List
    * returns true if not falses, false if any
    */
   public boolean noFalse(ArrayList<Boolean> items)
   {
       for (Boolean item : items)
        if (item == false)
            return false;
       return true;
    }
    
    
    /**
     * reverses word
     * @param word to reverse
     * @return reversed word
     */
    public String toReverse(String word)
    {
        String newWord = "";
        for (int i = word.length() -1; i >= 0; i --)
            newWord += word.charAt(i);
        return newWord;
    }
    
    /**
     * @param name to format
     * @param correct extension
     * @return formatted name
     */
    public String format(String name, String extension)
    {
       if (!name.endsWith(extension))
        name += extension;
       return name;
    }
    
    /**
     * @param word
     * @return spaces required after it
     */
    public String spaces(String word)
    {
        String spaces = "";
        int spaceAmount = 10;
        final int MIN_WORD_SIZE = 3;
        int wordExtra = word.length() - MIN_WORD_SIZE;
        spaceAmount -= wordExtra;
        for (int i = 0; i < spaceAmount; i ++)
           spaces += " "; 
        return spaces;
    }
}
