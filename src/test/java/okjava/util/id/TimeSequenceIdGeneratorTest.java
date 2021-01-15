package okjava.util.id;

import okjava.util.id.timesequence.TimeSequenceId;

import static okjava.util.id.LongTimeSequenceIdUtils.fetchSequence;
import static okjava.util.id.LongTimeSequenceIdUtils.fetchTime;
import static okjava.util.id.timesequence.TimeSequenceIdFactory.timeSequenceIdFactory;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:30.
 */
public class TimeSequenceIdGeneratorTest extends BaseIdGeneratorTest {

    @Override
    IdGenerator<TimeSequenceId> getIdGenerator() {
        return TimeSequenceIdGenerator.timeSequenceIdGenerator();
    }
}
