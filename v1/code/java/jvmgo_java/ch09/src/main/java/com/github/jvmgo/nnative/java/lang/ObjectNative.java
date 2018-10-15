package com.github.jvmgo.nnative.java.lang;

import com.github.jvmgo.nnative.NativeMethod;
import com.github.jvmgo.rtda.heap.OObject;

/**
 * @Author: panda
 * @Date: 2018/10/14 0014 13:13
 *
 * java.lang.Object里的本地方法
 */
public class ObjectNative {
   static final String  jlObject = "java/lang/Object";

   //this的对应class对象入栈  这样所有类都能用 类名.getClass()来得到对应class对象了
    private static final NativeMethod getClass= frame->{
        OObject thisObject = frame.getLocalVars().getRef(0);
        OObject classObject = thisObject.getClazz().jClass;
        frame.getOperandStack().push(classObject);
    };

    public static void init() {
        //java.lang.Object.getClass（）
        NativeMethod.register(jlObject, "getClass", "()Ljava/lang/Class;", getClass);

       // NativeMethod.register(jlObject, "hashCode", "()I", hashCode);
       // NativeMethod.register(jlObject, "clone", "()Ljava/lang/Object;", clone);
    }



}
