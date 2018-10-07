package com.github.jvmgo.instructions.comparisons.ifacmp;


import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class IfAcmp {
    public static boolean _acmp(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Object ref2 = stack.popRef();
        Object ref1 = stack.popRef();
        return ref1 == ref2;
    }
}
