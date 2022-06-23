import org.junit.Test;
import tokenBucket.TokenBucketImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TokenBucketImplTest {
    private static final long capacity = 10;
    private MockRefillStrategy refillStrategy = new MockRefillStrategy();
    private final TokenBucketImpl tokenBucket = new TokenBucketImpl(capacity, 0, refillStrategy);

    @Test (expected = AssertionError.class)
    public void testNegativeCapacity() {
        new TokenBucketImpl(-1, 0, refillStrategy);
    }

    @Test(expected = AssertionError.class)
    public void testZeroCapacity() {
        new TokenBucketImpl(0, 0, refillStrategy);
    }

    @Test(expected = AssertionError.class)
    public void initialTokensMoreThanCapacity() {
        new TokenBucketImpl(1, 2, refillStrategy);
    }

    @Test
    public void testGetCapacity() {
        assertEquals(capacity, tokenBucket.getCapacity());
    }

    @Test
    public void testEmptyBucketHasZeroTokens() {
        assertEquals(0, tokenBucket.getNumberOfAvailableTokens());
    }

    @Test
    public void testBucketWithInitialTokens() {
        TokenBucketImpl bucket = new TokenBucketImpl(capacity, capacity, refillStrategy);
        assertEquals(capacity, bucket.getNumberOfAvailableTokens());
    }

    @Test
    public void testAddingTokenIncreasesNumTokens() {
        refillStrategy.addToken();
        assertEquals(1, tokenBucket.getNumberOfAvailableTokens());
    }

    @Test
    public void testAddingMultipleTokensIncreasesNumTokens() {
        refillStrategy.addTokens(2);
        assertEquals(2, tokenBucket.getNumberOfAvailableTokens());
    }

    @Test
    public void testAtCapacityNumTokens() {
        refillStrategy.addTokens(capacity);
        assertEquals(capacity, tokenBucket.getNumberOfAvailableTokens());
    }

    @Test
    public void testOverCapacityNumTokens() {
        refillStrategy.addTokens(capacity + 1);
        assertEquals(capacity, tokenBucket.getNumberOfAvailableTokens());
    }

    @Test
    public void testConsumingTokenDecreasesNumTokens() {
        refillStrategy.addTokens(1);
        tokenBucket.consume();
        assertEquals(0, tokenBucket.getNumberOfAvailableTokens());
    }

    @Test
    public void testConsumingMultipleTokensDecreasesNumTokens() {
        refillStrategy.addTokens(capacity);
        tokenBucket.consume();
        assertEquals(capacity - 1, tokenBucket.getNumberOfAvailableTokens());
    }


    @Test
    public void testFailedConsumeKeepsNumTokens() {
        refillStrategy.addTokens(1);
        tokenBucket.tryConsume();
        assertEquals(0, tokenBucket.getNumberOfAvailableTokens());
    }

    @Test
    public void testTryConsumeOnEmptyBucket() {
        assertFalse(tokenBucket.tryConsume());
    }

    @Test
    public void testTryConsumeOneToken() {
        refillStrategy.addToken();
        assertTrue(tokenBucket.tryConsume());
    }

    private static final class MockRefillStrategy implements TokenBucketImpl.RefillStrategy
    {
        private long numTokensToAdd = 0;

        public long refill()
        {
            long numTokens = numTokensToAdd;
            numTokensToAdd = 0;
            return numTokens;
        }

        public void addToken()
        {
            numTokensToAdd++;
        }

        public void addTokens(long numTokens)
        {
            numTokensToAdd += numTokens;
        }
    }

}
