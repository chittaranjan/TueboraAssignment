/**
 * RateLimiter interface
 */
public interface RateLimiter {
    /**
     * Checks if its possible to consume
     * @return
     */
    boolean tryConsume();

    <T, R> Function<T, R> wrap(Function<T, R> function);
}
