package com.momolela.invokegroovy;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.groovy.runtime.InvokerHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroovyUtils {
    private static Map<String, Script> scriptCache = new ConcurrentHashMap<>();

    private static GroovyShell groovyShell = new GroovyShell();

    public static <T> T invoke(String scriptText, String function, Object... objects) throws Exception {
        Script script;

        String md5Hex = DigestUtils.md5Hex(scriptText);
        if (scriptCache.containsKey(md5Hex)) {
            script = scriptCache.get(md5Hex);
        } else {
            script = groovyShell.parse(scriptText);
            scriptCache.put(md5Hex, script);
        }

        return (T) InvokerHelper.invokeMethod(script, function, objects);
    }
}
