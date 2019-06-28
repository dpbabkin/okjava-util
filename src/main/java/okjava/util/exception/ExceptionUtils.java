package okjava.util.exception;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.io.PrintWriter;
import java.io.StringWriter;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/27/2019
 * 19:07.
 */
@Utility
public enum ExceptionUtils {

    ;

    ExceptionUtils(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }


    public static String exceptionStackTraceToString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString(); // stack trace as a string
    }
}
