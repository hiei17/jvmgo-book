package com.github.jvmgo.nnative.sun.misc;

import com.github.jvmgo.instructions.base.MethodInvokeLogic;
import com.github.jvmgo.nnative.NativeMethod;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.*;

/**
 * @Author: panda
 * @Date: 2018/10/15 0015 15:11
 */
public class VM {

    //初始化时注册此方法
    public static void init() {




        NativeMethod.register("sun/misc/VM", "initialize", "()V", initialize);



    }
/*
    private static    NativeMethod initialize=frame ->
            //只是调用了System.initializeSystemClass（）方法而已
// private static native void initialize();
    {
        MyClassLoader classLoader = frame.getMethod().getClazz().classLoader;
        CClass systemClass = classLoader.loadClass("java/lang/System");
        Method initStaticSysClass= systemClass.getMethod("initializeSystemClass", "()V");
        MethodInvokeLogic.invoke(frame,initStaticSysClass);
    };  */

/*
    IntegerCache在初始化时
    需要确定缓存池中Integer对象的上限值，为此它调用了
    sun.misc.VM类的getSavedProperty（）方法。要想让VM正确初始化需
    要做很多工作，这个工作推迟到第11章进行。

    todo 这里先用一个hack让
    VM.getSavedProperty（）方法返回非null值，以便IntegerCache可以正
    常初始化。
    */
    private static    NativeMethod initialize=frame ->{
    CClass vmClazz = frame.getMethod().getClazz();
    Object savedProps = vmClazz.getStaticFieldVal("savedProps", "Ljava/util/Properties;");
    MyClassLoader classLoader = vmClazz.classLoader;

    OObject key = StringPool.JString(classLoader, "foo");
    OObject val = StringPool.JString(classLoader, "bar");

    OperandStack stack = frame.getOperandStack();
    stack.push(savedProps);
    stack.push(key);
    stack.push(val);

   CClass propsClass = classLoader.loadClass("java/util/Properties");

  Method  setPropMethod = propsClass.getMethod("setProperty",
            "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");

    MethodInvokeLogic.invoke(frame,setPropMethod);
    };



}
