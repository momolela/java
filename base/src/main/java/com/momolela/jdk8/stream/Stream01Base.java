package com.momolela.jdk8.stream;

import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
class Book {
    String name;
    int price;

    Book(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hashNo = 7;
        return 13 * hashNo + (this.name == null ? 0 : this.name.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        Book book = (Book) obj;
        if (this == book) {
            return true;
        } else {
            if (this.name.equals(book.getName()) && this.price == book.getPrice()) {
                return true;
            } else {
                return false;
            }
        }
    }
}

public class Stream01Base {

    public static void main(String[] args) {

        // filter
        // 过滤出数字
        List<String> strList1 = Arrays.asList("5", "Sunzj", "2", "Hufy", "0");
        List<String> result1 = strList1.stream().filter((s) -> s.toUpperCase().equals(s.toLowerCase())).collect(Collectors.toList());
        System.out.println(result1);
        System.out.println("========== 1");


        // map
        // 将每个元素替换成新的元素，用于改变流中元素本身类型
        List<String> strList2 = Arrays.asList("5", "Sunzj", "2", "Hufy", "0");
        List<String> result2 = strList2.stream().map((s) -> {
            if (s.toUpperCase().equals(s.toLowerCase())) {
                return "love";
            } else {
                return s;
            }
        }).collect(Collectors.toList());
        System.out.println(result2);
        System.out.println("========== 2");


        // peek
        // 一般用于不想改变流中元素本身的类型或者只想元素的内部状态时,一般用于 debugger 的时候，如下 peek 用于打印过滤后的结果
        // peek，接收的是 Consumer，没有返回值，不会重新写入到 stream 中
        // map，接收的是 Function，有返回值，会生成新的值重新写入到 stream 中
        // 如下 .peek(s -> s.toUpperCase()) 不会起作用，因为不会返回新的值，所以不会改变 Stream
        // 但是 .peek(obj -> obj.setName("sunzj")) obj 的 name 属性是会变化的
        Stream<String> stream3 = Stream.of("5", "Sunzj", "2", "Hufy", "0");
        stream3.filter((s) -> !s.toUpperCase().equals(s.toLowerCase())).peek(System.out::println).peek(s -> s.toUpperCase()).collect(Collectors.toList());
        System.out.println("========== 3");


        // forEach
        // 迭代流中的每个数据
        Stream<String> stream4 = Stream.of("5", "Sunzj", "2", "Hufy", "0");
        stream4.forEach((s) -> System.out.print(s + " "));
        System.out.println();
        System.out.println("========== 4");


        // sorted
        // 用于排序
        // 随机打印10条数据，然后排序
        Random random5 = new Random();
        random5.ints().limit(10).sorted().forEach(System.out::println);
        System.out.println("========== 5");


        // skip
        // 跳过前 n 个元素，配合 limit 可以做分页，简单的数据类型的分页或者量小的数据可以使用，但是复杂的数据类型分页不推荐这种
        Stream<String> stream6 = Stream.of("5", "Sunzj", "2", "Hufy", "0");
        List<String> result6 = stream6.sorted().skip(3).collect(Collectors.toList());
        System.out.println(result6);
        System.out.println("========== 6");


        // limit
        // 只保留 n 个元素，配合 skip 可以做分页
        // 随机打印 10 条数据
        // random6.ints() 会生成无限的整数流
        // .limit(10) 只是按顺序只取 10 个数据
        Random random7 = new Random();
        random7.ints().limit(10).forEach(System.out::println);
        System.out.println("========== 7");


        // distinct
        // --- 字符串数组数据去重
        Stream<String> stream8 = Stream.of("AA", "BB", "CC", "BB", "BB", "CC");
        //long count8 = stream8.distinct().count();
        //System.out.println("去重后的个数：" + count8);
        List<String> result81 = stream8.distinct().collect(Collectors.toList()); // 如果上面的注释放开会报错，因为 stream8 由于 count 已经关闭了流
        System.out.println(result81);
        // --- 对象数组数据去重，通过重写对象的 equals 和 hashCode 才能直接用 distinct() 去重，distinct() 不提供按照属性对对象列表进行去重的直接实现。它是基于 hashCode() 和 equals() 工作的
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("haha", 123));
        bookList.add(new Book("xixi", 123));
        bookList.add(new Book("haha", 234));
        bookList.add(new Book("xixi", 123));
        bookList.add(new Book("keke", 456));
        bookList.add(new Book("keke", 456));
        List<Book> result82 = bookList.stream().distinct().collect(Collectors.toList());
        System.out.println(result82);
        // --- 通过对象属性对对象数组去重，自定义方法 distinctByKey
        List<Book> result83 = bookList.stream().filter(distinctByKey(b -> b.getName() + "#" + b.getPrice())).collect(Collectors.toList());
        System.out.println(result83);
        // collectingAndThen 是先归约，然后再转换成 ArrayList
        List<Book> result84 = bookList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(
                Comparator.comparing(b -> b.getName() + "#" + b.getPrice()))), ArrayList::new));
        System.out.println(result84);
        System.out.println("========== 8");


        // unordered
        // 返回一个等价（不会打乱原来的顺序）的无序流。可以返回自己，因为流已经是无序的，或者因为底层流状态被修改为无序
        Stream.of(5, 1, 2, 6, 3, 7, 4).unordered().forEach(System.out::println);
        Stream.of(5, 1, 2, 6, 3, 7, 4).unordered().parallel().forEach(System.out::println); // 可以打乱原来的顺序
        System.out.println("========== 9");


        // max
        // 取最大值
        // 返回的是 Optional
        Integer max = Stream.of(5, 1, 2, 6, 3, 7, 4).max(Integer::compareTo).get();
        System.out.println(max);
        System.out.println("========== 10");


        // min
        // 取最小值
        // 返回的是 Optional
        Integer min = Stream.of(5, 1, 2, 6, 3, 7, 4).min(Integer::compareTo).get();
        System.out.println(min);
        System.out.println("========== 11");


        // count
        // 取个数
        long count = Stream.of(5, 1, 2, 6, 3, 7, 4).count();
        System.out.println(count);
        System.out.println("========== 12");


        // reduce
        // 归约
        // --- 接受的参数是 Optional<T> reduce(BinaryOperator<T> accumulator);
        // a 是累计的结果，b 是新的值
        // 返回的是 Optional
        Integer result131 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).reduce((a, b) -> {
            System.out.println("a1=" + a + ",b1=" + b);
            return a + b;
        }).get();
        System.out.println(result131);
        // --- 接受的参数是 T reduce(T identity, BinaryOperator<T> accumulator);
        // 指定了初始值为 0，即使 stream 为空也不会返回 null
        Integer result132 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).reduce(5, (a, b) -> {
            System.out.println("a2=" + a + ",b2=" + b);
            return a + b;
        });
        System.out.println(result132);
        // --- 接受的参数是 <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
        // 将 int 类型的列表合并成 long 类型的结果
        List<Integer> numList = Arrays.asList(Integer.MAX_VALUE, Integer.MAX_VALUE);
        long result133 = numList.stream().reduce(0L, (a, b) -> a + b, (a, b) -> 0L);
        System.out.println(result133);
        // 新应用，将一个 int 类型的 ArrayList 转换成一个 String 类型的 ArrayList
        List<Integer> numList134 = Arrays.asList(1, 2, 3, 4, 5, 6);
        ArrayList<String> result134 = numList134.stream().reduce(new ArrayList<String>(), (a, b) -> {
            a.add("element-" + Integer.toString(b));
            return a;
        }, (a, b) -> null);
        System.out.println(result134);
        System.out.println("========== 13");


        // flatMap
        // 将每个元素替换成新的元素，用于改变流中元素本身类型
        // flatMap 可以将一个二维的集合映射成一个一维的集合
        Stream<String> stream141 = Stream.of("1 2", "3 4", "5 6");
        stream141.flatMap(item -> Arrays.stream(item.split(" "))).forEach(System.out::println);
        Stream<String> stream142 = Stream.of("1 2", "3 4", "5 6");
        // 可见，用map()方法，返回了一个“流中流”，需要在每个Stream元素遍历时，再加一层forEach进行遍历
        stream142.map(item -> Arrays.stream(item.split(" "))).forEach(n -> n.forEach(System.out::println));
        System.out.println("========== 14");


        // collect
        // 收集
        // collect() 方法的参数为一个 java.util.stream.Collector 类型对象，可以用 java.util.stream.Collectors 工具类提供的静态方法来生成，Collectors 类实现很多的归约操作，如Collectors.toList()、Collectors.toSet()、Collectors.joining()（joining适用于字符串流）等
        // --- Collectors.toList() 转成 List
        List<Integer> numbers151 = Arrays.asList(-1, -2, 0, 4, 5);
        List<Integer> absList151 = numbers151.stream().map(n -> Math.abs(n)).collect(Collectors.toList());
        //List<Integer> absList151 = numbers151.stream().map(n -> Math.abs(n)).collect(Collectors.toCollection(ArrayList::new));
        System.out.println(absList151);
        // --- Collectors..joining(",") 合并成 String
        List<String> strings152 = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        String mergedString152 = strings152.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", ")); // abc, bc, efg, abcd, jkl
        //String mergedString152 = strings152.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining("")); // abcbcefgabcdjkl
        //String mergedString152 = strings152.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", ", "{", "}")); // {abc, bc, efg, abcd, jkl}
        System.out.println(mergedString152);
        // --- Collectors.groupingBy(key, value) 生成一个 map
        List<Book> bookList153 = new ArrayList<>();
        bookList153.add(new Book("sunzj", 50));
        bookList153.add(new Book("hufy", 20));
        bookList153.add(new Book("sunzj", 0));
        bookList153.add(new Book("sunzj", 0));
        Map<Integer, List<Book>> result153 = bookList153.stream().collect(Collectors.groupingBy(Book::getPrice)); // {0=[Book(name=sunzj, price=0), Book(name=sunzj, price=0)], 50=[Book(name=sunzj, price=50)], 20=[Book(name=hufy, price=20)]}
        //Map<Integer, Long> result153 = bookList153.stream().collect(Collectors.groupingBy(Book::getPrice, counting())); // {0=2, 50=1, 20=1}
        System.out.println(result153);
        // --- Collectors.toMap(key, value) 生成一个 map
        Map<Integer, String> result154 = bookList153.stream().distinct().collect(Collectors.toMap(Book::getPrice, Book::getName));// {0=sunzj, 50=sunzj, 20=hufy}
        //Map<Integer, Book> result154 = bookList153.stream().distinct().collect(Collectors.toMap(Book::getPrice, Book -> Book)); // {0=Book(name=sunzj, price=0), 50=Book(name=sunzj, price=50), 20=Book(name=hufy, price=20)}
        System.out.println(result154);
        // --- Collectors.toSet() 转成 Set
        Set<Book> result155 = bookList153.stream().collect(Collectors.toSet());
        //Set<Book> result155 = bookList153.stream().collect(Collectors.toCollection(HashSet::new));
        System.out.println(result155);
        // --- Collectors.maxBy() Collectors.minBy() Collectors.summingInt() Collectors.averagingInt() Collectors.counting()
        Book maxBook = bookList153.stream().collect(Collectors.maxBy((b1, b2) -> b1.getPrice() - b2.getPrice())).get();
        System.out.println(maxBook);
        // --- Collectors.partitioningBy() 分区分组，只能分成 true 和 false 两组
        Map<Boolean, List<Book>> result156 = bookList153.stream().collect(Collectors.partitioningBy(b -> b.getPrice() > 20));
        System.out.println(result156);
        System.out.println("========== 15");


        // toArray
        List<String> stringList161 = Arrays.asList("AA", "BB", "CC", "BB", "BB", "CC");
        // --- 无参
        Object[] result161 = stringList161.stream().toArray();
        for (int i = 0; i < result161.length; i++) {
            System.out.println(result161[i]);
        }
        // --- 参数 IntFunction<A[]> generator
        String[] result162 = stringList161.stream().toArray(String[]::new);
        for (int i = 0; i < result162.length; i++) {
            System.out.println(result162[i]);
        }
        System.out.println("========== 16");


        // forEachOrdered
        List<String> stringList171 = Arrays.asList("A", "B", "C", "D");
        stringList171.stream().parallel().forEach(System.out::println); // 顺序不定
        stringList171.stream().parallel().forEachOrdered(System.out::println); // A B C D
        System.out.println("========== 17");


        // anyMatch allMatch noneMatch count
        List<String> stringList181 = Arrays.asList("a", "a", "a", "a", "b");
        boolean aa = stringList181.stream().anyMatch(str -> str.equals("a"));
        boolean bb = stringList181.stream().allMatch(str -> str.equals("a"));
        boolean cc = stringList181.stream().noneMatch(str -> str.equals("a"));
        long count18 = stringList181.stream().filter(str -> str.equals("a")).count();
        System.out.println(aa); // true
        System.out.println(bb); // false
        System.out.println(cc); // false
        System.out.println(count18); // 4
        List<String> stringList182 = new ArrayList<>();
        boolean allMatch = stringList182.stream().allMatch(e -> e.equals("a"));
        boolean anyMatch = stringList182.stream().anyMatch(e -> e.equals("a"));
        boolean noneMatch = stringList182.stream().noneMatch(e -> e.equals("a"));
        System.out.println(allMatch); // true
        System.out.println(anyMatch); // false
        System.out.println(noneMatch); // true
        System.out.println("========== 18");


        // findFirst findAny
        List<String> stringList191 = Arrays.asList("a", "b", "c", "d", "e");
        String firstString191 = stringList191.stream().findFirst().get();
        System.out.println(firstString191);
        String anyString191 = stringList191.stream().findAny().get();
        System.out.println(anyString191);
        System.out.println("========== 19");


        // mapToInt mapToDouble mapToLong
        List<String> stringList201 = Arrays.asList("3", "6", "8", "14", "15");
        stringList201.stream().mapToInt(s -> Integer.parseInt(s)).filter(num -> num % 3 == 0).forEach(System.out::println);
        System.out.println("========== 20");
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null; // putIfAbsent 返回 null，说明 map 不存在该 key，且已经插入了；
    }

}
