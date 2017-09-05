package com.ywd.tool.groovy;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

/**
 * Created by admin on 2017/8/16.
 */
public class GroovyScriptEngineExample {
    public static void main(String args[]) {
        try {
            String rootPath = System.getProperty("user.dir");
            String[] roots = new String[]{rootPath + "/data"};//定义Groovy脚本引擎的根路径
            GroovyScriptEngine engine = new GroovyScriptEngine(roots);
            Binding binding = new Binding();
            binding.setVariable("language", "Groovy");
            Object value = engine.run("simple.groovy", binding);
            assert value.equals("success");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
