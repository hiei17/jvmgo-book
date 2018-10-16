package com.github.jvmgo.instructions.references.array;

import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.ArrayObject;

/**
 * @Author: panda
 * @Date: 2018/10/10 0010 21:27
 */
public class Arraylength extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        ArrayObject arrayObject= (ArrayObject)operandStack.popRef();

        if (arrayObject == null ){
            throw new NullPointerException();
        }
        operandStack.push(arrayObject.length);
    }
}
