package com.github.jvmgo.instructions.comparisons;

import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Zframe;

/**
 * @Author: panda
 * @Date: 2018/10/5 0005 19:47
 */
public class LCMP extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        long val2 = stack.popLong();
        long val1 = stack.popLong();
        if (val1 > val2) {
            stack.push(1);
        } else if (val1 == val2) {
            stack.push(0);
        } else {
            stack.push(-1);
        }
    }
}
