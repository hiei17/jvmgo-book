package com.github.jvmgo.nnative.java.lang;

import com.github.jvmgo.nnative.NativeMethod;

/**
 * @Author: panda
 * @Date: 2018/10/14 0014 21:32
 */
/*

Math类在初始化时需要调用
Float.floatToRawIntBits（）和
Double.doubleToRawLongBits（）
package java.lang;
public final class Math {
	// Use raw bit-wise conversions on guaranteed non-NaN arguments.
	private static long negativeZeroFloatBits = Float.floatToRawIntBits(-0.0f);
	private static long negativeZeroDoubleBits = Double.doubleToRawLongBits(-0.0d);
}
*/
public class FloatNative {

    static final String  jlFloat = "java/lang/Float";
    private static NativeMethod floatToRawIntBits=frame -> {
        Float aFloat = frame.getLocalVars().getFloat(0);
        frame.getOperandStack().push(Float.floatToRawIntBits(aFloat));
    };

    public static void init() {
        NativeMethod.register(jlFloat, "floatToRawIntBits", "(F)I", floatToRawIntBits);
      //  NativeMethod.register(jlFloat, "intBitsToFloat", "(I)F", intBitsToFloat);
    }


  /*  // public static native float intBitsToFloat(int bits);
// (I)F
    func intBitsToFloat(frame *rtda.Frame) {
        bits := frame.LocalVars().GetInt(0)
        value := math.Float32frombits(uint32(bits)) // todo
        frame.OperandStack().PushFloat(value)
    }*/
}
