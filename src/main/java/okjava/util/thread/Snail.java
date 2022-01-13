package okjava.util.thread;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;
import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/19/2017
 * 22:48.
 */
@Utility
public enum Snail {
    ;

    private static final String stop = generateStop();
    private static final String snail0 = generateSnail0();
    private static final String snail1 = generateSnail1();
    private static final String snail2 = generateSnail2();
    Snail(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static String getSnail0() {
        return snail0;
    }

    public static String generateSnail0() {
        return """


                                           .-=--.               \s
                                         .' .--. '.             \s
                                        :  : .-.'. :    _ _        \s
                                        :  : : .': :   (o)o)    \s
                                        :  '. '-' .'   ////     \s
                                        _'.__'--=' '-.//i'      \s
                                     .-'               /        \s
                                     '---..____...---''         \s

                """;
    }

    public static String getSnail1() {
        return snail1;
    }

    public static String generateSnail1() {
        return """


                      /^\\    /^\\
                     {  O}  {  O}
                      \\ /    \\ /
                      //     //       _------_
                     //     //     ./~        ~-_
                    / ~----~/     /              \\
                  /         :   ./       _---_    ~-
                 |  \\________) :       /~     ~\\   |
                 |        /    |      |  :~~\\  |   |
                 |       |     |      |  \\___-~    |
                 |        \\ __/`=1C______\\.        ./
                  \\                     ~-______-~\\.
                  .|                                ~-_
                 /_____________________________________~~____
                \s
                """;
    }

    public static String getSnail2() {
        return snail2;
    }

    public static String generateSnail2() {
        return """


                         (0)_(0)                                \s
                           \\\\  \\            _____            \s
                            \\\\  \\       .-'`     ``'-.       \s
                             \\\\  \\_   .'              '.     \s
                         _.-'    `\\  /      _..---._    \\     \s
                       .'           \\/    .-'  __    '.   \\   \s
                      /       ,_    |    /    (_,`\\    |  |    \s
                      \\       /     |:.  \\        /    /  /   \s
                       '----'`--'\\  \\:.   '-....-'   .' _/    \s
                                  \\ /`-..___     _.-' -` \\.__ \s
                                   |::.     `````        /   `) \s
                                    `-::___         __.-'``''`  \s
                                           `````````            \s

                """;
    }

    private static String stopNative() {
        return """
                ─
                ─
                ─
                ─────────────────────▄████▄
                ──────────────────────█▀▀█
                ─────▄▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▄
                ───▄▀░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▀▄
                ─▄▀░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▀▄
                █░░░░▄██████▄░░████████░░▄██████▄░░▄██████▄░░░░█
                █░░░░██░░░░░░░░░░░██░░░░░██░░░░██░░██░░░░██░░░░█
                █░░░░▀██████▄░░░░░██░░░░░██░░░░██░░███████▀░░░░█
                █░░░░░░░░░░██░░░░░██░░░░░██░░░░██░░██░░░░░░░░░░█
                █░░░░▀██████▀░░░░░██░░░░░▀██████▀░░██░░░░░░░░░░█
                ─▀▄░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▄▀
                ───▀▄░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▄▀
                ─────▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀██████▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ──────────────────────█▀▀█
                ─────────────────────█▀▀▀▀█
                ─────────────────────█▀▀▀▀█
                ─────────────────────█▀▀▀▀█
                ─────────────────────█▀▀▀▀█
                ───────────────────▄▄██▀▀██▄▄
                ─
                ─
                ─
                """;
    }

    public static String stop() {
        return stop;
    }

    private static String generateStop() {
        char add = "─".charAt(0);
        String[] split = stopNative().split("\n");

        int max = Stream.of(split)
                .mapToInt(String::length)
                .max()
                .orElseThrow();
        int totalLength = 79;
        int toAdd = (totalLength - max) / 2;

        return Stream.of(split)
                .map(s -> fillEnd(s, max + toAdd, add))
                .map(s -> fillStart(s, totalLength, add))
                .collect(Collectors.joining(lineSeparator()));
    }

    private static String fillStart(String string, int length, char fill) {
        while (string.length() < length) {
            string = fill + string;
        }
        return string;
    }

    private static String fillEnd(String string, int length, char fill) {
        StringBuilder stringBuilder = new StringBuilder(string);
        while (stringBuilder.length() < length) {
            stringBuilder.append(fill);
        }
        return stringBuilder.toString();
    }
}
