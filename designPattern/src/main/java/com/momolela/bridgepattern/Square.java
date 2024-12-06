package com.momolela.bridgepattern;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/12/6 10:04
 */
public class Square extends Shape {
    @Override
    void draw() {
        this.color.bePaint("正方形");
    }
}
