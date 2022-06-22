import tokenBucket.TokenBucket;
import tokenBucket.TokenBucketImpl;

/**
 * This is implementation of RateLimiter with a custom TokenBucket algorithm
 */
public class RateLimiterImplWithTokenBucket implements RateLimiter {
    private TokenBucket tokenBucket;

    public RateLimiterImplWithTokenBucket(long capacity, long initialTokens, long refillTokens, long window) {
        this.tokenBucket = new TokenBucketImpl.Builder()
                .withCapacity(capacity)
                .withInitialTokens(initialTokens)
                .withFixedIntervalRefillStrategy(refillTokens, window)
                .build();
    }

    @Override
    public boolean tryConsume() {
        return tokenBucket.tryConsume();
    }

    @Override
    public <T, R> Function<T, R> wrap(Function<T, R> function) {
        return new RateLimitedFunction<T, R>(this, function);
    }
}
