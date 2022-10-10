package com.momolela.invokegroovy;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.codehaus.groovy.runtime.InvokerHelper;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class App {
    public static void main(String[] args) throws Exception {
        Object[] param = {8, 7};
        Object result = invoke01("def cal(int a, int b){\n" +
                " return a+b\n" +
                "}", "cal", param);
        System.out.println(result);
    }

    // 方式一，使用 GroovyClassLoader 调用
    public static <T> T invoke01(String scriptText, String func, Object... objs) throws Exception {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class groovyClazz = groovyClassLoader.parseClass(scriptText);

        GroovyObject groovyObject = (GroovyObject) groovyClazz.newInstance();
        Object result = groovyObject.invokeMethod(func, objs);
        return (T) result;
    }

    // 方式二，使用ScriptEngine调用
    public <T> T invoke02(String scriptText, String func, Object... objs) throws ScriptException, NoSuchMethodException {
        ScriptEngine scriptEngine = new GroovyScriptEngineFactory().getScriptEngine();
        scriptEngine.eval(scriptText);
        Object result = ((Invocable) scriptEngine).invokeFunction(func, objs);
        return (T) result;
    }

    // 方式三，使用GroovyShell调用(推荐)
    public <T> T invoke03(String scriptText, String func, Object... objs) {
        GroovyShell groovyShell = new GroovyShell();
        Script script = groovyShell.parse(scriptText);
        Object result = InvokerHelper.invokeMethod(script, func, objs);
        return (T) result;
    }
}
