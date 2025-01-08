package com.momolela.chainofresponsibilitypattern.entity;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2025/1/8 11:12
 */
public interface IPassService {
    /**
     * 获取第一个处理类
     *
     * @return
     */
    PassLink getFirstPassLink();

    /**
     * 通过 id 获取 Handler 处理类
     *
     * @param id
     * @return
     */
    PassLink getPassLinkById(String id);
}
