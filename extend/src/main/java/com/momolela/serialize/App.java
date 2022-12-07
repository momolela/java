package com.momolela.serialize;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    public static void main(String[] args) {
        Serializer serializer = SerializerFactory.getSerializer((byte) 3);

        // 序列化
        Test sunzj = new Test("sunzj", 27);
        byte[] bytes = serializer.write(sunzj);
        System.out.println(new String(bytes));
        // 反序列化
        Test test = (Test) serializer.read(bytes);
        System.out.println(test.getName() + ":" + test.getAge());


        // ------------------ fastjson 对于复杂对象序列化支持很好 -----------------
        // fastjson 序列化
        String jsonString = JSONObject.toJSONString(sunzj);
        System.out.println("fastjson 序列化 javabean 的结果：" + jsonString);
        // fastjson 反序列化
        Test test1 = JSONObject.parseObject(jsonString, Test.class);
        System.out.println(test1.getName() + ":" + test1.getAge());

        // ------------------ jackson 对于大数据对象序列化支持很好 ----------------
        // 可以通过 filter 确定哪些字段需要或不需要序列化
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String valueAsString = objectMapper.writeValueAsString(sunzj);
            System.out.println("fastjson 序列化 javabean 的结果：" + valueAsString);
            Test test2 = objectMapper.readValue(valueAsString, Test.class);
            System.out.println(test2.getName() + ":" + test2.getAge());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
