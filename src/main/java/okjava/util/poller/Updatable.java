package okjava.util.poller;

public interface Updatable extends Runnable {
    void onUpdate();

    @Override
    default void run() {
        onUpdate();
    }
}
