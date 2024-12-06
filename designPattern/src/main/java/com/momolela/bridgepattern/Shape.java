package com.momolela.bridgepattern;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/12/6 09:45
 */
public abstract class Shape {

    public Color color;

    void setColor(Color color) {
        this.color = color;
    }

    abstract void draw();
}
