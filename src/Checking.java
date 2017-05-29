import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Created by 1 on 04.05.2017.
 */
public class Checking {

    @Option(name = "-o", metaVar = "fileName",
            usage = "setfailname")
    private String nameFile;

    @Option(name = "-l",  forbids = {"-c", "-n"},  metaVar = "flag", required = true,
            usage = "setVarOfSplit")
    private String flag;
    @Option(name = "-c",  forbids = {"-l", "-n"},  metaVar = "flag", required = true,
            usage = "setVarOfSplit")
    private String flag1;
    @Option(name = "-n",  forbids = {"-c", "-l"},  metaVar = "flag", required = true,
            usage = "setVarOfSplit")
    private String flag2;
    @Option(name = "-d", metaVar = "lastfileName",
            usage = "lastsetfailname")
    private String lastFileName;

    public void readCLine(String args) {
        CmdLineParser parser = new CmdLineParser(this); //разделили по аргументам
        try {
            if (Integer.parseInt(flag) <= 0) {
                throw new CmdLineException("Неверный формат строки");
            }
        }
        catch (CmdLineException e) {
    }}}