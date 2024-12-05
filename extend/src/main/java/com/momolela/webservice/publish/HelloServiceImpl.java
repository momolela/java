package com.momolela.webservice.publish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskHeaders;
import com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskRequest;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.utils.StringUtils;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.momolela.webservice.entity.JSONResponseBean;
import com.momolela.webservice.entity.MyAuthenticator;
import com.momolela.webservice.entity.SendReq;
import com.momolela.webservice.entity.SendRes;
import com.momolela.webservice.util.JSONUtils;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoRequest;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @author sunzj
 * 定义 soap1.2 的协议方式，@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
 */
@WebService
// @BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class HelloServiceImpl implements HelloService {

    private String url = "http://112.35.1.155:1992/sms/norsubmit";
    private String ecName = "南京市溧水区卫生和计划生育局";
    private String apId = "admin";
    private String secretKey = "n9*LycnH";
    private String sign = "oksm0kfKs";
    private String addSerial = "";
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        // HelloServiceImpl helloService = new HelloServiceImpl();
        // String content = "验证码：123321";
        // helloService.sendSms("17326049852,13616549386", content);


        // HelloServiceImpl helloService = new HelloServiceImpl();
        // String contentStr = "{\"subject\":\"Hello word\",\"content\":\"you is sb 2\"}";
        // helloService.sendEmail("1981197934@qq.com", contentStr);

        // HelloServiceImpl helloService = new HelloServiceImpl();
        // helloService.sendDing("dinglhbrp2daraefojgd", "Q-3WOfZV7dV6NEoDw7Rhq7X31ZrvoG8kAekOMmYc1fRNoJ-uMe5t5pV1ry7-4UtG", "2777817668", "402665090523516536", "action_card", "123", "456", "789", "www.baidu.com");

        // HelloServiceImpl helloService = new HelloServiceImpl();
        // helloService.sendDingTask("dinglhbrp2daraefojgd", "Q-3WOfZV7dV6NEoDw7Rhq7X31ZrvoG8kAekOMmYc1fRNoJ-uMe5t5pV1ry7-4UtG", "manager6124", "402665090523516536", "123", "456", "http://www.baidu.com");
    }

    // @Override
    // @WebMethod
    // public String sayHello(String name) {
    //     System.out.println("hello " + name);
    //     return "hello " + name;
    // }
    //
    // @Override
    // @WebMethod
    // public Boolean sendEmail(@WebParam(name = "to") String to, @WebParam(name = "contentStr") String contentStr) {
    //
    //     Map<String, Object> contentMap = (Map<String, Object>) JSONObject.parse(contentStr);
    //
    //     Properties props = new Properties();
    //     props.setProperty("mail.smtp.host", "smtp.qq.com");
    //     props.setProperty("mail.smtp.auth", "true");
    //     props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    //     props.setProperty("mail.smtp.port", "465");
    //     props.setProperty("mail.smtp.socketFactory.port", "465");
    //
    //     String userName = "1083910359@qq.com";
    //     String password = "lpykkpcteabpiade";
    //     Authenticator authenticator = new MyAuthenticator(userName, password);
    //     javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, authenticator);
    //     // 可以从控制台看到信息
    //     session.setDebug(true);
    //
    //     try {
    //         Address fromAddress = new InternetAddress(userName);
    //         Address toAddress = new InternetAddress(to);
    //         MimeMessage msg = new MimeMessage(session);
    //         msg.setFrom(fromAddress);
    //         msg.setSubject((String) contentMap.get("subject"));
    //         msg.setContent(contentMap.get("content"), "text/html;charset=utf-8");
    //         msg.setSentDate(new Date());
    //         msg.setRecipient(Message.RecipientType.TO, toAddress);
    //         Transport.send(msg);
    //     } catch (MessagingException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    //
    //     return true;
    // }
    //
    // @Override
    // public Boolean sendSmsByAliYun(@WebParam(name = "to") String to, @WebParam(name = "contentStr") String contentStr) {
    //
    //     Map<String, Object> contentMap = (Map<String, Object>) JSONObject.parse(contentStr);
    //
    //     try {
    //         // Configure Credentials authentication information, including ak, secret, token
    //         StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
    //                 .accessKeyId((String) contentMap.get("accessKeyID"))
    //                 .accessKeySecret((String) contentMap.get("accessKeySecret"))
    //                 // use STS token
    //                 // .securityToken(contentMap.get("SECURITY_TOKEN"))
    //                 .build());
    //
    //         // Configure the Client
    //         AsyncClient client = AsyncClient.builder()
    //                 // Region ID
    //                 .region("cn-hangzhou")
    //                 .credentialsProvider(provider)
    //                 // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
    //                 .overrideConfiguration(ClientOverrideConfiguration.create().setEndpointOverride("dysmsapi.aliyuncs.com"))
    //                 .build();
    //
    //         // Parameter settings for API request
    //         SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
    //                 .signName((String) contentMap.get("signName"))
    //                 .templateCode((String) contentMap.get("templateCode"))
    //                 .phoneNumbers(to)
    //                 // "{"code": "1234"}"
    //                 .templateParam((String) contentMap.get("templateParam"))
    //                 .build();
    //
    //         // Asynchronously get the return value of the API request
    //         CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
    //         // Synchronously get the return value of the API request
    //         SendSmsResponse resp = response.get();
    //         System.out.println(new Gson().toJson(resp));
    //         // Finally, close the client
    //         client.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    //     return true;
    // }

    @Override
    @WebMethod(operationName = "sendSms")
    public SendRes sendSms(@WebParam(name = "mobiles") String mobiles, @WebParam(name = "content") String content) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        SendReq sendReq = new SendReq();
        sendReq.setEcName(ecName);
        sendReq.setApId(apId);
        sendReq.setSecretKey(secretKey);
        sendReq.setMobiles(mobiles);
        sendReq.setContent(content);
        sendReq.setSign(sign);
        sendReq.setAddSerial(addSerial);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sendReq.getEcName())
                .append(sendReq.getApId())
                .append(sendReq.getSecretKey())
                .append(sendReq.getMobiles())
                .append(sendReq.getContent())
                .append(sendReq.getSign())
                .append(sendReq.getAddSerial());
        sendReq.setMac(DigestUtils.md5DigestAsHex(stringBuffer.toString().getBytes()).toLowerCase());

        String jsonText = JSON.toJSONString(sendReq);
        String body = null;
        try {
            body = Base64.getEncoder().encodeToString(jsonText.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpEntity<String> httpEntity = new HttpEntity<String>(body, httpHeaders);
        RestTemplate restTemplate1 = new RestTemplate();
        JSONObject result = restTemplate1.postForObject(url, httpEntity, JSONObject.class);
        return JSON.toJavaObject(result, SendRes.class);
    }

    // @Override
    // public Object getDingDingUserInfoByTmpAuthCode(@WebParam(name = "accessKey") String accessKey, @WebParam(name = "accessSecret") String accessSecret, @WebParam(name = "tmpAuthCode") String tmpAuthCode) {
    //     try {
    //         // 获取access_token，注意正式代码要有异常流处理
    //         String access_token = getDingDingToken(accessKey, accessSecret);
    //
    //         // 通过临时授权码获取授权用户的个人信息
    //         DefaultDingTalkClient client2 = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
    //         OapiSnsGetuserinfoBycodeRequest reqBycodeRequest = new OapiSnsGetuserinfoBycodeRequest();
    //         // 通过扫描二维码，跳转指定的redirect_uri后，向url中追加的code临时授权码
    //         reqBycodeRequest.setTmpAuthCode(tmpAuthCode);
    //         OapiSnsGetuserinfoBycodeResponse bycodeResponse = client2.execute(reqBycodeRequest, accessKey, accessSecret);
    //
    //         // 根据unionid获取userid
    //         String unionid = bycodeResponse.getUserInfo().getUnionid();
    //         DingTalkClient clientDingTalkClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/getbyunionid");
    //         OapiUserGetbyunionidRequest reqGetbyunionidRequest = new OapiUserGetbyunionidRequest();
    //         reqGetbyunionidRequest.setUnionid(unionid);
    //         OapiUserGetbyunionidResponse oapiUserGetbyunionidResponse = clientDingTalkClient.execute(reqGetbyunionidRequest, access_token);
    //
    //         // 根据userId获取用户信息
    //         String userid = oapiUserGetbyunionidResponse.getResult().getUserid();
    //         DingTalkClient clientDingTalkClient2 = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
    //         OapiV2UserGetRequest reqGetRequest = new OapiV2UserGetRequest();
    //         reqGetRequest.setUserid(userid);
    //         reqGetRequest.setLanguage("zh_CN");
    //         OapiV2UserGetResponse rspGetResponse = clientDingTalkClient2.execute(reqGetRequest, access_token);
    //         System.out.println(rspGetResponse.getBody());
    //         ObjectMapper mapper = new ObjectMapper();
    //         Map<String, Object> userMap = mapper.readValue(rspGetResponse.getBody(), new TypeReference<Map<String, Object>>() {
    //         });
    //         return JSONObject.toJSONString(userMap);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return "";
    // }
    //
    // private String getDingDingToken(String accessKey, String accessSecret) {
    //     try {
    //         DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
    //         OapiGettokenRequest request = new OapiGettokenRequest();
    //         request.setAppkey(accessKey);
    //         request.setAppsecret(accessSecret);
    //         request.setHttpMethod("GET");
    //         OapiGettokenResponse response = client.execute(request);
    //         ObjectMapper mapper = new ObjectMapper();
    //         Map<String, Object> map = mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {
    //         });
    //         return (String) map.get("access_token");
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return "";
    // }
    //
    //
    // @Override
    // public JSONResponseBean sendDing(@WebParam(name = "accessKey") String accessKey, @WebParam(name = "accessSecret") String accessSecret, @WebParam(name = "agentId") String agentIdStr, @WebParam(name = "userIds") String userIds, @WebParam(name = "msgType") String msgType, @WebParam(name = "title") String title, @WebParam(name = "content") String content, @WebParam(name = "singleTitle") String singleTitle, @WebParam(name = "singleUrl") String singleUrl) {
    //     JSONResponseBean res = new JSONResponseBean();
    //     try {
    //         String accessToken = getDingDingToken(accessKey, accessSecret);
    //         String url = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
    //         long agentId = Long.parseLong(agentIdStr);
    //         DefaultDingTalkClient defaultDingTalkClient = new DefaultDingTalkClient(url);
    //         OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
    //         req.setAgentId(agentId);
    //         req.setUseridList(userIds);
    //         OapiMessageCorpconversationAsyncsendV2Request.Msg obj1 = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
    //         obj1.setMsgtype(msgType);
    //
    //         if ("text".equals(msgType)) {
    //             OapiMessageCorpconversationAsyncsendV2Request.Text obj2 = new OapiMessageCorpconversationAsyncsendV2Request.Text();
    //             obj2.setContent(content);
    //             obj1.setText(obj2);
    //         } else {
    //             OapiMessageCorpconversationAsyncsendV2Request.ActionCard obj2 = new OapiMessageCorpconversationAsyncsendV2Request.ActionCard();
    //             obj2.setSingleUrl(singleUrl);
    //             obj2.setSingleTitle(singleTitle);
    //             obj2.setMarkdown(content);
    //             obj2.setTitle(title);
    //             obj1.setActionCard(obj2);
    //         }
    //
    //         req.setMsg(obj1);
    //         OapiMessageCorpconversationAsyncsendV2Response response = (OapiMessageCorpconversationAsyncsendV2Response) defaultDingTalkClient.execute((TaobaoRequest) req, accessToken);
    //         System.out.println("DingSendMsgService-----" + JSONUtils.toString(response));
    //         String errMsg = response.getErrmsg();
    //         if (!"ok".equals(errMsg)) {
    //             res.setBody(response.getBody());
    //             res.setCode(-100);
    //             res.setMsg("推送钉钉消息失败");
    //         } else {
    //             res.setBody(response.getBody());
    //             res.setCode(200);
    //             res.setMsg("推送钉钉消息成功");
    //         }
    //         return res;
    //     } catch (ApiException e) {
    //         e.printStackTrace();
    //         res.setCode(400);
    //         res.setMsg(String.format("[%s]", new Object[]{e}));
    //         return res;
    //     }
    // }
    //
    // @Override
    // public JSONResponseBean sendDingTask(@WebParam(name = "accessKey") String accessKey, @WebParam(name = "accessSecret") String accessSecret, @WebParam(name = "sendUserId") String sendUserId, @WebParam(name = "userIds") String userIds, @WebParam(name = "title") String title, @WebParam(name = "desc") String desc, @WebParam(name = "taskUrl") String taskUrl) {
    //
    //     JSONResponseBean res = new JSONResponseBean();
    //     try {
    //         Config config = new Config();
    //         config.protocol = "https";
    //         config.regionId = "central";
    //         com.aliyun.dingtalktodo_1_0.Client client = new com.aliyun.dingtalktodo_1_0.Client(config);
    //         String accessToken = getDingDingToken(accessKey, accessSecret);
    //         String unionId = getUnionId(accessToken, sendUserId);
    //
    //         List<String> reUnionIds = new ArrayList<>();
    //         String[] userIdArr = userIds.split(",");
    //         for (int i = 0; i < userIdArr.length; i++) {
    //             String reUnionId = getUnionId(accessToken, userIdArr[i]);
    //             reUnionIds.add(reUnionId);
    //         }
    //
    //         CreateTodoTaskHeaders createTodoTaskHeaders = new CreateTodoTaskHeaders();
    //         createTodoTaskHeaders.xAcsDingtalkAccessToken = accessToken;
    //
    //         CreateTodoTaskRequest.CreateTodoTaskRequestNotifyConfigs notifyConfigs = new CreateTodoTaskRequest.CreateTodoTaskRequestNotifyConfigs().setDingNotify("1");
    //         CreateTodoTaskRequest.CreateTodoTaskRequestContentFieldList contentFieldList0 = new CreateTodoTaskRequest.CreateTodoTaskRequestContentFieldList();
    //         CreateTodoTaskRequest.CreateTodoTaskRequestDetailUrl detailUrl = new CreateTodoTaskRequest.CreateTodoTaskRequestDetailUrl().setAppUrl(taskUrl).setPcUrl(taskUrl);
    //         CreateTodoTaskRequest createTodoTaskRequest = new CreateTodoTaskRequest()
    //                 .setOperatorId(unionId)
    //                 // 业务 id
    //                 // .setSourceId("123456789")
    //                 // 待办标题
    //                 .setSubject(title)
    //                 // 创建者
    //                 .setCreatorId(unionId)
    //                 // 描述
    //                 .setDescription(desc)
    //                 // 截止时间
    //                 .setDueTime(1699488000000L)
    //                 // 执行者
    //                 .setExecutorIds(reUnionIds)
    //                 // 参与者
    //                 .setParticipantIds(reUnionIds)
    //                 // 跳转地址，如果没有跳转地址即可操作完成代办
    //                 .setDetailUrl(StringUtils.isEmpty(taskUrl) ? null : detailUrl)
    //                 .setContentFieldList(Collections.singletonList(contentFieldList0))
    //                 .setIsOnlyShowExecutor(true)
    //                 .setPriority(20)
    //                 .setNotifyConfigs(notifyConfigs);
    //         try {
    //             client.createTodoTaskWithOptions(unionId, createTodoTaskRequest, createTodoTaskHeaders, new RuntimeOptions());
    //             res.setBody("创建代办成功");
    //             res.setCode(200);
    //         } catch (Exception err) {
    //             res.setBody(err.getMessage());
    //             res.setCode(900);
    //             System.out.println(err.getMessage());
    //         }
    //     } catch (Exception e) {
    //         res.setBody(e.getMessage());
    //         res.setCode(900);
    //         System.out.println(e.getMessage());
    //     }
    //     return res;
    // }
    //
    // private String getUnionId(String accessToken, String userId) {
    //     try {
    //         DingTalkClient dingTalkClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
    //         OapiV2UserGetRequest req = new OapiV2UserGetRequest();
    //         req.setUserid(userId);
    //         req.setLanguage("en_US");
    //         OapiV2UserGetResponse rsp = dingTalkClient.execute(req, accessToken);
    //         return (String) ((Map<String, Object>) ((Map<String, Object>) (JSONObject.parse(rsp.getBody()))).get("result")).get("unionid");
    //     } catch (ApiException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
}
