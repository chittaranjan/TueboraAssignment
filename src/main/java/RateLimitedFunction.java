public class RateLimitedFunction<T, R> implements Function<T, R>{
    private RateLimiter rateLimiter;
    private Function<T, R> function;

    public RateLimitedFunction(RateLimiter rateLimiter, Function<T, R> function) {
        this.rateLimiter = rateLimiter;
        this.function = function;
    }

    @Override
    public R apply(T t) {
        rateLimiter.tryConsume();
        return function.apply(t);
    }
}
