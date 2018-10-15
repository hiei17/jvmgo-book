package com.github.jvmgo.instructions.reserved;

import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.nnative.NativeMethod;
import com.github.jvmgo.nnative.java.lang.*;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.heap.Method;

/**
 * @Author: panda
 * @Date: 2018/10/12 0012 22:09
 * 我们自己用jvm保留指令0xFE 来实现本地方法调用
 *
 * Method里面 从classFile解析出来的code字节码时
 * 发现方法是本地方法 就往里面放code{0xFE,reture} 所以本地方法都会到这来
 */
public class InvokeNative extends NoOperandsInstruction {

    public InvokeNative() {
        ClassNative.init();
        ObjectNative.init();
        SystemNative.init();
        DoubleNative.init();
        FloatNative.init();
        StringNative.init();

    }

    @Override
    public void execute(Frame frame) {

        Method method = frame.getMethod();
        String className = method.getClazz().getName();
        String methodName = method.getName();
        String descriptor = method.getDescriptor();
        NativeMethod nativeMethod = NativeMethod.findNativeMethod(className, methodName, descriptor);
        if (nativeMethod == null) {
          String  methodInfo = className + "." + methodName + descriptor;
          throw new UnsatisfiedLinkError(methodInfo);
        }

        nativeMethod.execute(frame);
    }


}
