import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.File;
import java.awt.Desktop;

/**
 * Writes word searches to PDFs
 * @author David Shapiro
 * @version 1.0
 */
public class PDFwriter
{
    /*
     * Tutorial from
     * https://www.tutorialspoint.com/pdfbox/pdfbox_adding_text.htm
     */
    
    Utility util = new Utility();

    final int MARGIN = 50; //Space on document sides
    final int Y_MAX = 800; //Highest y postion
    final int X_MAX = 610; //Highest x postion
    final int SEARCH_CHAR_SIZE = 10; //width of search char size
    final int TITLE_CHAR_SIZE = 12;  //width of title char size

    PDPageContentStream contentStream; //search writer
    PDPageContentStream titleStream; //title writer
    PDPageContentStream wordsStream; //word bank writer
    PDPageContentStream authorStream; //author writer

    PDDocument doc; //document
    PDPage page1; //page
    String[] search; //word search
    String title; //title of search
    String[] words; //words in word search

    String fileName;
    String author;
    String filePath;
    boolean hideWordBank;

    Desktop desktop = Desktop.getDesktop();

    /**
     * Sets all needed information for PDF 
     * 
     * @param word search to write
     * @param title to put on search
     * @param words to put in word bank
     * @param file path to save PDF in
     * @param file name of PDF
     * @param author of word search
     * @param word bank hidden?
     */
    public PDFwriter(String[] search, String title, String[] words, String filePath,
    String fileName, String author, boolean hideWordBank)
    {
        this.search = search;
        this.title = title;
        this.words = words;
        this.fileName = fileName;
        this.author = author;
        this.hideWordBank = hideWordBank;
        this.filePath = filePath;
    }

    /**
     * writes PDF
     */
    public void start()
    {
        try{

            doc = new PDDocument(); //New doc

            page1 = new PDPage(); //New page
            doc.addPage(page1); //add page to doc

            //writing object
            contentStream = new PDPageContentStream(doc, doc.getPage(0),
                PDPageContentStream.AppendMode.APPEND, false);
            titleStream   = new PDPageContentStream(doc, doc.getPage(0), 
                PDPageContentStream.AppendMode.APPEND, false);
            authorStream = new PDPageContentStream(doc, doc.getPage(0),
                PDPageContentStream.AppendMode.APPEND, false);                  

            writeTitle();
            writeSearch();

            //Write wordBank unless it is hidden
            if (!hideWordBank)
            {
                wordsStream   = new PDPageContentStream(doc, doc.getPage(0), 
                    PDPageContentStream.AppendMode.APPEND, false);
                writeWords();
            }
            
            writeAuthor();

            doc.save(filePath + fileName); //Save document

            doc.close(); //close document
        }
        catch(Exception e){}
    }

    /**
     * Writes word search
     */
    public void writeSearch()
    {
        try {
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(center(search[0], SEARCH_CHAR_SIZE),
                                          650); //Position x, y
                                          
            contentStream.setFont(PDType1Font.COURIER , 16); //Font

            //Write each line
            for (String line : search)
            {
                contentStream.showText(line);
                contentStream.newLine();
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close(); //close writing

        }
        catch (Exception e) {}
    }

    /**
     * Writes title of word search
     */
    public void writeTitle()
    {
        try {
            titleStream.beginText();
            titleStream.setLeading(14.5f);

            titleStream.newLineAtOffset(center(title, TITLE_CHAR_SIZE),
                                        725); //Position x, y
                                        
            titleStream.setFont(PDType1Font.COURIER , 20); //Font

            titleStream.showText(title);

            titleStream.endText();
            titleStream.close(); //close writing

        }
        catch (Exception e) {}
    }

    /**
     * Writes words in answer key
     */
    public void writeWords()
    {
        try {
            final int WORDS_PER_ROW = 4;
            
            int wordsTally = 0;
            wordsStream.beginText();
            wordsStream.setLeading(14.5f);

            wordsStream.newLineAtOffset(MARGIN, 170); //Position x, y
            wordsStream.setFont(PDType1Font.COURIER , 16); //Font

            wordsStream.showText("WORD BANK");
            wordsStream.newLine();
            wordsStream.newLine();
            wordsStream.newLine();

            for (String word : words)
            {
                if (word.length() != 0)
                {
                    wordsStream.showText(word);
                    wordsTally ++;

                    if (wordsTally % WORDS_PER_ROW == 0)
                    {
                        wordsStream.newLine();
                        wordsStream.newLine();
                    }
                    else
                        wordsStream.showText(util.spaces(word));
                }
            }

            wordsStream.endText();
            wordsStream.close(); //close writing

        }
        catch (Exception e){}
    }

    /**
     * Writes title of word search
     */
    public void writeAuthor()
    {
        try {
            authorStream.beginText();
            authorStream.setLeading(14.5f);

            authorStream.newLineAtOffset(430, 30); //Position x, y
            authorStream.setFont(PDType1Font.COURIER , 10); //Font

            authorStream.showText("Created by " + author);

            authorStream.endText();
            authorStream.close(); //close writing

        }
        catch (Exception e){}
    }

    /**
     * centers string in pdf
     * @param line to place in PDF
     * @param width of font
     * @return start of line on PDF
     */
    public int center(String line, int char_size)
    {
        int textSpace;

        textSpace = line.length() * char_size;
        
        int whiteSpace = X_MAX - (2 * MARGIN); //total white space
        int start = (( (whiteSpace - textSpace) / 2 ) + MARGIN); //start pos.
        
        return start;
    }

    /**
     * Opens PDF for user
     */
    public void openFile()
    {
        try
        {
            File wordSearch = new File(filePath + fileName);
            desktop.open(wordSearch);
        }
        catch (Exception e){}
    }
}
