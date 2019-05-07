package okjava.util.id;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import okjava.util.id.timesequence.TimeSequenceId;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:30.
 */
public class TimeBucketCountIdGeneratorTest extends BaseIdGeneratorTest {

    private final IdGenerator<TimeSequenceId> timeBucketCountIdGenerator = TimeBucketCountIdGenerator.timeSequenceIdGenerator();

    @Override
    IdGenerator<TimeSequenceId> getIdGenerator() {
        return timeBucketCountIdGenerator;
    }
}
