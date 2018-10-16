package com.github.jvmgo.rtda.heap.util;

import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.Method;

/**
 * @Author: panda
 * @Date: 2018/10/8 0008 19:14
 */
public class MethodLookupUtil {


    public static Method lookupMethodInInterfaces(CClass clazz, String name, String descriptor) {



        Method amethod = clazz.getMethod(name, descriptor);
        if (amethod!=null){
            return amethod;
        }

        //遍历这些接口找
        for (CClass anInterface : clazz.interfaces) {
            Method method = anInterface.getMethod(name, descriptor);
            if (method!=null){
                return method;
            }
            //接口的接口里面找
            method = lookupMethodInInterfaces(anInterface, name, descriptor);
            if (method!=null){
                return method;
            }
        }
        return null;
    }

    public static Method lookupMethodInClass(CClass clazz, String name, String descriptor) {
        for(CClass aClass = clazz; aClass!=null; aClass=aClass.superClass){
            Method method = aClass.getMethod(name, descriptor);
            if (method!=null){
                return method;
            }
        }
        return null;
    }

}
