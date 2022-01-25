package com.momolela.jdk8.lambda;


import java.util.ArrayList;
import java.util.Comparator;

class Item07 {
    private final int id;
    private final String name;
    private final double price;

    public Item07(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }
}

/**
 * 集合内元素的排序
 *
 * 在以前我们若要为集合内的元素排序，就必须调用 sort 方法，传入比较器匿名内部类重写 compare 方法，我们现在可以使用 lambda 表达式来简化代码。
 */
public class Lambda07UseCase6 {

    public static void main(String[] args) {
        ArrayList<Item07> list = new ArrayList<>();
        list.add(new Item07(13, "背心", 7.80));
        list.add(new Item07(11, "半袖", 37.80));
        list.add(new Item07(14, "风衣", 139.80));
        list.add(new Item07(12, "秋裤", 55.33));

        //list.sort(new Comparator<Item07>() {
        //    @Override
        //    public int compare(Item07 o1, Item07 o2) {
        //        return o1.getId()  - o2.getId();
        //    }
        //});

        //list.sort((o1, o2) -> o1.getId() - o2.getId());
        list.sort(Comparator.comparingInt(Item07::getId));

        System.out.println(list);
    }
}
