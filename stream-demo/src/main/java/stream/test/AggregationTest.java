package stream.test;

import stream.model.Person;

import java.util.*;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/9/6 10:04
 * @description 聚合测试类，包括max、min、sum、count
 */
public class AggregationTest {
    public static void main(String[] args) {
        findingLongest();
        findingMax();
        maxmumSalary();
        count();
    }

    /**
     * 获取String集合中最长的元素。
     */
    public static void findingLongest() {
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");

        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());
    }

    /**
     * 获取Integer集合中的最大值
     */
    public static void findingMax() {
        List<Integer> list = Arrays.asList(7, 6, 9, 4, 11, 6);

        // 自然排序
        Optional<Integer> max = list.stream().max(Integer::compareTo);
        // 自定义排序
        Optional<Integer> max2 = list.stream().max((o1, o2) -> o2 - o1);
        System.out.println("自然排序的最大值：" + max.get());
        System.out.println("自定义排序的最大值：" + max2.get());
    }

    /**
     * 获取员工薪资最高的人
     */
    public static void maxmumSalary() {
        List<Person> personList = Person.assemblePersonList();

        Optional<Person> max = personList.stream().max(Comparator.comparingInt(Person::getSalary));
        System.out.println("员工工资最大值：" + max.get().getSalary());
    }

    public static void count() {
        List<Integer> list = Arrays.asList(7, 6, 4, 8, 2, 11, 9);

        long count = list.stream().filter(x -> x > 6).count();
        System.out.println("list中大于6的元素个数：" + count);
    }
}
