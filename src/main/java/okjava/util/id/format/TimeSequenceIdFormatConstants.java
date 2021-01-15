package okjava.util.id.format;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;
import okjava.util.datetime.DateTimeFormat;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:10.
 */
@Utility
enum TimeSequenceIdFormatConstants {
    ;

     final static String PATTERN = "yyyyMMdd:HHmmss.SSSX";
     final static DateTimeFormat FORMATTER = DateTimeFormat.create(PATTERN);
     final static String SEPARATOR = "'";

    TimeSequenceIdFormatConstants(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
   }

}
