/**
 * Brandon Wong
 * CSE 216: HW3
 */

import java.util.*;
import java.lang.*;
import java.util.function.BiFunction;
public class HigherOrderUtils {
    public static interface NamedBiFunction<T, U, R> extends java.util.function.BiFunction<T, U, R> {
        String name();
    }
    public static NamedBiFunction<Double, Double, Double> add = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "plus";
        }

        @Override
        public Double apply(Double a, Double b) {
            return a + b;
        }
    };
    public static NamedBiFunction<Double, Double, Double> subtract = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "minus";
        }

        @Override
        public Double apply(Double a, Double b) {
            return a - b;
        }
    };
    public static NamedBiFunction<Double, Double, Double> multiply = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "mult";
        }

        @Override
        public Double apply(Double a, Double b) {
            return a * b;
        }
    };
    public static NamedBiFunction<Double, Double, Double> divide = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "div";
        }

        @Override
        public Double apply(Double a, Double b) {
            if(b == 0.0) throw new java.lang.ArithmeticException("Cannot divide by 0.");
            else return a / b;
        }
    };
    /**
     * Applies a given list of bifunctions -- functions that take two arguments of a certain type
     * and produce a single instance of that type -- to a list of arguments of that type. The
     * functions are applied in an iterative manner, and the result of each function is stored in
     * the list in an iterative manner as well, to be used by the next bifunction in the next
     * iteration. For example, given
     * List<Double> args = Arrays.asList(-0.5, 2d, 3d, 0d, 4d), and
     * List<NamedBiFunction<Double, Double, Double>> bfs = Arrays.asList(add, multiply, add, divide),
     * <code>zip(args, bfs)</code> will proceed as follows:
     * - the result of add(-0.5, 2.0) is stored in index 1 to yield args = [-0.5, 1.5, 3.0, 0.0, 4.0]
     * - the result of multiply(1.5, 3.0) is stored in index 2 to yield args = [-0.5, 1.5, 4.5, 0.0, 4.0]
     * - the result of add(4.5, 0.0) is stored in index 3 to yield args = [-0.5, 1.5, 4.5, 4.5, 4.0]
     * - the result of divide(4.5, 4.0) is stored in index 4 to yield args = [-0.5, 1.5, 4.5, 4.5, 1.125]
     *
     * @param args the arguments over which <code>bifunctions</code> will be applied.
     * @param bifunctions the list of bifunctions that will be applied on <code>args</code>.
     * @param <T> the type parameter of the arguments (e.g., Integer, Double)
     * @return the item in the last index of <code>args</code>, which has the final result
     * of all the bifunctions being applied in sequence.
     *
     * @throws IllegalArgumentException if the number of bifunction elements and the number of argument
     * elements do not match up as required.
     */
    public static <T> T zip(List<T> args, List<? extends BiFunction<T, T, T>> bifunctions) {
        if (args.size() != bifunctions.size() + 1) {
            throw new IllegalArgumentException("Invalid size for the bifunctions or args.");
        }
        for (int i = 0; i < bifunctions.size(); i++) {
            T result = bifunctions.get(i).apply(args.get(i), args.get(i + 1));
            args.set(i + 1, result);
        }
        return args.get(args.size() - 1);
    }
    public static void main(String... args) {
        List<Double> numbers = Arrays.asList(-0.5, 2d, 3d, 0d, 4d); // documentation example
        List<NamedBiFunction<Double, Double, Double>> operations = Arrays.asList(add,multiply,add,divide);
        Double d = zip(numbers, operations); // expected correct value: 1.125
// different use case, not with NamedBiFunction objects
        List<String> strings = Arrays.asList("a", "n", "t");
// note the syntax of this lambda expression
        BiFunction<String, String, String> concat = (s, t) -> s + t;
        String s = zip(strings, Arrays.asList(concat, concat)); // expected correct value: "ant"
        System.out.println("d: " + d + "\ns: " + s);   //test line
    }
}
