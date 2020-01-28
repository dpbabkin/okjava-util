package okjava.util.thread;

import org.slf4j.Logger;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadFactory;

public interface ThreadsFactory {

    ForkJoinPool.ForkJoinWorkerThreadFactory createForkJoinWorkerThreadFactory();

    ThreadFactory createThreadFactory();

    ThreadFactory createThreadFactory(String prefix);

    ThreadFactory createThreadFactory(String prefix, int priority);

    Thread.UncaughtExceptionHandler create();

    Thread.UncaughtExceptionHandler create(Logger logger);
}
