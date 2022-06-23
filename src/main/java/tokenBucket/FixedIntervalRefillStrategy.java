package tokenBucket;

/**
 * Fixed Interval Refill Strategy
 * This provides a strategy to refill the bucket in a fixed interval
 */
public class FixedIntervalRefillStrategy implements TokenBucket.RefillStrategy {
    private long lastRefillTime;
    private long nextRefillTime;
    private long numberOfTokensPerWindow;
    private long windowSizeInSeconds; //In seconds

    /**
     * FixedIntervalRefillStrategy constructor
     * @param numberOfTokensPerWindow
     * @param windowSizeInSeconds
     */
    public FixedIntervalRefillStrategy(long numberOfTokensPerWindow, long windowSizeInSeconds) {
        this.numberOfTokensPerWindow = numberOfTokensPerWindow;
        this.windowSizeInSeconds = windowSizeInSeconds;
        long now = System.currentTimeMillis();
        lastRefillTime = now;
        nextRefillTime = now + windowSizeInSeconds * 1000;
    }

    @Override
    public long refill() {
        long now = System.currentTimeMillis();
        if (now < nextRefillTime) {
            return 0;
        }

        long numberOfWindows = Math.max(0, ((now - lastRefillTime) / 1000) / windowSizeInSeconds);
        lastRefillTime += numberOfWindows * windowSizeInSeconds;
        nextRefillTime = lastRefillTime + windowSizeInSeconds;

        return numberOfWindows * numberOfTokensPerWindow;
    }

}
