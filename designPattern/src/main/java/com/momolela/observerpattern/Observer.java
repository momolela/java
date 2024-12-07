package com.momolela.observerpattern;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/12/7 10:27
 */
public abstract class Observer {

    public Subject subject;

    public abstract void update();

}
