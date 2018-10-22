package com.github.jvmgo.nnative.sun.misc;

import com.github.jvmgo.instructions.base.MethodInvokeLogic;
import com.github.jvmgo.nnative.NativeMethod;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.rtda.heap.MyClassLoader;

/**
 * @Author: panda
 * @Date: 2018/10/15 0015 15:11
 */
public class VM {

    //初始化时注册此方法
    public static void init() {



        //System类的初始化过程 会调用 要在main之前准备好
        NativeMethod.register("sun/misc/VM", "initialize", "()V", initialize);



    }


//只是调用了System.initializeSystemClass（）方法而已
    private static    NativeMethod initialize=frame ->{

        //找到方法
        MyClassLoader classLoader = frame.getMethod().getClazz().classLoader;
        CClass systemClass = classLoader.loadClass("java/lang/System");
        Method initializeSystemClassMethod = systemClass.getMethod("initializeSystemClass", "()V");

        //调用
        MethodInvokeLogic.invoke(frame,initializeSystemClassMethod);

    };



}
