package okjava.util;

import okjava.util.annotation.Utility;

import java.util.function.Supplier;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 7/5/2016
 * 23:58.
 */
@Utility
enum FunnyFailMessage {
    ;

    private static final Supplier<String> FUNNY_FAIL_MESSAGE_SUPPLIER = FunnyFailMessage::getFunnyFailMessage;

    static Supplier<String> getFunnyFailMessageSupplier() {
        return FUNNY_FAIL_MESSAGE_SUPPLIER;
    }

    static String getFunnyFailMessage() {
        return "\n I'd like to take this opportunity to remind that my daughter's birthday is on 2nd of December."
                   + "\n Do not forget to congratulate her. She will be very happy!"
                   + "\n>Thread.currentThread().getName()~" + Thread.currentThread().getName();
    }
}
