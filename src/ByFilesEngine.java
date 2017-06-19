import java.io.*;

/**
 * Created by 1 on 27.04.2017.
 */
public class ByFilesEngine extends Split.Engine {
    private File inputFile;
    private InputStream is;
    private OutputStream os;
    private int fileSize;
    private int currentByte;
    private int nextByte;
    private int currentFile;
    private File file;

    private Split split;

    public ByFilesEngine(Split split) {
        this.split = split;
        System.out.println("ByFilesEngine==========");
    }

    @Override
    protected void init() throws FileNotFoundException {
        this.inputFile = new File( split.getFileName() );
        this.is = new FileInputStream( this.inputFile );
        this.os = null;
        this.fileSize = (int) Math.ceil( ((double)this.inputFile.length()) / split.getOutFilesCount() ) ;
        System.out.println( "outupt file size " + this.fileSize );
        this.currentByte = 0;
        this.currentFile = 0;
        this.nextByte = 0;
    }

    @Override
    protected void readChunk() throws IOException {
        this.nextByte = this.is.read();
        this.currentByte++;
    }

    @Override
    protected boolean hasNext() {
        return this.nextByte >= 0;
    }

    @Override
    protected void checkNewFile() throws IOException {
        int max = this.split.getOutFilesCount();
        if( this.currentByte > this.fileSize || this.file == null ){
            this.currentFile++;
            if( this.file != null ){
                this.os.close();
            }
            String pathname = this.split.fileName( this.currentFile, max );
            System.out.println(pathname);
            this.file = new File(pathname);
            this.os = new FileOutputStream( this.file );
            this.currentByte = 1;
        }
    }

    @Override
    protected void writeChunk() throws IOException {
        this.os.write( this.nextByte );
    }

    @Override
    protected void finish() throws IOException {
        this.os.close();
    }
}

