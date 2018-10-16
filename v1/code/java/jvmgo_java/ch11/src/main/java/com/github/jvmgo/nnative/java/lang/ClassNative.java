package com.github.jvmgo.nnative.java.lang;

import com.github.jvmgo.nnative.NativeMethod;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.MyClassLoader;
import com.github.jvmgo.rtda.heap.OObject;
import com.github.jvmgo.rtda.heap.StringPool;

/**
 * @Author: panda
 * @Date: 2018/10/14 0014 13:36
 *
 * 注册java/lang/Class里面的本地方法
 */
public class ClassNative {

    public static void init(){
        NativeMethod.register(
                "java/lang/Class",
                "getPrimitiveClass",
                "(Ljava/lang/String;)Ljava/lang/Class;",
                getPrimitiveClass);
        NativeMethod.register(
                "java/lang/Class",
                "getName0",
                "()Ljava/lang/String;",
                getName0);
        NativeMethod.register("java/lang/Class",
                "desiredAssertionStatus0",
                "(Ljava/lang/Class;)Z",
                desiredAssertionStatus0);
    }

    //基本类型的包装类在初始化时会调用
//如Integer类里面初始化会执行:
//public static final Class<Integer> TYPE = (Class<Integer>) Class.getPrimitiveClass("int");
//让类加载器加载int基本类 得其class对象 入栈
    private static final NativeMethod getPrimitiveClass =frame -> {

        //拿到本基本类的类名
        OObject nameStringObject = frame.getLocalVars().getRef(0);//"int"java的String对象
        String name = StringPool.getString(nameStringObject);//"int"字符串 本地语言

        //从类加载器得到这个基本类→得其对应class对象
        MyClassLoader classLoader = frame.getMethod().getClazz().classLoader;
        OObject primitiveClassObject = classLoader.loadClass(name).jClass;
        //入栈
        frame.getOperandStack().push(primitiveClassObject);
    } ;



    //.getName（）方法 内部是调用这个的
    //例如
    /**
     * Class c=Integer.class;
     * c.getName();//内部调用getName0
     */
    private static final NativeMethod getName0 = frame -> {
        //class对象
        OObject thisClassObject = frame.getLocalVars().getRef(0);
        //这个Class类的
        CClass clazz = (CClass)thisClassObject.extra;
        //拿到它的类名(.分隔的而不是/) 转成string对象入栈
        String clazzName = clazz.getJavaName();
        //本地语言字符串包成java的String对象 入栈
        OObject stringObject = StringPool.JString(clazz.classLoader,clazzName);
        frame.getOperandStack().push(stringObject);
    };

    private static final NativeMethod desiredAssertionStatus0 = frame -> {
        //不实现
        frame.getOperandStack().push(false);
    };


}
