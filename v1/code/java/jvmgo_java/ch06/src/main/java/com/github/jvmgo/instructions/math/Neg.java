package com.github.jvmgo.instructions.math;

import com.github.jvmgo.instructions.base.DateTypeEnum;
import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Zframe;

//取反
public class Neg extends NoOperandsInstruction {
    DateTypeEnum typeEnum;

    public Neg(DateTypeEnum typeEnum) {
        this.typeEnum=typeEnum;
    }

    @Override
    public void execute(Zframe frame){
        OperandStack operandStack = frame.getOperandStack();
        switch (typeEnum){
            case i:
                int i1 = operandStack.popInt();

                operandStack.push(-i1);
                break;
            case l:
                long l1 = operandStack.popLong();

                operandStack.pushLorD(-l1);
                break;
            case f:
                float f1 = operandStack.popFloat();

                operandStack.push(-f1);
                break;
            case d:
                double d1 = operandStack.popFloat();

                operandStack.pushLorD(-d1);
                break;
        }
    }

}
