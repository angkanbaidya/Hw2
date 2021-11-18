import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

public class StreamUtils {

    /**
     * @param strings: the input collection of <code>String</code>s.
     * @return a collection of those <code>String</code>s in the input collection
     * that start with a capital letter.
     */
    public static Collection<String> capitalized(Collection<String> strings) {
        return strings.stream().filter(s -> s.substring(0, 1).equals(s.substring(0, 1).toUpperCase())).collect(Collectors.toList());
    }

    /**
     * Find and return the longest <code>String</code> in a given collection of <code>String</code>s.
     *
     * @param strings:the given collection of <code>String</code>s.
     * @param from_start: a <code>boolean</code> flag that decides how ties are broken.
     *                    If <code>true</code>, then the element encountered earlier in
     *                    the iteration is returned, otherwise the later element is returned.
     * @return the longest <code>String</code> in the given collection,
     * where ties are broken based on <code>from_start</code>.
     */
    public static String longest(Collection<String> strings, boolean from_start) {
        return strings.stream().max((w1, w2) -> w1.length() > w2.length() ? 1 : (w1.length() == w2.length() && from_start) ? 0 : -1).orElse(null);
    }

    /**
     * Find and return the least element from a collection of given elements that are comparable.
     *
     * @param items:      the given collection of elements
     * @param from_start: a <code>boolean</code> flag that decides how ties are broken.
     *                    If <code>true</code>, the element encountered earlier in the
     *                    iteration is returned, otherwise the later element is returned.
     * @param <T>:        the type parameter of the collection (i.e., the items are all of type T).
     * @return the least element in <code>items</code>, where ties are
     * broken based on <code>from_start</code>.
     */
    public static <T extends Comparable<T>> T least(Collection<T> items, boolean from_start) {
        return items.stream().max((t1, t2) -> t1.compareTo(t2) > 0 ? 1 : (t1.compareTo(t2) == 0 && from_start) ? 0 : -1).orElse(null);
    }

    /**
     * Flattens a map to a stream of <code>String</code>s, where each element in the list
     * is formatted as "key -> value".
     *
     * @param aMap the specified input map.
     * @param <K>  the type parameter of keys in <code>aMap</code>.
     * @param <V>  the type parameter of values in <code>aMap</code>.
     * @return the flattened list representation of <code>aMap</code>.
     */
    public static <K, V> List<String> flatten(Map<K, V> aMap){
        return aMap.keySet().stream().map(key -> key.toString() + " -> " + aMap.get(key).toString()).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        //capitalized(Arrays.asList(new String[]{"Ok,", "because", "I", "have", "Way", "too", "much", "time", "on", "my", "hands", "at", "the", "Moment", "here", "is", "a", "code", "Example", "that", "relates", "to", "your", "question"})).forEach(System.out::println);
        System.out.println(least(new ArrayList<Double>(), false));
        System.out.println(longest(Arrays.asList(new String[]{"Ok,", "because", "I", "have", "Way", "too", "much", "time", "on", "my", "hands", "at", "the", "Moment", "here", "is", "a", "mecanique", "Example", "that", "relates", "to", "your", "questions"}), false));
        Map<Double, Character> map = new HashMap<Double, Character>();
        map.put(12.0d, 'C');
        map.put(123.55d, 'Q');
        map.put(-56d, 'A');
        map.put(380d, 'W');
        map.put(1000d, 'X');
        flatten(map).forEach(System.out::println);
    }
}
