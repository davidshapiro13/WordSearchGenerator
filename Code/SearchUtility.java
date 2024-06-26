
/**
 * Helpful Word Search Utilities
 *
 * @author David Shapiro
 * @version 1.0
 */
public class SearchUtility
{
   boolean badOverlap = true;
   int startPosX;
   int startPosY;
   int type; //word position
   
   //Word position codes by level
   int[] easyTypes = {1, 1, 1, 1, 2, 2, 2, 3};
   int[] moderateTypes = {1, 1, 2, 2, 2, 3, 3, 4};
   int[] hardTypes = {1, 1, 2, 2, 3, 3, 4, 4};
   int[] ultraToughTypes = {1, 2, 3, 3, 3, 4, 4, 4};
   
   //Orientations based on level
   boolean[] easyOrientation = {true, true, true, false};
   boolean[] moderateOrientation = {true, true, false, false};
   boolean[] hardOrientation = {true, false, false, false};
   boolean[] ultraToughOrientation = {true, false, false, false};
   
   /**
    * Determine if unique letters over lap
    * @param index of letter in word search
    * @param searchTest word search to add to
    * @param word that is being added
    * @return true if the overlap is bad, false if it isn't
    */
   public boolean checkOverlap(int index, SearchLetter[][] searchTest, String word)
   {   
       SearchLetter trialLetter;
       boolean spaceTaken;
       boolean diffLetters;
       
       switch (type)
       {
           /*
            * Try next letter
            * Check if already being used
            * Check if letters are different
            */
           case 1:
                trialLetter = searchTest[startPosY][startPosX + index];
                
                spaceTaken  = trialLetter.isInWord(); 
                diffLetters = trialLetter.getLetter() != word.charAt(index);
           break;
           
           case 2:
                trialLetter = searchTest[startPosY + index][startPosX];
                
                spaceTaken = trialLetter.isInWord(); 
                diffLetters = trialLetter.getLetter() != word.charAt(index);
           break;
           
           case 3:
                trialLetter = searchTest[startPosY + index][startPosX + index];
                
                spaceTaken = trialLetter.isInWord(); 
                diffLetters = trialLetter.getLetter() != word.charAt(index);
           break;
           
           case 4:
                trialLetter = searchTest[startPosY - index][startPosX + index];
                
                spaceTaken = trialLetter.isInWord(); 
                diffLetters = trialLetter.getLetter() != word.charAt(index);
           break;
           
           default: //Bad Overlap
                spaceTaken  = true;
                diffLetters = true;
        }
        
       badOverlap = spaceTaken && diffLetters;
       
       return badOverlap;
   }

   /**
    * checks if intented word will fit in space provided
    * @param type of positioning
    * @param starting x position of word
    * @param starting y position of word
    * @param size of puzzle width
    * @param word to position
    * @return true if it does; false otherwise
    */
   public boolean enoughSpace(int type, int startPosX, int startPosY, 
                              int size, String word)
   {
       this.startPosX = startPosX;
       this.startPosY = startPosY;
       this.type = type;
       
       boolean doesFit = false;
       
       switch(type)
       {
           case 1: //horiz. 
            doesFit = size - startPosX >= word.length();
           break;
           
           case 2: //vert.
            doesFit = size - startPosY >= word.length();
           break;
           
           case 3: //diag right.
            doesFit = size - startPosY >= word.length() &&
                      size - startPosX >= word.length();
           break;
           
           case 4: //diag left.
            doesFit = startPosY >= word.length() &&
                      size - startPosX >= word.length(); 
           break;
           
        }
       return doesFit;
       }
   
       
   /**
    * Finds list of types to choose from based on level
    * @param level of challenge
    * @return array of arrangement codes
    */
   public int[] getTypes(int level)
   {  
     int[] types = new int[8];
     
     switch (level)
     {
         
         case 0: // easy
            types = easyTypes;
         break;
         
         case 1: // moderate
            types = hardTypes;
         break;
         
         case 2: //hard
            types = hardTypes;
         break;
         
         case 3: //ultra tough
            types = ultraToughTypes;
         break;
        }
     return types;
   }
   
   /**
    * gives set of orientation codes, based on level
    * @param level
    * @return orientations of words
    */
   public boolean[] getForms(int level)
   {
       boolean[] orientations = new boolean[4];
       switch (level)
       {
       case 0: // easy
            orientations = easyOrientation;
         break;
         
         case 1: // moderate
            orientations = moderateOrientation;
         break;
         
         case 2: //hard
            orientations = hardOrientation;
         break;
         
         case 3: //ultra tough
            orientations = ultraToughOrientation;
         break;
        }
       return orientations;
    }
    }
