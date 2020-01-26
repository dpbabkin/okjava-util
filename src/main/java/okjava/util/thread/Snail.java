package okjava.util.thread;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/19/2017
 * 22:48.
 */
@Utility
public enum Snail {
    ;

    Snail(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    private static final String stop = generateStop();
    private static final String snail0 = generateSnail0();
    private static final String snail1 = generateSnail1();
    private static final String snail2 = generateSnail2();


    public static String getSnail0() {
        return snail0;
    }
    public static String generateSnail0() {
        return "\n" +
                "\n" +
                "                           .-=--.                \n" +
                "                         .' .--. '.              \n" +
                "                        :  : .-.'. :    _ _         \n" +
                "                        :  : : .': :   (o)o)     \n" +
                "                        :  '. '-' .'   ////      \n" +
                "                        _'.__'--=' '-.//i'       \n" +
                "                     .-'               /         \n" +
                "                     '---..____...---''          \n" +
                "\n";
    }

    public static String getSnail1() {
        return snail1;
    }
    public static String generateSnail1() {
        return "\n" +
                "\n" +
                "      /^\\    /^\\\n" +
                "     {  O}  {  O}\n" +
                "      \\ /    \\ /\n" +
                "      //     //       _------_\n" +
                "     //     //     ./~        ~-_\n" +
                "    / ~----~/     /              \\\n" +
                "  /         :   ./       _---_    ~-\n" +
                " |  \\________) :       /~     ~\\   |\n" +
                " |        /    |      |  :~~\\  |   |\n" +
                " |       |     |      |  \\___-~    |\n" +
                " |        \\ __/`=1C______\\.        ./\n" +
                "  \\                     ~-______-~\\.\n" +
                "  .|                                ~-_\n" +
                " /_____________________________________~~____\n" +
                " \n";
    }

    public static String getSnail2() {
        return snail2;
    }
    public static String generateSnail2() {
        return "\n" +
                "\n" +
                "         (0)_(0)                                 \n" +
                "           \\\\  \\            _____             \n" +
                "            \\\\  \\       .-'`     ``'-.        \n" +
                "             \\\\  \\_   .'              '.      \n" +
                "         _.-'    `\\  /      _..---._    \\      \n" +
                "       .'           \\/    .-'  __    '.   \\    \n" +
                "      /       ,_    |    /    (_,`\\    |  |     \n" +
                "      \\       /     |:.  \\        /    /  /    \n" +
                "       '----'`--'\\  \\:.   '-....-'   .' _/     \n" +
                "                  \\ /`-..___     _.-' -` \\.__  \n" +
                "                   |::.     `````        /   `)  \n" +
                "                    `-::___         __.-'``''`   \n" +
                "                           `````````             \n" +
                "\n";
    }

    private static String stopNative() {
        return "─\n" +
                "─\n" +
                "─\n" +
                "─────────────────────▄████▄\n" +
                "──────────────────────█▀▀█\n" +
                "─────▄▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▄\n" +
                "───▄▀░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▀▄\n" +
                "─▄▀░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▀▄\n" +
                "█░░░░▄██████▄░░████████░░▄██████▄░░▄██████▄░░░░█\n" +
                "█░░░░██░░░░░░░░░░░██░░░░░██░░░░██░░██░░░░██░░░░█\n" +
                "█░░░░▀██████▄░░░░░██░░░░░██░░░░██░░███████▀░░░░█\n" +
                "█░░░░░░░░░░██░░░░░██░░░░░██░░░░██░░██░░░░░░░░░░█\n" +
                "█░░░░▀██████▀░░░░░██░░░░░▀██████▀░░██░░░░░░░░░░█\n" +
                "─▀▄░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▄▀\n" +
                "───▀▄░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▄▀\n" +
                "─────▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀██████▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "──────────────────────█▀▀█\n" +
                "─────────────────────█▀▀▀▀█\n" +
                "─────────────────────█▀▀▀▀█\n" +
                "─────────────────────█▀▀▀▀█\n" +
                "─────────────────────█▀▀▀▀█\n" +
                "───────────────────▄▄██▀▀██▄▄\n";
    }

    public static String stop() {
        return stop;
    }

    private static String generateStop() {
        char add = '─';
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
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static String fillStart(String string, int length, char fill) {
        while (string.length() < length) {
            string = fill + string;
        }
        return string;
    }

    private static String fillEnd(String string, int length, char fill) {
        while (string.length() < length) {
            string += fill;
        }
        return string;
    }
}
