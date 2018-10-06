package com.github.jvmgo.instructions.comparisons.ifacmp;


import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class IfAcmp {
    public static boolean _acmp(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        Object ref2 = stack.popRef();
        Object ref1 = stack.popRef();
        return ref1 == ref2;
    }
}
