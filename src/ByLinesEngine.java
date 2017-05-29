import java.io.*;
import java.util.Scanner;

/**
 * Created by 1 on 27.04.2017.
 */

public class ByLinesEngine extends Split.Engine {
    private Split split;
    private Scanner in;
    private File file;
    private FileWriter fr;
    private int currentLine;
    private int currentFile;
    private String line;

    public ByLinesEngine(Split split) {
        this.split = split;
        System.out.println("ByLines==========");
    }

        @Override
        protected void init() throws FileNotFoundException {
            this.in = new Scanner(new FileReader(split.getFileName()));
            this.file = null;
            this.fr = null;
            this.currentLine = 0;
            this.currentFile = 0;
        }

        @Override
        protected void readChunk() {
            this.line = in.nextLine();
            this.currentLine++;
        }

        @Override
        protected boolean hasNext() {
            return in.hasNext();
        }

        @Override
        protected void checkNewFile() throws IOException {
            if( this.currentLine > this.split.getSizeInLines() || this.file == null ){
                this.currentFile++;
                if( this.file != null ){
                    this.fr.close();
                }
                this.file = new File("C:\\Users\\1\\IdeaProjects\\var122zd\\src\\var17\\" + this.split.getBaseName() + Integer.toString(this.currentFile));
                this.fr = new FileWriter(this.file);
                this.currentLine = 1;
            } else {
                this.line = "\n" + this.line;
            }
        }

        @Override
        protected void writeChunk() throws IOException {
            this.fr.write( this.line );
        }

        @Override
        protected void finish() throws IOException {
            this.fr.close();
        }
    }

