/**
 * Rate Limited Function
 * This is essentially a Function decorated with a rate limiter instance
 * @param <T>
 * @param <R>
 */
public class RateLimitedFunction<T, R> implements Function<T, R>{
    private RateLimiter rateLimiter;
    private Function<T, R> function;

    public RateLimitedFunction(RateLimiter rateLimiter, Function<T, R> function) {
        this.rateLimiter = rateLimiter;
        this.function = function;
    }

    /**
     * When a rate limited function is applied, the rate limiter first tries to get execution permit
     * if permits are available, function gets executed
     * Otherwise throws an IllegalStateException
     * @param t
     * @return
     */
    @Override
    public R apply(T t) {
        if (!rateLimiter.getExecutionPermit()) {
            throw new IllegalStateException("Rate Limited.. try after some time..");
        }
        return function.apply(t);
    }
}
