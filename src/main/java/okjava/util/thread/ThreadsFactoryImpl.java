package okjava.util.thread;

import org.slf4j.Logger;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadFactory;

public class ThreadsFactoryImpl implements ThreadsFactory {
    @Override
    public ForkJoinPool.ForkJoinWorkerThreadFactory createForkJoinWorkerThreadFactory() {
        throw new UnsupportedOperationException("not implemented //code generation template");
    }

    @Override
    public ThreadFactory createThreadFactory() {
        throw new UnsupportedOperationException("not implemented //code generation template");
    }

    @Override
    public ThreadFactory createThreadFactory(String prefix) {
        throw new UnsupportedOperationException("not implemented //code generation template");
    }

    @Override
    public ThreadFactory createThreadFactory(String prefix, int priority) {
        throw new UnsupportedOperationException("not implemented //code generation template");
    }

    @Override
    public Thread.UncaughtExceptionHandler create() {
        throw new UnsupportedOperationException("not implemented //code generation template");
    }

    @Override
    public Thread.UncaughtExceptionHandler create(Logger logger) {
        throw new UnsupportedOperationException("not implemented //code generation template");
    }
}
