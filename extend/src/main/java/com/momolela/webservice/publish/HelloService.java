package com.momolela.webservice.publish;

import com.momolela.webservice.entity.JSONResponseBean;
import com.momolela.webservice.entity.SendRes;

/**
 * @author sunzj
 */
public interface HelloService {

    /**
     * 打印 hello
     *
     * @param name 名字
     * @return
     */
    String sayHello(String name);

    /**
     * 发送 email
     *
     * @param to         发送给谁
     * @param contentStr 内容
     * @return
     */
    Boolean sendEmail(String to, String contentStr);

    /**
     * 发送短信
     *
     * @param to         发送给谁
     * @param contentStr 内容
     * @return
     */
    Boolean sendSmsByAliYun(String to, String contentStr);

    /**
     * 发送短信
     *
     * @param to      发送给谁
     * @param content 内容
     * @return
     */
    SendRes sendSms(String to, String content);

    /**
     * 获取钉钉 token
     *
     * @param accessKey    accessKey
     * @param accessSecret accessSecret
     * @param tmpAuthCode  tmpAuthCode
     * @return
     */
    Object getDingDingUserInfoByTmpAuthCode(String accessKey, String accessSecret, String tmpAuthCode);


    /**
     * 发送钉钉消息
     *
     * @param accessKey    accessKey
     * @param accessSecret accessSecret
     * @param agentIdStr   agentIdStr
     * @param userIds      userIds
     * @param msgType      消息类型：text，action_card，暂时只支持这两种
     * @param title        title
     * @param content      content，消息类型为 text 时，只需要设置这一个值
     * @param singleTitle  singleTitle
     * @param singleUrl    singleUrl
     * @return
     */
    JSONResponseBean sendDing(String accessKey, String accessSecret, String agentIdStr, String userIds, String msgType, String title, String content, String singleTitle, String singleUrl);

    /**
     * 发送钉钉代办消息
     *
     * @param accessKey    accessKey
     * @param accessSecret accessSecret
     * @param userIds      userIds
     * @param title        title
     * @param desc         content
     * @param taskUrl      taskUrl
     * @return
     */
    JSONResponseBean sendDingTask(String accessKey, String accessSecret, String sendUserId, String userIds, String title, String desc, String taskUrl);
}
