package com.momolela.util;

public class ThreadUtil {

    /**
     * 获取某个类的调用位置
     *
     * @param clz Class
     * @return string
     */
    public static String getSignedCallHierarchy(Class clz) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 0) {
            StringBuilder sb = new StringBuilder();

            StackTraceElement stackTraceElement;
            String fileName;
            String className;
            String methodName;
            int lineNumber;
            for (int i = 1; i < stackTrace.length; i++) {
                stackTraceElement = stackTrace[i];
                className = stackTraceElement.getClassName();
                if (clz.getName().equals(className)) {
                    methodName = stackTraceElement.getMethodName();
                    fileName = stackTraceElement.getFileName();
                    lineNumber = stackTraceElement.getLineNumber();
                    sb.append(className).append(".").append(methodName).append("(").append(fileName).append(":").append(lineNumber).append(")\n");
                }
            }
            return sb.toString();
        }
        return "";
    }

}
