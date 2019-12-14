package okjava.util.poller;

public class UpdaterWIthValueImpl<V> extends AbstractBaseUpdater<V, PollerWithValue<V>> implements UpdaterWithValue<V> {

    public static <V> UpdaterWithValue<V> create(V value) {
        return new UpdaterWIthValueImpl<>(value);
    }

    private final ListenerCollection<V> allListenerRegister = ListenerCollection.create(this);

    private UpdaterWIthValueImpl(V value) {
        super(PollerWithVaueImpl.create(value));
    }

    @Override
    public V get() {
        return getGenericPoller().get();
    }

    @Override
    public void accept(V value) {
       getGenericPoller().accept(value);
       super.onUpdateNative();
    }

    @Override
    public ListenerRegister<V> getAllEventListenerRegister() {
        throw new UnsupportedOperationException("not implemented //code generation template");
    }
}
