package com.github.jvmgo.rtda.heap.util;

import com.github.jvmgo.rtda.heap.JClass;
import com.github.jvmgo.rtda.heap.Method;

/**
 * @Author: panda
 * @Date: 2018/10/8 0008 19:14
 */
public class MethodLookupUtil {


    public static Method lookupMethodInInterfaces(JClass jClass, String name, String descriptor) {
        //遍历这些接口找
        for (JClass anInterface : jClass.interfaces) {
            Method method = anInterface.getMethod(name, descriptor);
            if (method!=null){
                return null;
            }
            //接口的接口里面找
            method = lookupMethodInInterfaces(anInterface, name, descriptor);
            if (method!=null){
                return null;
            }
        }
        return null;
    }

    public static Method lookupMethodInClass(JClass jClass, String name, String descriptor) {
        for(JClass aClass=jClass;aClass!=null;aClass=aClass.superClass){
            Method method = aClass.getMethod(name, descriptor);
            if (method!=null){
                return method;
            }
        }
        return null;
    }

}
