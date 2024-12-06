package com.momolela.bridgepattern;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/12/6 09:49
 */
public class App {
    public static void main(String[] args) {
        Shape circle = new Circle();
        circle.setColor(new White());
        circle.draw();

        Shape rectangle = new Rectangle();
        rectangle.setColor(new Black());
        rectangle.draw();
    }
}
