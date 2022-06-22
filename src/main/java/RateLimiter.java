
public interface RateLimiter {
    boolean tryConsume();

    <T, R> Function<T, R> wrap(Function<T, R> function);
}
