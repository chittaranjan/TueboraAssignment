/**
 * Interface to represent a Function
 */
public interface Function<T, R> {
    /**
     * performs the operation of the function
     * Input Type : T
     * Return type : R
     * @param t
     * @return
     */
    R apply(T t);
}
