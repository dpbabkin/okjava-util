package okjava.util.condition;

final class ResultImpl implements Result {

    private final boolean result;

    static Result result(boolean result) {
        return new ResultImpl(result);
    }

    private ResultImpl(boolean result) {
        this.result = result;
    }

    @Override
    public boolean get() {
        return result;
    }
}
