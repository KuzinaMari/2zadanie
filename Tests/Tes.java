import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tes {
    ByFilesEngine byFilesEngine = new ByFilesEngine(new Split(true, 3,3,
            3,"ofile", "text.txt", Split.ModeFlag.BY_FILES));
    @Test
    void checkNewFile() throws IOException {
    byFilesEngine.checkNewFile();
    assertEquals(true,new File("ofile3").exists());
    }

    ByLinesEngine byLinesEngine = new ByLinesEngine(new Split(true, 3,3,
            3,"ofile", "text.txt", Split.ModeFlag.BY_LINES));
    @Test
    void checkNewFile1() throws IOException {
        byFilesEngine.checkNewFile();
        assertEquals(true,new File("ofile3").exists());
    }
    ByCharsEngine byCharsEngine = new ByCharsEngine(new Split(true, 3,3,
            3,"ofile", "text.txt", Split.ModeFlag.BY_CHARS));
    @Test
    void checkNewFile2() throws IOException {
        try {
            byCharsEngine.checkNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(true,new File("ofile3").exists());
    }
    @Test
    void checkFileContent() throws IOException {
        String[] filesContent = {"123", "231", "312"};
        Split s = new Split(true, 1, 1,
                1, "ofile", "test1", Split.ModeFlag.BY_LINES);
        s.split();
        for (int i = 1; i < 4; i++) {
            assertEquals(filesContent[i - 1], Files.readAllLines(Paths.get("ofile" + i)).get(0));
        }
    }
        @Test
    void checkFileContent1() throws IOException {
        String[] filesContent = {"1", "2", "3"};
        Split s = new Split(true, 1, 1,
                1, "ofile", "test1", Split.ModeFlag.BY_CHARS);
        s.split();
        for (int i = 1; i < 4; i++) {
            assertEquals(filesContent[i - 1], Files.readAllLines(Paths.get("ofile" + i)).get(0));
        }
    }

    @Test
    void checkFileContent2() throws IOException {
        String[] filesContent = {"1", "2", "3"};
        Split s = new Split(true, 1, 1,
                1, "ofile", "test1", Split.ModeFlag.BY_CHARS);
        s.split();
        for (int i = 1; i < 4; i++) {
            assertEquals(filesContent[i - 1], Files.readAllLines(Paths.get("ofile" + i)).get(0));
        }
    }
}