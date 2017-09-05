package com.ywd.tool.groovy;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;

/**
 * Created by admin on 2017/8/17.
 */
public class GroovyClassLoaderExample {

    public static void main(String[] args) throws Exception {
        String rootPath = System.getProperty("user.dir");
        String prePath = rootPath + "/src/main/java/com/ywd/tool/groovy/";
        GroovyClassLoader loader = new GroovyClassLoader();
        Class fileCreator = loader.parseClass(new File(prePath + "GroovySimpleFileCreator.groovy"));
        GroovyObject object = (GroovyObject) fileCreator.newInstance();
        object.invokeMethod("say", "ywd");
    }
}
