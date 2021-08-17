package com.momolela.jdk8.lambda;

class Item {
    public Item(int id, String name, double price) {
    }

    public Item() {
    }
}

interface ItemCreatorBlankConstruct {
    Item getItem();
}

interface ItemCreatorParamConstruct {
    Item getItem(int id, String name, double price);
}

/**
 * 构造方法的引用
 *
 * 一般我们需要声明接口，该接口作为对象的生成器，通过 类名::new 的方式来实例化对象，然后调用方法返回对象。
 */
public class Lambda03UseCase2 {
    public static void main(String[] args) {
        ItemCreatorBlankConstruct creator1 = () -> new Item();
        Item item1 = creator1.getItem();

        ItemCreatorBlankConstruct creator2 = Item::new;
        Item item2 = creator2.getItem();

        ItemCreatorParamConstruct creator3 = Item::new;
        Item item3 = creator3.getItem(112, "鼠标", 135.99);
    }
}
