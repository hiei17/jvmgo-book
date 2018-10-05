package com.github.jvmgo.instructions.math;

import com.github.jvmgo.instructions.base.DateTypeEnum;
import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Zframe;

/**
位移
 */
public class Sh extends NoOperandsInstruction {

    DateTypeEnum typeEnum;
    //是否逻辑 (右移补0>>>
    boolean isUn;
    //是右移
    boolean isRight;

    public Sh(DateTypeEnum typeEnum, boolean isRight, boolean isUn) {
        this.typeEnum = typeEnum;
        this.isUn = isUn;
        this.isRight = isRight;
    }

    @Override
    public void execute(Zframe frame) {
        OperandStack operandStack = frame.getOperandStack();
        int s = operandStack.popInt();
        switch (typeEnum){
            case l:
                long pop = operandStack.popLong();
                operandStack.pushLorD(moveLong(s,pop));
                break;
            case i:
                int pop2 = operandStack.popInt();
                operandStack.push(moveInt(s,pop2));
                break;
        }

    }

    private long moveLong(int s, long pop) {
        if(isRight){
            if(isUn){
                return pop>>>s;
            }else {
                return pop>>s;
            }
        }
        return pop<<s;
    }
    private int moveInt(int s, int pop) {
        if(isRight){
            if(isUn){
                return pop>>>s;
            }else {
                return pop>>s;
            }
        }
        return pop<<s;
    }
}
