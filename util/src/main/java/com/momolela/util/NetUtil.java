package com.momolela.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络相关工具
 */
public class NetUtil {

    private static String macAddressStr = null;
    private static String computerName = System.getenv().get("COMPUTERNAME");
    private static final String[] windowsCommand = {"ipconfig", "/all"};
    private static final String[] linuxCommand = {"/sbin/ifconfig", "-a"};
    private static final Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) throws Exception {
        System.out.println(getComputerName());
    }

    /**
     * 获取 IP 地址，支持 windows 和 Linux
     *
     * @return ip
     */
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress inetAddress;
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        inetAddress = inetAddresses.nextElement();
                        if (inetAddress != null && inetAddress instanceof Inet4Address) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("IP 地址获取失败：" + e.toString());
        }
        return "";
    }

    /**
     * 获取多个网卡地址
     *
     * @return MACAddressList
     */
    public static List<String> getMACAddressList() throws IOException {
        ArrayList<String> macAddressList = new ArrayList<>();
        String os = System.getProperty("os.name");
        String[] command;
        if (os.startsWith("Windows")) {
            command = windowsCommand;
        } else if (os.startsWith("Linux") || os.startsWith("Mac")) {
            command = linuxCommand;
        } else {
            throw new IOException("未知的操作系统：" + os);
        }
        // 执行命令
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (String line; (line = bufReader.readLine()) != null; ) {
            Matcher matcher = macPattern.matcher(line);
            if (matcher.matches()) {
                macAddressList.add(matcher.group(1));
            }
        }
        process.destroy();
        bufReader.close();
        return macAddressList;
    }

    /**
     * 获取一个网卡地址（多个网卡时从中获取一个）
     *
     * @return MACAddress
     */
    public static String getMACAddress() {
        if (macAddressStr == null || "".equals(macAddressStr)) {
            StringBuilder sb = new StringBuilder(); // 存放多个网卡地址用，目前只取一个非0000000000E0隧道的值
            try {
                List<String> macList = getMACAddressList();
                for (Iterator<String> iter = macList.iterator(); iter.hasNext(); ) {
                    String mac = iter.next();
                    if (!"0000000000E0".equals(mac)) {
                        sb.append(mac);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            macAddressStr = sb.toString();
        }
        return macAddressStr;
    }

    /**
     * 获取电脑名
     *
     * @return computerName
     */
    public static String getComputerName() {
        if (computerName == null || "".equals(computerName)) {
            computerName = System.getenv().get("COMPUTERNAME");
        }
        return computerName;
    }
}
