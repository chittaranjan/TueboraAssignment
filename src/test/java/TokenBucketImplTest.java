import org.junit.Test;
import tokenBucket.TokenBucket;
import tokenBucket.TokenBucketImpl;

public class TokenBucketImplTest {
    private static final long capacity = 10;
    private TokenBucket.RefillStrategy refillStrategy = null;
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


}
