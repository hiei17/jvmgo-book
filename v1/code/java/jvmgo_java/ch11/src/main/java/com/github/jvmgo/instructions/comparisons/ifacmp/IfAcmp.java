package com.github.jvmgo.instructions.comparisons.ifacmp;


import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.OObject;

import java.util.Objects;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class IfAcmp {
    public static boolean _acmp(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        OObject ref2 = stack.popRef();
        OObject ref1 = stack.popRef();
        return Objects.equals(ref1,ref2);
    }
}
