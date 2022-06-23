package tokenBucket;

public interface TokenBucket {
    /**
     * Returns the maximum number of tokens that the bucket can hold at any given time.
     * @return
     */
    long getCapacity();

    /**
     * Returns the number of token available in the bucket currently
     * @return
     */
    long getNumberOfAvailableTokens();

    boolean tryConsume();

    void consume();

    void refill(long tokens);

    /**
     * Strategy for token refill
     */
    interface RefillStrategy {
        /**
         * Returns the number of tokens to add to the bucket
         * @return
         */
        long refill();
    }
}
