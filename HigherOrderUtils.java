import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HigherOrderUtils<T, U, R> {

    public static final NamedBiFunction<Double, Double, Double> add = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "add";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble + aDouble2;
        }
    };

    public static final NamedBiFunction<Double, Double, Double> subtract = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "di";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble - aDouble2;
        }
    };

    public static final NamedBiFunction<Double, Double, Double> multiply = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "mult";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble * aDouble2;
        }
    };

    public static final NamedBiFunction<Double, Double, Double> divide = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "div";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) throws ArithmeticException {
            if (aDouble2.equals(0d)) {
                throw new ArithmeticException("Division by zero");
            }
            return aDouble / aDouble2;
        }
    };

    /**
     * Applies a given list of bifunctions -- functions that take two arguments of a certain type
     * and produce a single instance of that type -- to a list of arguments of that type. The
     * functions are applied in an iterative manner, and the result of each function is stored in
     * the list in an iterative manner as well, to be used by the next bifunction in the next
     * iteration. For example, given
     * List<Double> args = Arrays.asList(1d, 1d, 3d, 0d, 4d), and
     * List<NamedBiFunction<Double, Double, Double>> bfs = [add, multiply, add, divide],
     * <code>zip(args, bfs)</code> will proceed iteratively as follows:
     * - index 0: the result of add(1,1) is stored in args[1] to yield args = [1,2,3,0,4]
     * - index 1: the result of multiply(2,3) is stored in args[2] to yield args = [1,2,6,0,4]
     * - index 2: the result of add(6,0) is stored in args[3] to yield args = [1,2,6,6,4]
     * - index 3: the result of divide(6,4) is stored in args[4] to yield args = [1,2,6,6,1]
     *
     * @param args:        the arguments over which <code>bifunctions</code> will be applied.
     * @param bifunctions: the list of bifunctions that will be applied on <code>args</code>.
     * @param <T>:         the type parameter of the arguments (e.g., Integer, Double)
     * @return the item in the last index of <code>args</code>, which has the final
     * result of all the bifunctions being applied in sequence.
     */
    public static <T> T zip(List<T> args, List<NamedBiFunction<T, T, T>> bifunctions) {
        int index = 0;
        for (NamedBiFunction<T, T, T> biFunction : bifunctions) {
            T result = biFunction.apply(args.get(index), args.get(++index));
            args.set(index, result);
        }
        return args.get(index);
    }

    interface NamedBiFunction<T, U, R> extends BiFunction<T, U, R> {
        String name();
    }

    static class FunctionComposition<T, U, R>{
        BiFunction<Function<T, U>, Function<U, R>, Function<T, R>> composition = (f, g) -> g.compose(f);
    }

    public static void main(String[] args) {
        List<Double> argsList = Arrays.asList(1d, 1d, 3d, 0d, 4d);
        List<NamedBiFunction<Double, Double, Double>> bfs = Arrays.asList(add, multiply, add, divide);
        System.out.println("Result " + zip(argsList, bfs));
        FunctionComposition<Character, Integer, Long> a = new FunctionComposition<Character, Integer, Long>();
        Function<Character, Integer> duplicate = c -> Integer.valueOf(c);
        Function<Integer, Long> length = s -> s*2l;
        System.out.println(a.composition.apply(duplicate, length).apply('z'));
        System.out.println((byte)'z');
    }
}
