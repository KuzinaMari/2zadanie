import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by 1 on 27.04.2017.
 */

public class Split {

    enum ModeFlag { BY_LINES, BY_CHARS, BY_FILES }//создаем перечисление вариантов разбора файла

    public static abstract class Engine {
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

    private boolean numbersInNames; //по умолчанию false

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

    private void printParamsInfo() {
        System.out.println( "numbersInNames " + this.numbersInNames );
        System.out.println( "size " + this.sizeInLines );
        System.out.println( "sizeInChars " + this.sizeInChars );
        System.out.println( "outFilesCount " + this.outFilesCount );
        System.out.println( "baseName " + this.baseName );
        System.out.println( "fileName " + this.fileName );
    }

    public Split(boolean numbersInNames, int sizeInLines, int sizeInChars, int outFilesCount, String baseName, String fileName, ModeFlag mode) {//отладочная информация
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

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ModeFlag mode = ModeFlag.BY_LINES;
        boolean isModeChanged; //по умолчанию false
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
            switch ( token ){
                case "-d":
                    numbersInNames = true;
                    break;
                case "-l":
                    sizeInLines = Integer.parseInt( it.next() );
                    mode = ModeFlag.BY_LINES;
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
        if( fileName.trim().isEmpty() ){
            System.out.println( "Пожалуйста, обозначте имя входного файла" );
            return;
        }
        if( !Files.exists( Paths.get( fileName ) ) ){
            System.out.println( "Пожалуйста, укажите верное имя входного файла" );
            return;
        }
        Split split = new Split( numbersInNames, sizeInLines, sizeInChars, outFilesCount, baseName, fileName, mode);
        split.split();
    }

    /*package*/ String fileName( int index, int max ){
        String res = "";
        if( isNumbersInNames() ){
            res = Integer.toString( index );
        } else {
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            int base = alphabet.length;
            int quotient = index - 1;
            int digits = 0;
            int size = 0;
            int mult = base;
            while( mult <= max ){
                size++;
                mult *= base;
            }
            while ( quotient > 0 ){
                int digit = quotient % base;
                quotient = quotient / base;
                res = Character.toString( alphabet[ digit ] ) + res;
                digits++;
            }
            for( int i = digits; i <= size; i++ ){
                res = alphabet[ 0 ] + res;
            }
        }
        return getBaseName() + res;
    }
}

