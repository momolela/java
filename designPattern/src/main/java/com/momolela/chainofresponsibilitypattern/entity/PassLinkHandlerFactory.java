package com.momolela.chainofresponsibilitypattern.entity;

import com.momolela.chainofresponsibilitypattern.Handler;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2025/1/8 11:29
 */
public class PassLinkHandlerFactory {

    private static PassServiceImpl passService = new PassServiceImpl();

    public Handler getFirstPassLinkHandler() {
        PassLink firstPassLink = passService.getFirstPassLink();
        Handler firstPassLinkHandler = newPassLinkHandler(firstPassLink);
        if (firstPassLinkHandler == null) {
            return null;
        }

        // 把 firstPassLinkHandler 的后续处理类设置上
        Integer nextHandlerId;
        PassLink tempPassLink = firstPassLink;
        Handler tempPassLinkHandler = firstPassLinkHandler;
        while ((nextHandlerId = tempPassLink.getNextHandlerId()) != null) {
            PassLink passLinkById = passService.getPassLinkById(String.valueOf(nextHandlerId));
            Handler nextPassLinkHandler = newPassLinkHandler(passLinkById);
            if (nextPassLinkHandler != null) {
                tempPassLinkHandler.setNext(nextPassLinkHandler);
            }
            tempPassLink = passLinkById;
            tempPassLinkHandler = nextPassLinkHandler;
        }

        return firstPassLinkHandler;
    }

    public Handler newPassLinkHandler(PassLink passLink) {
        String handlerClassStr = passLink.getHandlerClass();
        if (handlerClassStr != null && !"".equals(handlerClassStr)) {
            try {
                Class<?> handlerClass = Class.forName(handlerClassStr);
                return (Handler) handlerClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
