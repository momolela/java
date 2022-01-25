package com.momolela.jdk8.nashornscript;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class NashornScript01Base {
    public static void main(String[] args) {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        try {
            //nashorn.eval("console.log(1)"); // 执行脚本失败
            String str = "hello world";
            nashorn.eval("print('" + str + "');");
            Integer result = (Integer) nashorn.eval("1 + 2");
            System.out.println(result);

            // 另外执行
            nashorn.eval("function invoke (args) {\n" +
                    "    var outLabel=[];\n" +
                    "    var excludeLabel=[];\n" +
                    "    if (args.label == \"a\") {\n" +
                    "        outLabel.push(\"a\");\n" +
                    "    } else {\n" +
                    "        outLabel.push(\"b\");\n" +
                    "    }\n" +
                    "    var returnMap = {ol:outLabel,el:excludeLabel};\n" +
                    "    return JSON.stringify(returnMap);\n" +
                    "}");
            try {
                Invocable invocable = (Invocable) nashorn;
                String resultStr = (String) invocable.invokeFunction("invoke", "{\"label\":\"a\"}");
                System.out.println(resultStr);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        } catch (ScriptException e) {
            e.printStackTrace();
            System.out.println("执行脚本失败");
        }
    }
}