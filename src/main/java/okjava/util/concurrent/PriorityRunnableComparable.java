package okjava.util.concurrent;

/**
 * @author Dmitry Babkin dpbabkin@gmail.com
 *         11/26/2016
 *         16:31.
 */
public interface PriorityRunnableComparable<P extends Comparable<P>> extends PriorityRunnable<P>, Comparable<PriorityRunnable<P>> {

    @Override
    default int compareTo(PriorityRunnable<P> o) {
        return this.getPriority().compareTo(o.getPriority());
    }


    static PriorityRunnableComparable<Boolean> fromRunnable(Runnable runnable) {
        return PriorityRunnableComparable.fromRunnable(PriorityRunnable.fromRunnable(runnable));
    }

    static <P extends Comparable<P>> PriorityRunnableComparable<P> fromRunnable(PriorityRunnable<P> priorityRunnable) {
        return new PriorityRunnableComparable<P>() {

            @Override
            public void run() {
                priorityRunnable.run();
            }

            @Override
            public P getPriority() {
                return priorityRunnable.getPriority();
            }
        };
    }
}
