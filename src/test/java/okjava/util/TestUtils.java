package okjava.util;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/17/2016
 * 16:31.
 */
public class TestUtils {
    public static void setJULtoConsoleSTDOUT() {
        Handler ch = new ConsoleHandler() {
            @Override
            protected synchronized void setOutputStream(OutputStream out) throws SecurityException {
                super.setOutputStream(System.out);
            }
        };
        ch.setLevel(Level.FINEST);
        Logger rootLogger = Logger.getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            // Change log level of default handler(s) of root logger
            // The paranoid would check that this is the ConsoleHandler ;)
            rootLogger.removeHandler(handler);
        }
        rootLogger.addHandler(ch);
        rootLogger.setLevel(Level.INFO);
    }
}
