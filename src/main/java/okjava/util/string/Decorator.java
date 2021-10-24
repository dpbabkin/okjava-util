package okjava.util.string;

import java.util.function.UnaryOperator;

import static java.util.function.UnaryOperator.identity;
import static okjava.util.NotNull.notNull;


final class Decorator<O> {
    //private final String value;
    private final UnaryOperator<O> valueDecorator;

    //private DecoratedString() {
    //     this(identity());
    // }

    private Decorator(UnaryOperator<O> valueDecorator) {
        // this.value = notNull(value);
        this.valueDecorator = notNull(valueDecorator);
    }

    static <O> Decorator<O> create() {
        return new Decorator<>(identity());
    }

    Decorator<O> decorateAfter(UnaryOperator<O> afterDecorator) {
        return new Decorator<>(s -> afterDecorator.apply(this.valueDecorator.apply(s)));
    }

    Decorator<O> decorateBefore(UnaryOperator<O> beforeDecorator) {
        return new Decorator<>(s -> (this.valueDecorator.apply(beforeDecorator.apply(s))));
    }

    O decorate(O value) {
        return valueDecorator.apply(value);
    }
}

//record Decorator(UnaryOperator<String> valueDecorator) {
//
//    Decorator(UnaryOperator<String> valueDecorator) {
//        this.valueDecorator = notNull(valueDecorator);
//    }
//
//    static Decorator create() {
//        return new Decorator(identity());
//    }
//
//    Decorator decorateAfter(UnaryOperator<String> afterDecorator) {
//        return new Decorator(s -> afterDecorator.apply(this.valueDecorator.apply(s)));
//    }
//
//    Decorator decorateBefore(UnaryOperator<String> beforeDecorator) {
//        return new Decorator(s -> (this.valueDecorator.apply(beforeDecorator.apply(s))));
//    }
//
//    String decorate(String value) {
//        return valueDecorator.apply(value);
//    }
//}

