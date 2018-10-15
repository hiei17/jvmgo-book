package com.github.jvmgo.nnative;

import com.github.jvmgo.rtda.Frame;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: panda
 * @Date: 2018/10/12 0012 22:27
 */

public  interface NativeMethod {


    Map<String,NativeMethod> registry=new HashMap<>();


    static void register(String className,String methodName,String methodDescriptor ,NativeMethod method ) {
        String  key = className + "~" + methodName + "~" + methodDescriptor;
        registry.put(key,method);
    }

    static NativeMethod findNativeMethod( String className,String methodName,String methodDescriptor )  {
        String  key = className + "~" + methodName + "~" + methodDescriptor;
        NativeMethod nativeMethod = registry.get(key);
        if (nativeMethod!=null){
            return nativeMethod;
        }

        if("()V".equals(methodDescriptor) && "registerNatives".equals(methodName)){
            return NativeMethod.emptyMethod();
        }
        return null;
    }

     static NativeMethod emptyMethod() {
        return  frame->{};
    }

    void execute(Frame frame) ;


}
