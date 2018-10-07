package com.github.jvmgo.instructions.stacks;

import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;

/**
 * @Author: panda
 * @Date: 2018/10/5 0005 14:23
 */
public class Pop extends NoOperandsInstruction {

    private int popAmount;

    public Pop(int i) {
        popAmount =i;
    }

    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        switch (popAmount){
            case 1:
            operandStack.pop();break;
            case 2:
            operandStack.pop2();break;
        }
    }
}
