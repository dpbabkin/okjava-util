package okjava.util.condition;

import java.util.function.Supplier;

public interface Result extends Supplier<Boolean> {

    default void assertTrue() {
        assertTrue(AssertionError::new);
    }

    default void assertTrue(String message) {
        assertTrue(() -> new AssertionError(message));
    }

    default void assertTrue(Supplier<AssertionError> supplier) {
        if (!get()) {
            throw supplier.get();
        }
    }
}
