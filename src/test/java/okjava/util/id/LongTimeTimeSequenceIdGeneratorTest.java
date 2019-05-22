package okjava.util.id;

import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.fetchSequence;
import static okjava.util.id.timesequence.LongTimeSequenceIdUtils.fetchTime;
import static okjava.util.id.timesequence.TimeSequenceIdFactory.timeSequenceIdFactory;

import okjava.util.id.timesequence.TimeSequenceId;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:30.
 */
public class LongTimeTimeSequenceIdGeneratorTest extends BaseIdGeneratorTest {

    private final IdGenerator<TimeSequenceId> timeBucketCountIdGenerator = new IdGenerator<TimeSequenceId>() {
        @Override
        public TimeSequenceId generate() {
            long value = LongTimeTimeSequenceIdGenerator.timeSequenceIdGenerator().generate();
            return timeSequenceIdFactory().create(fetchTime(value), fetchSequence(value));
        }
    };


    @Override
    IdGenerator<TimeSequenceId> getIdGenerator() {
        return timeBucketCountIdGenerator;
    }
}
