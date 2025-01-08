package com.momolela.chainofresponsibilitypattern.entity;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2025/1/8 10:35
 */
public class PassLink {
    /**
     * id
     */
    private Integer id;
    /**
     * 处理类全路径
     */
    private String handlerClass;
    /**
     * 前置处理 id
     */
    private Integer preHandlerId;
    /**
     * 后置处理 id
     */
    private Integer nextHandlerId;

    public PassLink(Integer id, String handlerClass, Integer preHandlerId, Integer nextHandlerId) {
        this.id = id;
        this.handlerClass = handlerClass;
        this.preHandlerId = preHandlerId;
        this.nextHandlerId = nextHandlerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(String handlerClass) {
        this.handlerClass = handlerClass;
    }

    public Integer getPreHandlerId() {
        return preHandlerId;
    }

    public void setPreHandlerId(Integer preHandlerId) {
        this.preHandlerId = preHandlerId;
    }

    public Integer getNextHandlerId() {
        return nextHandlerId;
    }

    public void setNextHandlerId(Integer nextHandlerId) {
        this.nextHandlerId = nextHandlerId;
    }
}
