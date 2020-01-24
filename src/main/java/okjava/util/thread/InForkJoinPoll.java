package okjava.util.thread;

import okjava.util.annotation.Utility;
import okjava.util.check.Never;

import static okjava.util.check.Never.neverNeverCalled;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 6/19/2017
 * 22:48.
 */
@Utility
public enum InForkJoinPoll {
    ;

    InForkJoinPoll(@SuppressWarnings("unused") Never never) {
        neverNeverCalled();
    }

    public static String getSnail0() {
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
}
