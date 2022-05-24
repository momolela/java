package com.momolela.map;

import org.apache.commons.collections.map.CaseInsensitiveMap;

public class Map03CaseInsensitiveMap {

    /**
     * CaseInsensitiveMap
     * key 忽视大小写，直接全部变成小写
     * value 相同 key 的 value 会进行覆盖
     * @param args
     */
    public static void main(String[] args) {
        CaseInsensitiveMap caseInsensitiveMap = new CaseInsensitiveMap();
        caseInsensitiveMap.put("userName", 27);
        System.out.println(caseInsensitiveMap); // {username=27}
        caseInsensitiveMap.put("USERNAME", 28);
        System.out.println(caseInsensitiveMap); // {username=28}
    }
}
