/**
 * Brandon Wong
 * CSE 216: HW3
 */

import java.util.*;
import java.util.stream.*;
import java.util.function.UnaryOperator;

public class BijectionGroup {
    //Heap's algorithm (https://www.baeldung.com/java-array-permutations)
    private static <T> void addPermutation(List<T> input, List<List<T>> perms) {
        List<T> perm = new ArrayList<>();
        for(int i = 0; i < input.size(); i++) {
            perm.add(input.get(i));
        }
        perms.add(perm);
    }

    private static <T> void swap(List<T> input, int a, int b) {
        T tmp = input.get(a);
        input.set(a, input.get(b));
        input.set(b, tmp);
    }

    public static <T> void getAllPermutations(int n, List<T> elements, List<List<T>> perms) {
        if(n == 1) {
            addPermutation(elements, perms);
        } else {
            for(int i = 0; i < n-1; i++) {
                getAllPermutations(n - 1, elements, perms);
                if(n % 2 == 0) {
                    swap(elements, i, n-1);
                } else {
                    swap(elements, 0, n-1);
                }
            }
            getAllPermutations(n - 1, elements, perms);
        }
    }
    public static <T> ArrayList<ArrayList<T>> permutation(ArrayList<T> values) {
        ArrayList<ArrayList<T>> perms = new ArrayList<>();
        if (values.isEmpty()) {
            perms.add(new ArrayList<>());
            return perms;
        }
        T first = values.remove(0);
        ArrayList<ArrayList<T>> permutations = permutation(values);
        for (ArrayList<T> permutated : permutations) {
            for (int i = 0; i <= permutated.size(); i++) {
                ArrayList<T> newPerm = new ArrayList<>(permutated);
                newPerm.add(i, first);
                perms.add(newPerm);
            }
        }
        return perms;
    }
    public static <T> Set<UnaryOperator<T>> bijectionsOf(Set<T> domain) {
        Set<UnaryOperator<T>> bijections = new HashSet<>();
        List<T> setList = new ArrayList<T>();
        setList.addAll(domain);
        List<List<T>> perms = new ArrayList<>();
        getAllPermutations(setList.size(), setList, perms);
        for (List<T> permutation : perms){
            bijections.add(t -> permutation.get(setList.indexOf(t)));
        }
        return bijections;
    }

    public static <T> Group<UnaryOperator<T>> bijectionGroup(Set<T> domain) {
        Group<UnaryOperator<T>> group = new Group<UnaryOperator<T>>() {
            Set<UnaryOperator<T>> bijections = bijectionsOf(domain);
            @Override
            public UnaryOperator<T> binaryOperation(UnaryOperator<T> one, UnaryOperator<T> other) {
                return (UnaryOperator<T>) one.andThen(other);
            }

            @Override
            public UnaryOperator<T> identity() {
                return t -> t;
            }

            @Override
            public UnaryOperator<T> inverseOf(UnaryOperator<T> f){
                for (UnaryOperator<T> g : bijections ){
                    if (domain.stream().allMatch( x -> g.apply(f.apply(x)).equals(x)))return g;
                }

                return f;
            }
        };

        return group;
    }
    public static void main(String... args) {
        //bijectionsOf
        System.out.println("bijectionsOf:");
        Set<Integer> a_few = Stream.of(1, 2, 3).collect(Collectors.toSet());
// you have to figure out the data type in the line below
        Set<UnaryOperator<Integer>> bijections = bijectionsOf(a_few);
        bijections.forEach(aBijection -> {
            a_few.forEach(n -> System.out.printf("%d --> %d; ", n, aBijection.apply(n)));
            System.out.println();
        });
        //bijectionGroup
        //Set<Integer> a_few = Stream.of(1, 2, 3).collect(Collectors.toSet());
// you have to figure out the data types in the lines below
// some of these data types are functional objects, so look into java.util.function.Function
        System.out.println("bijectionGroup:");
        Group<UnaryOperator<Integer>> g = bijectionGroup(a_few);
        UnaryOperator<Integer> f1 = bijectionsOf(a_few).stream().findFirst().get();
        UnaryOperator<Integer> f2 = g.inverseOf(f1);
        UnaryOperator<Integer> id = g.identity();
        System.out.print("f1: ");
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, f1.apply(n)));
        System.out.print("\ninverse: ");
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, f2.apply(n)));
        System.out.print("\nidentity: ");
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, id.apply(n)));
    }
}

