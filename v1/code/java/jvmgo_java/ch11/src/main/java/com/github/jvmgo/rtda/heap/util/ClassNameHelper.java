package com.github.jvmgo.rtda.heap.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: panda
 * @Date: 2018/10/10 0010 21:09
 */
public class ClassNameHelper {

    public static HashMap<String,String> primitiveTypes=new HashMap<>();

    static {
        primitiveTypes.put("void", "V");
        primitiveTypes.put("boolean", "Z");
        primitiveTypes.put("byte", "B");
        primitiveTypes.put("short", "S");
        primitiveTypes.put("int", "I");
        primitiveTypes.put("long", "J");
        primitiveTypes.put("char", "C");
        primitiveTypes.put("float", "F");
        primitiveTypes.put("double", "D");
    }

    public static String getArrayClassName(String className) {

        return "[" + toDescriptor(className);
    }

    private static String toDescriptor(String className) {
        //本身就是数组
        if (className.charAt(0) == '[') {
            // array
            return className;
        }

        // primitive
        String type = primitiveTypes.get(className);
        if(type!=null){
           return type;
        }

        // object
        return "L" + className + ";";
    }

    // [XXX  => [XXX
// LXXX; => XXX
// I     => int
    public static String getComponentClassNameFromArrayClassName(String arrayClassName) {
        if(arrayClassName.charAt(0)=='['){
            String s = arrayClassName.substring(1);

            //数组还是数组组成
            if (s.charAt(0) == '[') {
                // array
                return s;
            }

            //对象组成
            if (s.charAt(0) == 'L') {
                // object
                return s.substring(1, s.length() - 1);
            }
            //原始类型
            for (Map.Entry<String, String> entry : primitiveTypes.entrySet()) {
                if (entry.getValue().equals(s)) {
                    return entry.getKey();
                }
            }
            throw new RuntimeException("Invalid descriptor: " + s);
        }
        throw new RuntimeException("Not array: " + arrayClassName);
    }
}
