package okjava.util.id;

import okjava.util.id.timesequence.TimeSequenceId;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:30.
 */
public class TimeSequenceIdGeneratorTest extends BaseIdGeneratorTest {

    private final IdGenerator<TimeSequenceId> timeBucketCountIdGenerator = TimeSequenceIdGenerator.timeSequenceIdGenerator();

    @Override
    IdGenerator<TimeSequenceId> getIdGenerator() {
        return timeBucketCountIdGenerator;
    }
}
