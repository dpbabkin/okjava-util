//package okjava.util.condition;
//
//
//import java.util.function.BooleanSupplier;
//import java.util.function.Supplier;
//
//import static okjava.util.NotNull.notNull;
//
///**
// * @author Dmitry Babkin dpbabkin@gmail.com
// * 27.01.2022 - 22:43.
// */
//final class ResultWaiterBooleanAdapter<V> implements Waiter<Result> {
//    public static final Object NOT_NULL_OBJECT = new Object();
//    private final Waiter<V> delegate;
//
//    private ResultWaiterBooleanAdapter(Waiter<V> delegate) {
//        this.delegate = notNull(delegate);
//    }
//
//    static <V> Waiter<Result> create(Waiter<V> delegate) {
//        return new WaiterDelegateMapper<>(delegate, ResultWaiterBooleanAdapter::createResult);
//    }
//
//    private static Result createResult(Object object) {
//        assert object == null || object == Boolean.TRUE;
//        return ResultImpl.result(object != null);
//    }
//
//    @Override
//    public void cancel() {
//        delegate.cancel();
//    }
//
//    @Override
//    public Result await(long time) throws InterruptedException {
//        return createResult(delegate.await(time));
//    }
//
////    private Result createResult(Object object) {
////        assert object == null || object == NOT_NULL_OBJECT;
////        return ResultImpl.result(object != null);
////    }
//
//    @Override
//    public void onUpdate() {
//        delegate.onUpdate();
//    }
//
//    @Override
//    public Waiter<Result> withPoll(long time) {
//        return new ResultWaiterBooleanAdapter<>(delegate.withPoll(time));
//    }
//}
