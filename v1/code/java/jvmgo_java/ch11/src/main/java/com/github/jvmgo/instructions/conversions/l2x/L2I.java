package com.github.jvmgo.instructions.conversions.l2x;


import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Frame;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class L2I extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long val1 = stack.popLong();
        int val2 = (int) val1;
        stack.push(val2);
    }
}
