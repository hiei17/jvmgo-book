package com.github.jvmgo.nnative.java.lang;

import com.github.jvmgo.nnative.NativeMethod;

/**
 * @Author: panda
 * @Date: 2018/10/14 0014 21:32
 */
/*

同FloatNative
*/
public class DoubleNative {

    static final String  jlDouble = "java/lang/Double";
    private static NativeMethod doubleToRawLongBits = frame -> {
        double aDouble = frame.getLocalVars().getDouble(0);

        frame.getOperandStack().pushLorD(Double.doubleToLongBits(aDouble));
    };
    private static NativeMethod longBitsToDouble = frame -> {
        Long aLong = frame.getLocalVars().getLong(0);

        frame.getOperandStack().pushLorD(Double.longBitsToDouble(aLong));
    };

    public static void init() {
        NativeMethod.register(jlDouble, "doubleToRawLongBits", "(D)J", doubleToRawLongBits);
        NativeMethod.register(jlDouble, "longBitsToDouble", "(J)D", longBitsToDouble);

    }


}
