package com.github.jvmgo.instructions;

import com.github.jvmgo.instructions.base.Index8Instruction;
import com.github.jvmgo.instructions.base.DateTypeEnum;
import com.github.jvmgo.rtda.Zframe;
import com.github.jvmgo.util.BytecodeReader;

/**
 存储指令把变量从操作数栈顶弹出，然后存入局部变量表。
 */
public class Store extends Index8Instruction {
    private DateTypeEnum dateTypeEnum;


    public Store(DateTypeEnum dateTypeEnum, Integer localVarsIndex) {
        this.dateTypeEnum=dateTypeEnum;
        index=localVarsIndex;
    }

    public Store(DateTypeEnum dateTypeEnum) {
        this.dateTypeEnum=dateTypeEnum;
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        if ( index==null){
            super.fetchOperands(reader);
        }
    }

    @Override
    public   void execute(Zframe frame ) {
        switch (dateTypeEnum){

            case a:
                Object ref= frame.getOperandStack().popRef();
                frame.getLocalVars().setRef(index,ref);
                break;
            case i:
                int i =  frame.getOperandStack().popInt();
                frame.getLocalVars().setInt(index,i);
                break;
            case l:
                long l = frame.getOperandStack().popLong();
                frame.getLocalVars().setLong(index,l);
                break;
            case f:
                float f = frame.getOperandStack().popFloat();
               frame.getLocalVars().setFloat(index,f);
                break;
            case d:
                double d =  frame.getOperandStack().popDouble();
                frame.getLocalVars().setDouble(index,d);
                break;
        }

    }
}
