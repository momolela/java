package com.momolela.chainofresponsibilitypattern;

import com.momolela.chainofresponsibilitypattern.entity.PassLinkHandlerFactory;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/12/15 23:22
 */
public class App {
    public static void main(String[] args) {
        PassLinkHandlerFactory passLinkHandlerFactory = new PassLinkHandlerFactory();
        Handler firstPassLinkHandler = passLinkHandlerFactory.getFirstPassLinkHandler();
        firstPassLinkHandler.handler();
    }
}
