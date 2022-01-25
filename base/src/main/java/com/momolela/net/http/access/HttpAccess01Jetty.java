package com.momolela.net.http.access;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.handler.ContextHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 利用 jetty 发布一个 http 的接口
 * 并且配置好 JettyHttpHandler 处理方式
 */
public class HttpAccess01Jetty {
    public static void main(String[] args) throws Exception {
//        // jetty 也可以作为应用容器启动程序
//        Server webServer = new Server();
//        SelectChannelConnector connector = new SelectChannelConnector();
//        connector.setPort(8080);
//        webServer.setConnectors(new Connector[]{connector}); // 设置 connector
//        WebAppContext webAppContext = new WebAppContext();
//        webAppContext.setContextPath("/hai-node");
//        webAppContext.setResourceBase("./src/main/webapp");
//        webServer.setHandler(webAppContext); // 设置 handler
//        webServer.start();

        // jetty 使用 ContextHandler 发布 http 接口
        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/momolela/printName"); // http://ip:port/momolela/printName/
        contextHandler.setHandler(new JettyHttpHandler());
        Server server = new Server(9526);
        server.setHandler(contextHandler);
        server.start();
    }
}

/**
 * 真实的请求处理
 */
class JettyHttpHandler extends AbstractHandler {
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException, ServletException {
        String name = null;
        // method，http 请求方式
        String method = request.getMethod();
        System.out.println("method: " + method);
        // queryString
        String queryString = request.getQueryString();
        System.out.println("queryString: " + queryString);
        if (!StringUtils.isEmpty(queryString)) { // queryString 有值，说明是 get 请求，因为他只能获取到 get 请求的参数
            // 如果是 get 方法先从 body 中获取 name 数据

            /*
                getDataForPost 里面用了 request.getInputStream()，如果这里面有参数值，那下面的 request.getParameter("name") 就获取不到值了。
                因为 getParameter()、getReader()、getInputStream() 相互冲突只能用一个
                可以理解为 getParameter() 中也用到了流，而数据的流入只能进行一次
             */
            name = getDataForPost(request);

            System.out.println("get data from body: " + name);
        }
        // 再从 form 表单中获取参数
        if (StringUtils.isEmpty(name)) {
            name = request.getParameter("name"); // getParameter 既能获取到 get 请求的参数，又能获取到 post 请求的参数
            System.out.println("get data from form: " + name);
        }
        // 获取请求方的 ip 和 port
        String ipAddress = getIpAddress(request);
        System.out.println("request ip: " + ipAddress);
        int port = request.getRemotePort();
        System.out.println("request port: " + port);
        // 响应
        print(response, "name is: " + name);
    }

    private void print(HttpServletResponse response, String result) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        OutputStream os = response.getOutputStream();
        os.write(result.getBytes("UTF-8"));
        os.close();
    }

    private String getDataForPost(HttpServletRequest request) throws IOException {
        String charset = request.getCharacterEncoding();
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), charset));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    private String getIpAddress(HttpServletRequest request) throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
}
