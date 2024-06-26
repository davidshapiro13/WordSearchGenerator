
/**
 * Helpful Word Search Utilities.
 *
 * @author David Shapiro
 * @version 1.0
 */
public class SearchUtility
{
   boolean badOverlap = true;
   int startPosX;
   int startPosY;
   String word;
   int type;
   
   public boolean checkOverlap(int index, SearchLetter[][] searchTest)
   {   
       
       
       switch (type)
       {
           case 1:
                badOverlap = searchTest[startPosY][startPosX + index].isInWord() && 
                searchTest[startPosY][startPosX + index].getLetter() != word.charAt(index);
           break;
           
           case 2:
                badOverlap = searchTest[startPosY + index][startPosX ].isInWord() && 
                searchTest[startPosY + index][startPosX].getLetter() != word.charAt(index);
           break;
           
           case 3:
                badOverlap = searchTest[startPosY + index][startPosX + index].isInWord() && 
                searchTest[startPosY + index][startPosX + index].getLetter() != word.charAt(index);
           break;
           
           case 4:
                badOverlap = searchTest[startPosY - index][startPosX + index].isInWord() && 
                searchTest[startPosY - index][startPosX + index].getLetter() != word.charAt(index);
           break;
           
        }
       return badOverlap;
   }

   public boolean enoughSpace(int type, int startPosX, int startPosY, int size, String word)
   {
       this.startPosX = startPosX;
       this.startPosY = startPosY;
       this.word = word;
       this.type = type;
       
       boolean doesFit = false;
       
       switch(type)
       {
           case 1: 
            doesFit = size - startPosX > word.length();
           break;
           
           case 2:
            doesFit = size - startPosY > word.length();
           break;
           
           case 3:
            doesFit = size - startPosY > word.length() &&
                      size - startPosX > word.length();
           break;
           
           case 4:
            doesFit = startPosY > word.length() &&
                      size - startPosX > word.length(); 
           break;
           
        }
       return doesFit;
       }
    }
