package com.momolela.deepclone.byutils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.apache.commons.lang3.SerializationUtils;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2023/8/31 09:48
 */
public class App {

    public static void main(String[] args) {
        // 通过 common-lang3 的 SerializationUtils.clone 进行深度克隆
        House house1 = new House("市区", 58955);
        User user1 = new User("张三", 18, house1);
        User cloneUser1 = SerializationUtils.clone(user1);
        // false
        System.out.println(user1 == cloneUser1);

        // 通过 gson 进行深度克隆
        House house2 = new House("市区", 58955);
        User user2 = new User("张三", 18, house2);
        Gson gson = new Gson();
        // 将对象序列化为json字符串
        String userStr2 = gson.toJson(user2);
        // 然后将字符串反序列化为对象
        User cloneUser2 = gson.fromJson(userStr2, User.class);
        // false
        System.out.println(user2 == cloneUser2);

        // 通过 fastjson 进行深度克隆
        House house3 = new House("市区", 58955);
        User user3 = new User("张三", 18, house3);
        // 将对象序列化为 json 字符串
        String userStr3 = JSON.toJSONString(user3);
        // 然后将字符串反序列化为对象
        User cloneUser3 = JSON.parseObject(userStr3, User.class);
        // false
        System.out.println(user3 == cloneUser3);
    }

}
