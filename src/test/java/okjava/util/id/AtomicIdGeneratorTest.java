package okjava.util.id;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 * 5/6/2019
 * 23:30.
 */
public class AtomicIdGeneratorTest extends BaseIdGeneratorTest<Long> {

    @Override
    IdGenerator<Long> getIdGenerator() {
        return AtomicIdGenerator.i();
    }
}
