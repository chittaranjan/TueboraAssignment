package tokenBucket;

public class TokenBucketImpl implements TokenBucket {
    private RefillStrategy refillStrategy;
    private long capacity;
    private long size;

    public TokenBucketImpl(){
        this.capacity = 0;
        this.refillStrategy = null;
        this.size = 0;
    }

    public TokenBucketImpl(long capacity, long initialTokens) {
        assert capacity > 0;
        assert initialTokens <= capacity;

        this.capacity = capacity;
        this.size = initialTokens;
    }

    public TokenBucketImpl(long capacity, long initialTokens, RefillStrategy refillStrategy) {
        this(capacity, initialTokens);
        this.refillStrategy = refillStrategy;
    }

    @Override
    public long getCapacity() {
        return this.capacity;
    }

    @Override
    public synchronized long getNumberOfAvailableTokens() {
        assert refillStrategy != null;
        refill(refillStrategy.refill());
        return this.size;
    }

    @Override
    public synchronized boolean tryConsume() {
        assert refillStrategy != null;
        refill(refillStrategy.refill());
        if (size > 0) {
            size--;
            return true;
        }
        return false;
    }

    @Override
    public void consume() {
        while(true) {
            if (tryConsume()) {
                break;
            }
        }
    }

    @Override
    public synchronized void refill(long tokens) {
        long newTokens = Math.min(capacity, Math.max(0, tokens));
        size = Math.max(0, Math.min(size + newTokens, capacity));
    }


    public static class Builder {
        private TokenBucketImpl tokenBucket = new TokenBucketImpl();

        public Builder withCapacity(long tokens) {
            assert tokens > 0;
            tokenBucket.capacity = (Long.valueOf(tokens));
            return this;
        }

        public Builder withInitialTokens(long initialTokens) {
            assert initialTokens > 0;
            tokenBucket.size = (initialTokens);
            return this;
        }

        public Builder withFixedIntervalRefillStrategy(long refillTokens, long windowSize) {
            return withRefillStrategy(new FixedIntervalRefillStrategy(refillTokens, windowSize));
        }

        public Builder withRefillStrategy(TokenBucket.RefillStrategy refillStrategy) {
            tokenBucket.refillStrategy = (refillStrategy);
            return this;
        }

        public TokenBucket build() {
            return this.tokenBucket;
        }
    }
}
