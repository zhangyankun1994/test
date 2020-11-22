package main;

import java.util.*;

/**
 * 职位排序
 * @author zhangyankun
 * @date 2020.11.22
 */
public class TheSort {

    /**
     * null值比较常量
     */
    public static final String NULL = "Null";

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) {
        // 预期的排序完成之后的顺序集合
        List<Person> expected = new ArrayList<>();
        expected.add(new Person(2,"2","E,A"));
        expected.add(new Person(4,"4","C,B"));
        expected.add(new Person(3,"3","C"));
        expected.add(new Person(1,"1","D"));
        expected.add(new Person(6,"6","F"));
        expected.add(new Person(5,"5","Null"));

        // 准备处理的乱序集合
        List<Person> actually = new ArrayList<>();
        actually.add(new Person(1,"1","D"));
        actually.add(new Person(2,"2","E,A"));
        actually.add(new Person(3,"3","C"));
        actually.add(new Person(4,"4","C,B"));
        actually.add(new Person(5,"5","Null"));
        actually.add(new Person(6,"6","F"));

        // 开始测试方法
        assertEquals(expected, actually);
    }

    /**
     * 测试用例
     * 打印预期和排序后的集合的字符串
     * 并通过equals比对结果一致
     * @param expected 预期集合
     * @param actually 待排序集合
     */
    public static void assertEquals(List<Person> expected, List<Person> actually) {
        // 调用核心排序处理
        sort(actually);
        // 结果打印
        System.out.println("预期 "+expected.toString());
        System.out.println("排序后 "+actually.toString());
        System.out.println("比对是否一致 " + expected.toString()
                .equals(actually.toString()));
    }

    /**
     * 排序处理
     * 核心思路:
     * 一、判断o1和o2的post值是否包含Null,如果包含直接返回排序结果
     * 二、通过逗号分隔post值, 并放入TreeSet, 通过TreeSet获取
     *    自然排序后的第一个值
     * 三、判断o2中是否包含该值, 从而返回排序结果
     * 四、对于最高级职位相同的人员采用原本的顺序
     * @param personList
     */
    public static void sort(List<Person> personList) {
        Collections.sort(personList, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                // 判断Null值
                if (NULL.equals(o1.getPost())) {
                    return 1;
                }
                if (NULL.equals(o2.getPost())) {
                    return -1;
                }

                // 获取post值全部放入TreeSet
                String[] o1Str = o1.getPost().split(",");
                String[] o2Str = o2.getPost().split(",");
                TreeSet<String> treeSet = new TreeSet();
                for (String s : o1Str) {
                    treeSet.add(s);
                }
                for (String s : o2Str) {
                    treeSet.add(s);
                }
                // 取出自然排序之后最大值
                String first = treeSet.first();

                // 判断o2是否包含此最大值, 并返回结果
                if (o2.getPost().contains(first)) {
                    return 1;
                }
                return -1;
            }
        });
    }
}

/**
 * person对象
 */
class Person{
    private Integer id;
    private String name;
    private String post;
    public Person() {
    }
    public Person(Integer id, String name, String post) {
        this.id = id;
        this.name = name;
        this.post = post;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", post='" + post + '\'' +
                '}';
    }
}
