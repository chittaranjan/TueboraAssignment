package tokenBucket;

public class FixedIntervalRefillStrategy implements TokenBucket.RefillStrategy {
    private long lastRefillTime;
    private long nextRefillTime;
    private long numberOfTokensPerWindow;
    private long windowSizeInSeconds; //In seconds


    public FixedIntervalRefillStrategy(long numberOfTokensPerWindow, long windowSizeInSeconds) {
        this.numberOfTokensPerWindow = numberOfTokensPerWindow;
        this.windowSizeInSeconds = windowSizeInSeconds;
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

    @Override
    public long getDurationUntilNextRefill() {
        long now = System.currentTimeMillis();
        return Math.max(0, (nextRefillTime - now)/1000);
    }
}
