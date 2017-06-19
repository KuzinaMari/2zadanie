import java.io.*;
import java.util.Scanner;

/**
 * Created by 1 on 27.04.2017.
 */
public class ByCharsEngine extends Split.Engine {
    private Split split;
    private Scanner in;
    private File file;
    private FileWriter fr;
    private int currentChar;
    private int currentFile;
    private String chr;

    public ByCharsEngine(Split split) {
        this.split = split;
        System.out.println("ByChar==========");
    }

    @Override
    protected void init() throws FileNotFoundException {
        this.in = new Scanner(new FileReader(split.getFileName()));
        this.in.useDelimiter("");
        this.file = null;
        this.fr = null;
        this.currentChar = 0;
        this.currentFile = 0;
    }

    @Override
    protected void readChunk() {
        this.chr = in.next();
        this.currentChar++;
    }

    @Override
    protected boolean hasNext() {
        return this.in.hasNext();
    }

    @Override
    protected void checkNewFile() throws IOException {
        int max = 0;

        if( this.currentChar > this.split.getSizeInChars() || this.file == null ) {
            this.currentFile++;
            if( this.file != null ){
                this.fr.close();
            } //"C:\\Users\\1\\IdeaProjects\\var122zd\\src\\var17\\" +
            this.file = new File( this.split.fileName( this.currentFile, max ) );
            this.fr = new FileWriter(this.file);
            this.currentChar = 1;
            max = (int) Math.round( Math.ceil( this.file.length() / this.split.getSizeInChars() ) );
        }
    }

    @Override
    protected void writeChunk() throws IOException {
        this.fr.write( this.chr );
    }

    @Override
    protected void finish() throws IOException {
        this.fr.close();
    }
}
