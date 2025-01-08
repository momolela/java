package com.momolela.chainofresponsibilitypattern.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2025/1/8 11:14
 */
public class PassServiceImpl implements IPassService {

    private static final Map<String, PassLink> PASS_LINK_MAP = new HashMap<>();

    @Override
    public PassLink getFirstPassLink() {
        if (!PASS_LINK_MAP.isEmpty()) {
            Set<Map.Entry<String, PassLink>> entries = PASS_LINK_MAP.entrySet();
            for (Map.Entry<String, PassLink> entry : entries) {
                PassLink passLink = entry.getValue();
                if (passLink.getPreHandlerId() == null) {
                    return passLink;
                }
            }
        }
        return null;
    }

    @Override
    public PassLink getPassLinkById(String id) {
        return PASS_LINK_MAP.get(id);
    }

    static {
        PassLinkEnum[] values = PassLinkEnum.values();
        for (PassLinkEnum value : values) {
            PassLink passLink = value.getPassLink();
            PASS_LINK_MAP.put(String.valueOf(passLink.getId()), passLink);
        }
    }
}
