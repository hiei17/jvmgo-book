package com.github.jvmgo.instructions.references.array;

import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.ArrayObject;

/**
 * @Author: panda
 * @Date: 2018/10/10 0010 21:36
 *
 * stack.push(stack.pop2[stack.pop1])
 */
public class XAload extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {

        OperandStack operandStack = frame.getOperandStack();
        int index = operandStack.popInt();
        ArrayObject arrayObject = (ArrayObject) operandStack.popRef();
        if(arrayObject==null){
            throw new NullPointerException();
        }
        operandStack.push(arrayObject.get(index));
    }
}
