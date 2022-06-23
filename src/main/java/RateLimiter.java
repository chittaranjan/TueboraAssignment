/**
 * RateLimiter interface
 */
public interface RateLimiter {
    /**
     * Checks if its possible to consume
     * Returns true if tokens are available and decrement available tokens by 1
     */
    boolean getExecutionPermit();

    /**
     * Wraps a given Function with RateLimiter
     *
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    <T, R> Function<T, R> wrap(Function<T, R> function);
}
