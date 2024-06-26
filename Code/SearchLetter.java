
/**
 * letter in word search
 *
 * @author David Shapiro
 * @version 1.0
 */
public class SearchLetter
{
    private boolean partOfWord = false;
    private char letter = ' ';
    
    public SearchLetter(char letter, boolean partOfWord)
    {
        this.letter = letter;
        this.partOfWord = partOfWord;
    }
    
    /**
     * give letter
     * @return character 
     */
    public char getLetter()
    {
        return letter;
    }
    
    /**
     * Sets letter value
     * @param letter to set to
     */
    public void setLetter(char letter)
    {
        this.letter = letter;
    }
    
    /**
     * sets if letter is part of word or not
     */
    public void setPartOfWord()
    {
        partOfWord = !partOfWord;
    }
    
    /**
     * checks if letter is in word
     * @return true if part of word; false if not
     */
    public boolean isInWord()
    {
        return partOfWord;
    }
}
