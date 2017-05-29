import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tes {
    ByFilesEngine byFilesEngine = new ByFilesEngine(new Split(true, 3,3,
            3,"ofile", "C:\\Users\\1\\IdeaProjects\\2zadanie\\out\\production\\2zadanie\\text.txt", Split.ModeFlag.BY_FILES));
    @Test
    void checkNewFile() throws IOException {
    byFilesEngine.checkNewFile();
    assertEquals(true,new File("C:\\Users\\1\\IdeaProjects\\var122zd\\src\\var17\\ofile4").exists());
    }

    ByLinesEngine byLinesEngine = new ByLinesEngine(new Split(true, 3,3,
            3,"ofile", "C:\\Users\\1\\IdeaProjects\\2zadanie\\out\\production\\2zadanie\\text.txt", Split.ModeFlag.BY_LINES));
    @Test
    void checkNewFile1() throws IOException {
        byFilesEngine.checkNewFile();
        assertEquals(true,new File("C:\\Users\\1\\IdeaProjects\\var122zd\\src\\var17\\ofile4").exists());
    }
    ByCharsEngine byCharsEngine = new ByCharsEngine(new Split(true, 3,3,
            3,"ofile", "C:\\Users\\1\\IdeaProjects\\2zadanie\\out\\production\\2zadanie\\text.txt", Split.ModeFlag.BY_FILES));
    @Test
    void checkNewFile2() throws IOException {
        byFilesEngine.checkNewFile();
        assertEquals(true,new File("C:\\Users\\1\\IdeaProjects\\var122zd\\src\\var17\\ofile4").exists());
    }
}