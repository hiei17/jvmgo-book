package com.github.jvmgo.instructions.stacks;

import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Zframe;
import com.github.jvmgo.rtda.OperandStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: panda
 * @Date: 2018/10/5 0005 14:40
 */
public class Dup extends NoOperandsInstruction {
    private int copyData;
    private int offset;

    public Dup(int isTwo, int offset) {
        this.copyData = isTwo;
        this.offset = offset;
    }

    @Override
    public void execute(Zframe frame) {
        List dubObjects=new ArrayList();
        List moveObjects=new ArrayList();
        OperandStack operandStack = frame.getOperandStack();

        for(int i=0;i<copyData;i++){
            dubObjects.add(operandStack.pop());
        }
        for(int i=0;i<offset;i++){
            moveObjects.add(operandStack.pop());
        }
        dubObjects.forEach(operandStack::push);
        moveObjects.forEach(operandStack::push);

    }
}
