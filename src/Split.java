import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by 1 on 27.04.2017.
 */

public class Split {

     static enum ModeFlag { BY_LINES, BY_CHARS, BY_FILES }

    public static abstract class Engine{
        protected abstract void init() throws FileNotFoundException;
        protected abstract void readChunk() throws IOException;
        protected abstract boolean hasNext();
        protected abstract void checkNewFile() throws IOException;
        protected abstract void writeChunk() throws IOException;
        protected abstract void finish() throws IOException;

        public void doSplit() throws IOException {
            init();
            while (hasNext()) {
                readChunk();
                checkNewFile();
                writeChunk();
            }
            finish();
        }
    }

    private boolean numbersInNames = false;

    public boolean isNumbersInNames() {
        return numbersInNames;
    }

    public int getSizeInLines() {
        return sizeInLines;
    }

    public int getSizeInChars() {
        return sizeInChars;
    }

    public int getOutFilesCount() {
        return outFilesCount;
    }

    public String getBaseName() {
        return baseName;
    }

    public String getFileName() {
        return fileName;
    }

    public ModeFlag getMode() {
        return mode;
    }

    private int sizeInLines = 100; //значения по умолчанию
    private int sizeInChars = 100;
    private int outFilesCount = 100;
    private String baseName = "x";
    private String fileName = "";
    private ModeFlag mode;

    public Split(boolean numbersInNames, int sizeInLines, int sizeInChars, int outFilesCount, String baseName, String fileName, ModeFlag mode) {
        this.numbersInNames = numbersInNames;
        this.sizeInLines = sizeInLines;
        this.sizeInChars = sizeInChars;
        this.outFilesCount = outFilesCount;
        this.baseName = baseName;
        this.fileName = fileName;
        this.mode = mode;
        printParamsInfo();
    }

    public void split() throws FileNotFoundException, IOException {
        Engine engine = null;
        switch( this.mode ){
            case BY_LINES:
                engine = new ByLinesEngine(this);
                break;
            case BY_CHARS:
                engine = new ByCharsEngine(this);
                break;
            case BY_FILES:
                engine = new ByFilesEngine(this);
                break;
        }
        engine.doSplit();
    }

    private void printParamsInfo() {
        System.out.println( "numbersInNames " + this.numbersInNames );
        System.out.println( "size " + this.sizeInLines );
        System.out.println( "sizeInChars " + this.sizeInChars );
        System.out.println( "outFilesCount " + this.outFilesCount );
        System.out.println( "baseName " + this.baseName );
        System.out.println( "fileName " + this.fileName );
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ModeFlag mode = ModeFlag.BY_LINES;

        boolean numbersInNames = false;
        int sizeInLines = 100;
        int sizeInChars = 100;
        int outFilesCount = 100;
        String baseName = "x";
        String fileName = "";

        Iterable<String> argsList = Arrays.asList(args);
        Iterator<String> it = argsList.iterator();
        while ( it.hasNext() ){
            String token = it.next();
            System.out.println(token);
            switch ( token ){
                case "-d":
                    numbersInNames = true;
                    baseName = "ofile";
                    break;
                case "-l":
                    sizeInLines = Integer.parseInt( it.next() );
                    break;
                case "-c":
                    sizeInChars = Integer.parseInt( it.next() );
                    mode = ModeFlag.BY_CHARS;
                    break;
                case "-n":
                    outFilesCount = Integer.parseInt( it.next() );
                    mode = ModeFlag.BY_FILES;
                    break;
                case "-o":
                    baseName = it.next();
                    break;
                default:
                    fileName = token;
                    break;
            }
        }
        Split split = new Split( numbersInNames, sizeInLines, sizeInChars, outFilesCount, baseName, fileName, mode);
        split.split();
    }
}

