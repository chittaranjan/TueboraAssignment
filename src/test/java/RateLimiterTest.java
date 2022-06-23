import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class RateLimiterTest {
    @Test
    public void testRateLimitedFunctionWorksWithInitialTokens() throws Exception {
        RateLimiter rateLimiter = new RateLimiterImplWithTokenBucket(3, 3, 0, 1);
        Function<Integer, Integer> square = x -> x * x;
        Function<Integer, Integer> rateLimited = rateLimiter.wrap(square);
        IntStream.range(0, 3).parallel().forEach(
                x -> System.out.println(rateLimited.apply(4)));

    }

    @Test (expected = IllegalStateException.class)
    public void testRateLimitedFunctionFailsWithMoreThanInitialTokens() {
        RateLimiter rateLimiter = new RateLimiterImplWithTokenBucket(4, 4, 0, 1);
        Function<Integer, Integer> square = x -> x * x;
        Function<Integer, Integer> rateLimited = rateLimiter.wrap(square);
        IntStream.range(0, 5).parallel().forEach(
                x -> System.out.println(rateLimited.apply(4)));
    }

    @Test
    public void testRateLimitedFunctionWorksUponRetryingAfterWindowSize() throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiterImplWithTokenBucket(4, 4, 4, 1);
        Function<Integer, Integer> square = x -> x * x;
        Function<Integer, Integer> rateLimited = rateLimiter.wrap(square);
        try {
            IntStream.range(0, 5).parallel().forEach(
                    x -> System.out.println(rateLimited.apply(4)));
        } catch (IllegalStateException ise) {
            System.out.println(ise.getMessage());
        }
        Thread.sleep(1000);
        System.out.println(rateLimited.apply(4));
    }

    @Test (expected = IllegalStateException.class)
    public void testRateLimitedFunctionWorksUponRetryingBeforeWindowSize() throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiterImplWithTokenBucket(4, 4, 4, 2);
        Function<Integer, Integer> square = x -> x * x;
        Function<Integer, Integer> rateLimited = rateLimiter.wrap(square);
        try {
            IntStream.range(0, 5).parallel().forEach(
                    x -> System.out.println(rateLimited.apply(4)));
        } catch (IllegalStateException ise) {
            System.out.println(ise.getMessage());
        }
        Thread.sleep(1000);
        System.out.println(rateLimited.apply(4));
    }

    @Test
    public void testRateLimitedFunctionWithMultipleThreads() throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiterImplWithTokenBucket(4, 4, 1, 2);
        Function<Integer, Integer> square = x -> x * x;
        Function<Integer, Integer> rateLimited = rateLimiter.wrap(square);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i=0; i<10; i++) {
            executorService.execute(() -> {
                try {
                    IntStream.range(0, 5).parallel().forEach(
                            x -> System.out.println(rateLimited.apply(4)));
                } catch (IllegalStateException ise) {
                    System.out.println(ise.getMessage());
                }
            });
        }
        executorService.shutdown();
    }
}
