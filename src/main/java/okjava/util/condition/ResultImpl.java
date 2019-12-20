package okjava.util.condition;

final class ResultImpl implements Result {

    private final Boolean result;

    static Result result(boolean result) {
        return new ResultImpl(result);
    }

    private ResultImpl(boolean result) {
        this.result = result;
    }

    @Override
    public Boolean get() {
        return result;
    }
}
