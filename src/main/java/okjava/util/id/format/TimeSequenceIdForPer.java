package okjava.util.id.format;

import okjava.util.forper.ABForPer;
import okjava.util.forper.ForPer;
import okjava.util.id.timesequence.TimeSequenceId;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 15.01.2021 19:37
 */
public final class TimeSequenceIdForPer extends ABForPer<TimeSequenceId> {

    private final static ForPer<TimeSequenceId> INSTANCE = new TimeSequenceIdForPer();

    public static ForPer<TimeSequenceId> timeSequenceIdForPer() {
        return i();
    }

    public static ForPer<TimeSequenceId> i() {
        return INSTANCE;
    }

    private TimeSequenceIdForPer() {
        super(TimeSequenceIdParser.timeSequenceIdParser().getTimeSequenceIdParser(), TimeSequenceIdFormatter.timeSequenceIdFormatter().getFormatter());
    }
}
