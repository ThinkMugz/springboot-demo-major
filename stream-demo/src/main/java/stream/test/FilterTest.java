package stream.test;

import stream.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/9/6 9:59
 * @description filter测试类
 */
public class FilterTest {
    public static void main(String[] args) {
        filter();
        filter2();
    }

    public static void filter() {
        List<Integer> list = Arrays.asList(6, 7, 3, 8, 1, 2, 9);
        Stream<Integer> stream = list.stream();
        stream.filter(x -> x > 7).forEach(System.out::println);
    }

    public static void filter2() {
        List<Person> personList = Person.assemblePersonList();

        List<String> fiterList = personList.stream().filter(x -> x.getSalary() > 8000).map(Person::getName)
                .collect(Collectors.toList());
        System.out.print("高于8000的员工姓名：" + fiterList);
    }
}
