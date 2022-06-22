import java.util.stream.IntStream;

public class Application {
    public static void main(String[] args) {
        RateLimiter rateLimiter = new RateLimiterImplWithTokenBucket(10, 5, 5, 10);
        Function<Integer, Integer> square = x -> x * x;
        Function<Integer, Integer> rateLimited = rateLimiter.wrap(square);


        //System.out.println(rateLimited.apply(4));
        IntStream.range(0, 11).parallel().forEach(
                x -> System.out.println(rateLimited.apply(4)));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        IntStream.range(0, 2).parallel().forEach(
                x -> System.out.println(rateLimited.apply(4)));
    }
}
