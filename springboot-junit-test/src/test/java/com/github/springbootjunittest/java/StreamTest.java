package com.github.springbootjunittest.java;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author chenjianhua
 * @date 2021/5/19
 */
public class StreamTest {
    private List<Book> bookList = new LinkedList<>();

    private Book book1 = new Book(1L, "数学之美", new BigDecimal(21));
    private Book book2 = new Book(2L, "java编程思想", new BigDecimal(22));
    private Book book3 = new Book(3L, "非暴力沟通", new BigDecimal(23));

    @BeforeEach
    public void before() {
        bookList.add(book1);
        bookList.add(book3);
        bookList.add(book2);
    }

    @Test
    @DisplayName("排序 sorted")
    public void testSort() {
        List<Book> sortedList = bookList.stream().sorted(Comparator.comparing(Book::getId)).collect(Collectors.toList());
        List<Book> expectedList = new LinkedList<>();
        expectedList.add(book1);
        expectedList.add(book2);
        expectedList.add(book3);
        Assertions.assertEquals(expectedList, sortedList);

        sortedList = bookList.stream().sorted(Comparator.comparing(Book::getId).reversed()).collect(Collectors.toList());
        expectedList = new LinkedList<>();
        expectedList.add(book3);
        expectedList.add(book2);
        expectedList.add(book1);
        Assertions.assertEquals(expectedList, sortedList);
    }

    @Test
    @DisplayName("筛选操作filter")
    public void testFilter() {
        List<Book> sortedList = bookList.stream().filter(item -> item.getId() >= 2L).sorted(Comparator.comparing(Book::getId)).collect(Collectors.toList());
        List<Book> expectedList = new LinkedList<>();
        expectedList.add(book2);
        expectedList.add(book3);
        Assertions.assertEquals(expectedList, sortedList);
    }

    @Test
    @DisplayName("map映射操作，就像一个管道，可以将流中的元素`T`通过一个函数进行映射，返回一个新的元素`R`")
    public void testMap() {
        List<String> bookNameList = bookList.stream().sorted(Comparator.comparing(Book::getId)).map(item -> item.getName()).distinct().collect(Collectors.toList());
        List<String> expectedList = new LinkedList<>();
        expectedList.add(book1.getName());
        expectedList.add(book2.getName());
        expectedList.add(book3.getName());
        Assertions.assertEquals(expectedList, bookNameList);
    }

    @Test
    @DisplayName("reduce就是减少的意思，它会将集合中的所有值根据规则计算，最后只返回一个结果")
    public void testReduce() {
        // 求和
        Optional<BigDecimal> resultOpt = bookList.stream().map(Book::getPrice).reduce((x, y) -> x.add(y));
        resultOpt.ifPresent(result -> {
            Assertions.assertEquals(66, result.intValue());
        });
        BigDecimal result = bookList.stream().map(Book::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        Assertions.assertEquals(66, result.intValue());
        // 求最小值
        Optional<Long> minIdOpt = bookList.stream().map(Book::getId).reduce((x, y) -> x >= y ? y : x);
        minIdOpt.ifPresent(minId -> {
            Assertions.assertEquals(1, minId);
        });
    }

    @Test
    @DisplayName("joining 连接字符串")
    public void testJoining() {
        String nameStr = bookList.stream().sorted(Comparator.comparing(Book::getId)).map(Book::getName).collect(Collectors.joining(","));
        StringBuilder sb = new StringBuilder();
        sb.append(book1.getName()).append(",");
        sb.append(book2.getName()).append(",");
        sb.append(book3.getName());
        Assertions.assertEquals(sb.toString(), nameStr);
    }

    @Test
    @DisplayName("groupingBy 分组")
    public void testGroupingBy() {
        Map<Long, List<Book>> bookMap = bookList.stream().collect(Collectors.groupingBy(Book::getId));
        // 等同于
        bookMap = bookList.stream().collect(Collectors.groupingBy(Book::getId, Collectors.toList()));
        Optional<List<Book>> bookListOpt = Optional.ofNullable(bookMap.get(book1.getId()));
        bookListOpt.ifPresent(books -> {
            Assertions.assertEquals(book1, books.get(0));
        });
    }


    @Test
    @DisplayName("toMap 转换为Map")
    public void testToMap() {
        Map<Long, Book> bookMap = bookList.stream().collect(Collectors.toMap(Book::getId, Function.identity()));
        Map<Long, String> bookNameMap = bookList.stream().collect(Collectors.toMap(Book::getId, Book::getName));
        Optional<Book> bookOptional = Optional.ofNullable(bookMap.get(book1.getId()));
        bookOptional.ifPresent(book -> {
            Assertions.assertEquals(book1, book);
        });
        Optional<String> bookNameOptional = Optional.ofNullable(bookNameMap.get(book1.getId()));
        bookNameOptional.ifPresent(bookName -> {
            Assertions.assertEquals(book1.getName(), bookName);
        });
        // toMap 如果集合对象有重复的key，会报错Duplicate key ....
        // 可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
        bookList.add(book1);
        bookMap = bookList.stream().collect(Collectors.toMap(Book::getId, book -> book, (k1, k2) -> k1));
        bookOptional = Optional.ofNullable(bookMap.get(book1.getId()));
        bookOptional.ifPresent(book -> {
            Assertions.assertEquals(book1, book);
        });
    }

    /**
     * 分区与分组的区别在于，分区是按照`true`和`false`来分的，因此`partitioningBy` 接受的参数的 lambda 也是 T -> boolean
     */
    @Test
    @DisplayName("partitioningBy 分区")
    public void testPartitioningBy() {
        Map<Boolean, List<Book>> partitioningMap = bookList.stream().collect(Collectors.partitioningBy(item -> item.getId() >= 2));
        // 等同于
        partitioningMap = bookList.stream().collect(Collectors.groupingBy(item -> item.getId() >= 2, Collectors.toList()));
        Optional<List<Book>> bookListOpt = Optional.ofNullable(partitioningMap.get(false));
        bookListOpt.ifPresent(bookList -> {
            Assertions.assertEquals(book1, bookList.get(0));
        });
    }
}
