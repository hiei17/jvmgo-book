package com.github.jvmgo.classpath;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Classpath {

    private Entry bootClasspath;
    private Entry extClasspath;
    private Entry userClasspath;

    public Classpath(String jreOption, String cpOption) {
        parseBootAndExtClasspath(jreOption);
        parseUserClasspath(cpOption);
    }

    //mark jvm规范推荐 双亲加载 就是 先让父加载器去加载 这些走完了 再自定义的类记载器

    /**
     * public class Object{
     * //会报错 找不到main方法
     *  public static void main(String[] args) {
     *      Object obj = new 0bject();
     *      System.out.println("Hello");
     *  }
     * }
     * @param jreOption
     */
    private void parseBootAndExtClasspath(String jreOption) {
        String jreDir = getJreDir(jreOption);

        //D:\Java\jdk1.8.0_102\jre\lib
        //mark jre/lib/*
        String jreLibPath = Paths.get(jreDir, "lib").toString() + File.separator + "*";
        bootClasspath = new WildcardEntry(jreLibPath);

        //mark jre/lib/ext/*
        String jreExtPath = Paths.get(jreDir, "lib", "ext").toString() + File.separator + "*";
        extClasspath = new WildcardEntry(jreExtPath);
    }

    private static String getJreDir(String jreOption) {

        if (jreOption != null && Files.exists(Paths.get(jreOption))) {
            return jreOption;
        }
        if (Files.exists(Paths.get("./jre"))) {
            return "./jre";
        }
        String jh = System.getenv("JAVA_HOME");
        if (jh != null) {
            return Paths.get(jh, "jre").toString();
        }
        throw new RuntimeException("Can not find JRE folder!");
    }

    private void parseUserClasspath(String cpOption) {
        if (cpOption == null) {
            cpOption = ".";
        }
        userClasspath = Entry.create(cpOption);
    }

    // className: fully/qualified/ClassName
    public byte[] readClass(String className) throws Exception {
        className = className + ".class";


        //C++ 写 加载核心类库java.*  默认 JRE\Iib\rt.jar 里面找
        try {
            return bootClasspath.readClass(className);
        } catch (Exception ignored) {

        }

        //java 写 加载扩展库javax.* 默认RE\Iib\ext\*.jar 里面找
        try {
            return extClasspath.readClass(className);
        } catch (Exception ignored) {

        }

        // 默认 CLASSPATH 里面找
        return userClasspath.readClass(className);
    }


    public static String join(String... paths) {
        return Arrays.stream(paths)
                .collect(Collectors.joining(File.pathSeparator));
    }

    public static String[] split(String pathList) {
        return pathList.split(File.pathSeparator);
    }

}
