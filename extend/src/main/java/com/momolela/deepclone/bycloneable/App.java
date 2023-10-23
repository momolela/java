package com.momolela.deepclone.bycloneable;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2023/8/31 08:58
 */
public class App {
    public static void main(String[] args) throws CloneNotSupportedException {
        House house = new House("市区", 58955);
        User user = new User("张三", 18, house);
        User cloneUser = user.clone();
        // false
        System.out.println(user == cloneUser);
    }
}
