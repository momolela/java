package com.momolela.bridgepattern;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/12/6 09:44
 */
public class Gray implements Color {
    @Override
    public void bePaint(String shape) {
        System.out.println("灰色的" + shape);
    }
}