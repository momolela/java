package com.momolela.decoratorpattern;

/**
 * 实现了 Shape 接口的抽象装饰类
 */
public abstract class ShapeDecorator implements Shape {
    protected Shape shape;

    public ShapeDecorator(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void draw() {
        shape.draw();
    }
}