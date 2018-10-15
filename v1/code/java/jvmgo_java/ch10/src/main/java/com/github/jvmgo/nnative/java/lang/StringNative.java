package com.github.jvmgo.nnative.java.lang;

import com.github.jvmgo.nnative.NativeMethod;
import com.github.jvmgo.rtda.heap.OObject;
import com.github.jvmgo.rtda.heap.StringPool;

/**
 * @Author: panda
 * @Date: 2018/10/14 0014 21:44
 */
public class StringNative {
//字符串池

    public static void init() {

        NativeMethod intern=frame -> {
            OObject thisStringObject = frame.getLocalVars().getRef(0);
            OObject interned=  StringPool.internString(thisStringObject);
            frame.getOperandStack().push(interned);
        };
        NativeMethod.register("java/lang/String", "intern", "()Ljava/lang/String;", intern);
    }


}
