package com.github.jvmgo.instructions.references.array;

import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.ArrayObject;

/**
 * @Author: panda
 * @Date: 2018/10/10 0010 21:53
 */
public class XAstore extends NoOperandsInstruction {
    
    //要弹出三个值 3[2]=1
    @Override
    public void execute(Frame frame) {

        
        OperandStack operandStack = frame.getOperandStack();
        Object data = operandStack.pop();
        int index = operandStack.popInt();
        ArrayObject array= (ArrayObject)operandStack.popRef();
        if(array==null){
            throw new NullPointerException();
        }

        array.set(index,data);
    }
}
