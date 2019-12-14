package okjava.util.poller;

import okjava.util.thread.ExecutorFactory;

import java.util.concurrent.Executor;

import static okjava.util.NotNull.notNull;

abstract class AbstractBaseUpdater<V, P extends Poller<V>> implements Updater<V,P> {

    private final P genericPoller;
    private final ListenerCollection<V> listenerRegister = ListenerCollection.create(this);

    //private final Runnable updateGenericPoller = genericPoller::onUpdate;
    private final Runnable updateListenerRegister = listenerRegister::onUpdate;
    private final Runnable updateAll = this::onUpdateNative;

    protected static final Executor EXECUTOR = ExecutorFactory.getInstance().getExecutor();

    AbstractBaseUpdater(P genericPoller) {
        this.genericPoller=notNull(genericPoller);
    }

    @Override
    public P getGenericPoller() {
        return genericPoller;
    }

    @Override
    public ListenerRegister<V> getListenerRegister() {
        return listenerRegister;
    }

    protected void onUpdateAsync() {
        EXECUTOR.execute(updateAll);
    }

    protected void onUpdateNative() {
        EXECUTOR.execute(updateListenerRegister);
    }
}
