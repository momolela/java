package com.momolela.net.http.proxy;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.momolela.net.http.proxy.entity.CustomMergeStrategy;
import com.momolela.net.http.proxy.entity.GroupProjectUser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class HttpProxy99InvokeDemo {

    public static void main(String[] args) {
        // testHaiHttp();
        // testHisHttp();
        exportGitUser();
    }

    static class runThreadHttp implements Callable {

        @Override
        public Long call() {
            // try {
            //     DefaultHttpClient httpclient = new DefaultHttpClient();
            //     HttpPost httpPost = new HttpPost("http://10.8.2.178:8081/bs-whis/BSXml_webCloudClinic/getMedicalRecordInformation"); // url 请求地址
            //     StringEntity stringEntity = new StringEntity("<BSXml><MsgHeader><Organization>1</Organization><Sender>GOL</Sender><ServiceType>service</ServiceType><MsgType>ODS_02100005</MsgType><MsgVersion>3.3</MsgVersion></MsgHeader><MsgBody><VisitOrganization>1</VisitOrganization><VisitId>71</VisitId></MsgBody></BSXml>", "UTF-8");
            //     httpPost.setEntity(stringEntity);
            //     httpPost.setHeader("Content-Type", "application/xml");
            //
            //     long s = System.currentTimeMillis();
            //     HttpResponse response = httpclient.execute(httpPost);
            //     long e = System.currentTimeMillis();
            //     long cost = e - s;
            //     System.out.println(cost);
            //
            //     HttpEntity entity = response.getEntity();
            //     try {
            //         if (entity != null) {
            //             String str = EntityUtils.toString(entity, "UTF-8");
            //             System.out.println(str);
            //             return cost;
            //         }
            //     } finally {
            //         if (entity != null) {
            //             entity.getContent().close();
            //         }
            //     }
            // } catch (Exception e) {
            //     e.printStackTrace();
            // }
            // return null;


            // CloseableHttpResponse response = null;
            // CloseableHttpClient client = null;
            // try {
            //     HttpPost put = new HttpPost("http://10.8.2.178:8081/bs-whis/BSXml_webCloudClinic/getMedicalRecordInformation");
            //     StringEntity entity = new StringEntity("<BSXml><MsgHeader><Organization>1</Organization><Sender>GOL</Sender><ServiceType>service</ServiceType><MsgType>ODS_02100005</MsgType><MsgVersion>3.3</MsgVersion></MsgHeader><MsgBody><VisitOrganization>1</VisitOrganization><VisitId>71</VisitId></MsgBody></BSXml>", "UTF-8");
            //     put.setEntity(entity);
            //     put.addHeader("Content-Type", "application/xml");
            //     client = HttpClientBuilder.create().build();
            //
            //     long s = System.currentTimeMillis();
            //     response = client.execute(put);
            //     long e = System.currentTimeMillis();
            //     long cost = e - s;
            //     System.out.println(cost);
            //
            //     HttpEntity responseEntity = response.getEntity();
            //     System.out.println(EntityUtils.toString(responseEntity, "UTF-8"));
            //     return cost;
            // } catch (Exception e) {
            //     e.printStackTrace();
            // } finally {
            //     try {
            //         client.close();
            //     } catch (Exception e) {
            //         e.printStackTrace();
            //     }
            // }
            // return null;


            try {
                long s = System.currentTimeMillis();
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/xml");
                Map<String, Object> map = HttpProxy10HttpClientUtil.doPostString(
                        "http://10.8.2.178:8081/bs-whis/BSXml_webCloudClinic/getMedicalRecordInformation",
                        headers,
                        "<BSXml><MsgHeader><Organization>1</Organization><Sender>GOL</Sender><ServiceType>service</ServiceType><MsgType>ODS_02100005</MsgType><MsgVersion>3.3</MsgVersion></MsgHeader><MsgBody><VisitOrganization>1</VisitOrganization><VisitId>71</VisitId></MsgBody></BSXml>",
                        null,
                        null);
                System.out.println("status: " + map.get("status") + ", content: " + map.get("content"));
                long e = System.currentTimeMillis();
                long cost = e - s;
                System.out.println(cost);
                return cost;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void testHisHttp() {
        int total = 1; // 并发次数
        long totalCost = 0; // 总共耗时
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<Future> futureList = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Future future = threadPool.submit(new runThreadHttp());
            futureList.add(future);
        }
        for (Future f : futureList) {
            try {
                long cost = (long) f.get();
                totalCost = totalCost + cost;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
        System.out.println("avg：" + (totalCost / total));
    }

    public static void testHaiHttp() {
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            // url 请求地址，最后要加上 /，不然 post 请求失败
            HttpPost httpPost = new HttpPost("http://10.8.2.167:9526/hai/HttpEntry/");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // service 流程名称（必填）
            nvps.add(new BasicNameValuePair("service", "getFTeamList"));
            // urid 用户名
            nvps.add(new BasicNameValuePair("urid", ""));
            // pwd 密码
            nvps.add(new BasicNameValuePair("pwd", ""));
            // parameter 字符串格式
            nvps.add(new BasicNameValuePair("parameter", "{\"pageNo\":1,\"query\":{\"memberObjId\":\"9600\"},\"pageSize\":-1}"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            try {
                if (entity != null) {
                    String str = EntityUtils.toString(entity, "UTF-8");
                    System.out.println(str);
                }
            } finally {
                if (entity != null) {
                    entity.getContent().close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportGitUser() {
        List<GroupProjectUser> groupProjectUserList = new ArrayList<>();

        DefaultHttpClient httpClient = new DefaultHttpClient();

        // 查询基础分组
        HttpEntity entityGroups = null;
        try {
            HttpGet httpGetGroups = new HttpGet("https://gitlab.bsoft.com.cn/api/v4/groups?per_page=50");
            httpGetGroups.addHeader("Cookie", "visitor_id=27994f04-20b6-416d-9fac-9a3fcc12c072; sidebar_collapsed=false; hide_no_ssh_message=false; _ga_0C4M1PWYZ7=GS1.1.1698323180.1.0.1698323180.0.0.0; _ga=GA1.1.847935662.1667319710; event_filter=all; auto_devops_settings_dismissed=true; sidebar_pinned_section_expanded=false; preferred_language=en; known_sign_in=aCs0SENzSitOcTlVWEFXYVkrM0Q5bk1xMXBjYnlwNitqczcxbGZMZG9ReWNEL0dhMy9rUnprVWlQK0lTTlY1N1h3SUh3clBCVGxDNklsYUdyWHV0WlE1d2VpS3QrL0pVcElpcmJKNWtZN1E1aGN1c1RsdjdMTHlOSmtKTkFuS1EtLW5wUmVoV1pZQU16MzVKRWdYcG5FelE9PQ%3D%3D--f3ff317cbfb99d205dac156489f212f18325ae7e; _gitlab_session=87e168f33277b6a993abddc03fcd39be; SESSION=ZGQwODc2NmEtYjU2My00ZGU4LThmYTUtYWQ0YWE1NDUwYjRl");
            HttpResponse responseGroups = httpClient.execute(httpGetGroups);
            entityGroups = responseGroups.getEntity();
            if (entityGroups != null) {
                String strGroups = EntityUtils.toString(entityGroups, "UTF-8");
                JSONArray arrayGroups = JSONObject.parseArray(strGroups);
                arrayGroups.forEach(itemGroups -> {
                    Integer idGroups = (Integer) ((Map<String, Object>) itemGroups).get("id");
                    String nameGroups = (String) ((Map<String, Object>) itemGroups).get("name");

                    // 查询分组下面的项目
                    HttpEntity entityProjects = null;
                    try {
                        HttpGet httpGetProjects = new HttpGet("https://gitlab.bsoft.com.cn/api/v4/groups/" + idGroups + "/projects");
                        httpGetProjects.addHeader("Cookie", "visitor_id=27994f04-20b6-416d-9fac-9a3fcc12c072; sidebar_collapsed=false; hide_no_ssh_message=false; _ga_0C4M1PWYZ7=GS1.1.1698323180.1.0.1698323180.0.0.0; _ga=GA1.1.847935662.1667319710; event_filter=all; auto_devops_settings_dismissed=true; sidebar_pinned_section_expanded=false; preferred_language=en; known_sign_in=aCs0SENzSitOcTlVWEFXYVkrM0Q5bk1xMXBjYnlwNitqczcxbGZMZG9ReWNEL0dhMy9rUnprVWlQK0lTTlY1N1h3SUh3clBCVGxDNklsYUdyWHV0WlE1d2VpS3QrL0pVcElpcmJKNWtZN1E1aGN1c1RsdjdMTHlOSmtKTkFuS1EtLW5wUmVoV1pZQU16MzVKRWdYcG5FelE9PQ%3D%3D--f3ff317cbfb99d205dac156489f212f18325ae7e; _gitlab_session=87e168f33277b6a993abddc03fcd39be; SESSION=ZGQwODc2NmEtYjU2My00ZGU4LThmYTUtYWQ0YWE1NDUwYjRl");
                        HttpResponse responseProjects = httpClient.execute(httpGetProjects);
                        entityProjects = responseProjects.getEntity();
                        if (entityProjects != null) {
                            String strProjects = EntityUtils.toString(entityProjects, "UTF-8");
                            JSONArray arrayProjects = JSONObject.parseArray(strProjects);
                            arrayProjects.forEach(itemProjects -> {
                                Integer idProjects = (Integer) ((Map<String, Object>) itemProjects).get("id");
                                String nameProjects = (String) ((Map<String, Object>) itemProjects).get("name");
                                String projectDesc = (String) ((Map<String, Object>) itemProjects).get("description");

                                // 查询项目下的用户
                                HttpEntity entityMembers = null;
                                try {
                                    HttpGet httpGetMembers = new HttpGet("https://gitlab.bsoft.com.cn/api/v4/projects/" + idProjects + "/members");
                                    httpGetMembers.addHeader("Cookie", "visitor_id=27994f04-20b6-416d-9fac-9a3fcc12c072; sidebar_collapsed=false; hide_no_ssh_message=false; _ga_0C4M1PWYZ7=GS1.1.1698323180.1.0.1698323180.0.0.0; _ga=GA1.1.847935662.1667319710; event_filter=all; auto_devops_settings_dismissed=true; sidebar_pinned_section_expanded=false; preferred_language=en; known_sign_in=aCs0SENzSitOcTlVWEFXYVkrM0Q5bk1xMXBjYnlwNitqczcxbGZMZG9ReWNEL0dhMy9rUnprVWlQK0lTTlY1N1h3SUh3clBCVGxDNklsYUdyWHV0WlE1d2VpS3QrL0pVcElpcmJKNWtZN1E1aGN1c1RsdjdMTHlOSmtKTkFuS1EtLW5wUmVoV1pZQU16MzVKRWdYcG5FelE9PQ%3D%3D--f3ff317cbfb99d205dac156489f212f18325ae7e; _gitlab_session=87e168f33277b6a993abddc03fcd39be; SESSION=ZGQwODc2NmEtYjU2My00ZGU4LThmYTUtYWQ0YWE1NDUwYjRl");
                                    HttpResponse responseMembers = httpClient.execute(httpGetMembers);
                                    entityMembers = responseMembers.getEntity();
                                    if (entityMembers != null) {
                                        String strMembers = EntityUtils.toString(entityMembers, "UTF-8");
                                        JSONArray arrayMembers = JSONObject.parseArray(strMembers);
                                        arrayMembers.forEach(itemMembers -> {
                                            String nameMembers = (String) ((Map<String, Object>) itemMembers).get("name");
                                            GroupProjectUser groupProjectUser = new GroupProjectUser("PT", nameGroups, nameProjects, projectDesc, nameMembers);
                                            groupProjectUserList.add(groupProjectUser);
                                        });
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if (entityMembers != null) {
                                        try {
                                            entityMembers.getContent().close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (entityProjects != null) {
                            try {
                                entityProjects.getContent().close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityGroups != null) {
                try {
                    entityGroups.getContent().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // EasyExcel.write("/Users/sunzj/Desktop/GIT项目用户.xlsx", GroupProjectUser.class).sheet("分组项目用户列表").doWrite(groupProjectUserList);

        // 写入数据
        ExcelWriter excelWriter = EasyExcel.write("/Users/sunzj/Desktop/GIT项目用户.xlsx").excelType(ExcelTypeEnum.XLSX).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("分组项目用户列表").head(GroupProjectUser.class)
                .registerWriteHandler(new CustomMergeStrategy(groupProjectUserList.stream().map(GroupProjectUser::getRootGroup).collect(Collectors.toList()), 0))
                .registerWriteHandler(new CustomMergeStrategy(groupProjectUserList.stream().map(GroupProjectUser::getBaseGroup).collect(Collectors.toList()), 1))
                .registerWriteHandler(new CustomMergeStrategy(groupProjectUserList.stream().map(GroupProjectUser::getProjectName).collect(Collectors.toList()), 2))
                .registerWriteHandler(new CustomMergeStrategy(groupProjectUserList.stream().map(GroupProjectUser::getProjectDesc).collect(Collectors.toList()), 3))
                .build();
        excelWriter.write(groupProjectUserList, writeSheet);
        excelWriter.finish();
    }
}
