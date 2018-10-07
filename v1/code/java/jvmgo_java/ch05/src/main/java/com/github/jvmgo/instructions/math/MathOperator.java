package com.github.jvmgo.instructions.math;

import com.github.jvmgo.instructions.base.DateTypeEnum;
import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;


public class MathOperator extends NoOperandsInstruction {
    DateTypeEnum typeEnum;
    OperatorEnum operatorEnum;
    public MathOperator(DateTypeEnum typeEnum, OperatorEnum operatorEnum) {
        this.typeEnum=typeEnum;
        this.operatorEnum=operatorEnum;
    }

    @Override
    public void execute(Frame frame) {

        OperandStack operandStack = frame.getOperandStack();
        switch (typeEnum){
            case i:
                int i1 = operandStack.popInt();
                int i2 = operandStack.popInt();
                operandStack.push(operatorEnum.getResult(i1,i2));
                break;
            case l:
                long l1 = operandStack.popLong();
                long l2 = operandStack.popLong();
                operandStack.pushLorD(operatorEnum.getResult(l1,l2));
                break;
            case f:
                float f1 = operandStack.popFloat();
                float f2 = operandStack.popFloat();
                operandStack.push(operatorEnum.getResult(f1,f2));
                break;
            case d:
                double d1 = operandStack.popFloat();
                double d2 = operandStack.popFloat();
                operandStack.pushLorD(operatorEnum.getResult(d1,d2));
                break;
        }

    }

}
