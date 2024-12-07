package com.momolela.observerpattern;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/12/7 10:29
 */
public class App {
    public static void main(String[] args) {
        Subject subject = new Subject();
        BinaryObserver binaryObserver = new BinaryObserver(subject);
        OctalObserver octalObserver = new OctalObserver(subject);
        HexObserver hexObserver = new HexObserver(subject);
        System.out.println("First state change: 10");
        subject.setState(10);
        System.out.println();
        System.out.println("Second state change: 15");
        subject.setState(15);
    }
}
