package com.momolela.chainofresponsibilitypattern;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2025/1/8 10:55
 */
public abstract class Handler {

    protected Handler next;

    public void setNext(Handler handler) {
        this.next = handler;
    }

    /**
     * 处理类
     *
     * @return 关卡得分
     */
    public abstract int handler();
}
