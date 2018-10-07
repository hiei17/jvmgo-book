package com.github.jvmgo.instructions.conversions.d2x;


import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Frame;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class D2L extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        double val1 = stack.popDouble();
        long val2 = (long) val1;
        stack.pushLorD(val2);
    }
}
