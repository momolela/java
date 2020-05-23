package com.momolela.exception;

/**
 * 声明异常时，建议声明具体的异常
 */
class ExceptionsDealDemo {
    int div(int a, int b) throws ArithmeticException, IndexOutOfBoundsException {
        int[] arr = new int[a];
        System.out.println(arr[4]);
        return a / b;
    }
}

/**
 * 声明了哪些异常，就针对性的有哪些异常catch块
 * 如果catch块的异常出现了继承关系，父类的异常catch放在最下面
 * 不要定义多余的catch块
 */
public class ExceptionsDeal {
    public static void main(String[] args) {
        ExceptionsDealDemo exceptionsDealDemo = new ExceptionsDealDemo();
        try {
            int div = exceptionsDealDemo.div(4, 1);
            System.out.println("结果是：" + div);
        } catch (ArithmeticException ae) {
            System.out.println(ae.toString());
            System.out.println("被0除了");
        } catch (IndexOutOfBoundsException ie) {
            System.out.println(ie.toString());
            System.out.println("角标越界了");
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("申明外的异常");
        }
        System.out.println("over");
    }
}
