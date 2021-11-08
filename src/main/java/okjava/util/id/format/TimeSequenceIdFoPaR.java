package okjava.util.id.format;

import okjava.util.fopar.ABFoPaR;
import okjava.util.fopar.FoPaR;
import okjava.util.id.timesequence.TimeSequenceId;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 15.01.2021 19:37
 */
public final class TimeSequenceIdFoPaR extends ABFoPaR<TimeSequenceId> {

    private final static FoPaR<TimeSequenceId> INSTANCE = new TimeSequenceIdFoPaR();

    public static FoPaR<TimeSequenceId> timeSequenceIdFoPaR() {
        return i();
    }

    public static FoPaR<TimeSequenceId> i() {
        return INSTANCE;
    }

    private TimeSequenceIdFoPaR() {
        super(TimeSequenceIdParser.timeSequenceIdParser(), TimeSequenceIdFormatter.timeSequenceIdFormatter());
    }
}
