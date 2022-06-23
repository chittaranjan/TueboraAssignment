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

    /**
     * Tries to consume a token if available
     * @return
     */
    boolean tryConsume();

    /**
     * Refills the bucket with tokens
     * @param tokens
     */
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
