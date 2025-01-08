package com.momolela.chainofresponsibilitypattern.entity;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2025/1/8 10:43
 */
public enum PassLinkEnum {
    /**
     * PassLink(id, 处理类全路径, 前置处理 id, 后置处理 id)
     */
    F_HANDLER(new PassLink(1, "com.momolela.chainofresponsibilitypattern.FirstPassHandler", null, 3)),
    S_HANDLER(new PassLink(2, "com.momolela.chainofresponsibilitypattern.SecondPassHandler", 3, null)),
    T_HANDLER(new PassLink(3, "com.momolela.chainofresponsibilitypattern.ThirdPassHandler", 1, 2));

    PassLink passLink;

    PassLinkEnum(PassLink passLink) {
        this.passLink = passLink;
    }

    public PassLink getPassLink() {
        return this.passLink;
    }
}
