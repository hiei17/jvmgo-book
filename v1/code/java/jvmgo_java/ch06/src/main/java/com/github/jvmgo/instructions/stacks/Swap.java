package com.github.jvmgo.instructions.stacks;

import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Zframe;

public class Swap extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack operandStack = frame.getOperandStack();
        Object pop = operandStack.pop();
        Object pop2 = operandStack.pop();

        operandStack.push(pop);
        operandStack.push(pop2);
    }
}


