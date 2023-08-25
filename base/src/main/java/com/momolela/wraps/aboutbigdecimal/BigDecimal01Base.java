package com.momolela.wraps.aboutbigdecimal;

import java.math.BigDecimal;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2023/8/25 08:57
 */
public class BigDecimal01Base {
    public static void main(String[] args) {
        // Double 和 Float 浮点数都是近似值，很多小数没有办法用二进制精确表示
        // BigDecimal 可以精确的表示所有数字，涉及金钱的场景，单位是分的时候，用 long，如果单位是元，用 BigDecimal

        // 问题 1：new BigDecimal() 的时候，入参最好不要用浮点数，而是用字符串，因为浮点数本身就不精确
        // 打印 0.90000000000000002220446049250313080847263336181640625
        System.out.println(new BigDecimal(0.9));
        // 打印 0.9
        System.out.println(new BigDecimal("0.9"));


        // 问题 2：比较两个 BigDecimal 的数据，如 0.1 和 0.10 ，不能调用 equals 而是要调用 compareTo 方法。因为 equals 不仅会比较值还会比较标度（0.1 的标度是 1，0.10 的标度是 2）所以返回 false，但是 compareTo 只会比较值
        // 打印 false
        System.out.println(new BigDecimal("0.1").equals(new BigDecimal("0.10")));
        // 打印 0，a.compareTo(b) -1 说明 a 小于 b，0 说明 a 等于 b，1 说明 a 大于 b
        System.out.println(new BigDecimal("0.1").compareTo(new BigDecimal("0.10")));


        // 问题 3：toString() 可能返回 3.14E+3 这些科学计数法的值，应该用 toPlainString() 能正常返回值的字符串
        // 打印 100.000
        System.out.println(new BigDecimal("100.000").toString());
        // 打印 1E+2，stripTrailingZeros() 用于去除末尾多余的 0
        System.out.println(new BigDecimal(new BigDecimal("100.000").stripTrailingZeros().toString()));
        // 打印 100
        System.out.println(new BigDecimal(new BigDecimal("100.000").stripTrailingZeros().toPlainString()));

        // 问题 4：BigDecimal 的类型在数据库中也需要存储为 decimal 的字段类型不然也会丢失精度

    }
}
