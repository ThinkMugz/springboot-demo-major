package stream.test;

import stream.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2022/9/6 10:23
 * @description 映射测试类
 */
public class MapTest {
    public static void main(String[] args) {
//        mapTest();
//        mapTest2();
//        mapTest3();
//        mapTest4();
        mapTest5();
    }

    /**
     * 英文字符串数组的元素全部改为大写。整数数组每个元素+3
     */
    public static void mapTest() {
        String[] strArr = {"abcd", "bcdd", "defde", "fTr"};
        List<String> strList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());

        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> intListNew = intList.stream().map(x -> x + 3).collect(Collectors.toList());

        System.out.println("每个元素大写：" + strList);
        System.out.println("每个元素+3：" + intListNew);
    }

    /**
     * 将员工的薪资全部增加1000
     */
    public static void mapTest2() {
        List<Person> personList = Person.assemblePersonList();
        // 不改变原来员工集合的方式
        List<Person> personListNew = personList.stream().map(person -> {
            Person personNew = new Person(person.getName(), 0, 0, null, null);
            personNew.setSalary(person.getSalary() + 10000);
            return personNew;
        }).collect(Collectors.toList());
        System.out.println("一次改动前：" + personList.get(0).getName() + "-->" + personList.get(0).getSalary());
        System.out.println("一次改动后：" + personListNew.get(0).getName() + "-->" + personListNew.get(0).getSalary());

        // 改变原来员工集合的方式
        List<Person> personListNew2 = personList.stream().map(person -> {
            person.setSalary(person.getSalary() + 10000);
            return person;
        }).collect(Collectors.toList());
        List<Person> personListNew3 = personList.stream().peek(person -> person.setSalary(person.getSalary() + 10000)).collect(Collectors.toList());
        System.out.println("二次改动前：" + personList.get(0).getName() + "-->" + personListNew.get(0).getSalary());
        System.out.println("二次改动后：" + personListNew2.get(0).getName() + "-->" + personListNew2.get(0).getSalary());
        System.out.println("二次改动后：" + personListNew3.get(0).getName() + "-->" + personListNew3.get(0).getSalary());
    }

    public static void mapTest3() {
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> listNew = list.stream().flatMap(s -> {
            // 将每个元素转换成一个stream
            String[] split = s.split(",");
            return Arrays.stream(split);
        }).collect(Collectors.toList());

        System.out.println("处理前的集合：" + list);
        System.out.println("处理后的集合：" + listNew);
    }

    public static void mapTest4() {
        List<String> stringList = Arrays.asList("mu", "CSDN", "hello",
                "world", "quickly");
        stringList.stream().mapToInt(String::length).forEach(System.out::println);
        List<Integer> integerList = Arrays.asList(4, 5, 2, 1, 6, 3);
        integerList.stream().mapToInt(x -> x + 1000).forEach(System.out::println);
    }

    public static void mapTest5() {
        List<Double> doubleList = Arrays.asList(1.0, 2.0, 3.0, 4.0, 2.0);
        double average = doubleList.stream().mapToDouble(Number::doubleValue).average().getAsDouble();
        double sum = doubleList.stream().mapToDouble(Number::doubleValue).sum();
        double max = doubleList.stream().mapToDouble(Number::doubleValue).max().getAsDouble();
        System.out.println("平均值：" + average + "，总和：" + sum + "，最大值：" + max);
    }
}
