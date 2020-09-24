package com.momolela.generic;

/**
 * 以前的做法，统一定义成Object，让所有的对象都可被接收
 */
class UtilsBefore {
    private Object tool;

    public Object getTool() {
        return tool;
    }

    public void setTool(Object tool) {
        this.tool = tool;
    }
}

/**
 * 现在的做法，定义成泛型类，要用的类型通过创建对象的时候指定，然后类中直接运用该类型
 *
 * @param <T>
 */
class UtilsNow<T> {
    private T tool;

    public T getTool() {
        return tool;
    }

    public void setTool(T tool) {
        this.tool = tool;
    }
}

/**
 * 现在做法的扩展，类上使用泛型的限定，但是不能用 ?
 *
 * @param <T>
 */
class UtilsNowExtends<T extends String> {
    private T tool;

    public T getTool() {
        return tool;
    }

    public void setTool(T tool) {
        this.tool = tool;
    }
}

public class Generic03InClass {
    public static void main(String[] args) {
        UtilsNow<String> utilsNow = new UtilsNow<String>();
        String tool = utilsNow.getTool();

        UtilsNowExtends<String> utilsNowExtends = new UtilsNowExtends<String>();
        String toolExtends = utilsNowExtends.getTool();
    }
}
