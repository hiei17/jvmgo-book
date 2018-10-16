package com.github.jvmgo.instructions;

import com.github.jvmgo.instructions.base.DateTypeEnum;
import com.github.jvmgo.instructions.base.Index8Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.heap.OObject;
import com.github.jvmgo.util.BytecodeReader;

/**
 从[局部变量表]获取变量，然后推入[操作数栈]顶
 */
public class Load extends Index8Instruction {

    private DateTypeEnum dateTypeEnum;

    public Load(DateTypeEnum dateTypeEnum, Integer localVarsIndex) {
        this.dateTypeEnum = dateTypeEnum;
        index = localVarsIndex;
    }

    public Load(DateTypeEnum dateTypeEnum) {
        this.dateTypeEnum = dateTypeEnum;
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        if (index==null){
            super.fetchOperands(reader);
        }
    }



    @Override
    public   void execute(Frame frame ) {
        switch (dateTypeEnum){

            case a:
                OObject ref = frame.getLocalVars().getRef(index);
                frame.getOperandStack().push(ref);
                break;
            case i:
                int i = frame.getLocalVars().getInt(index);
                frame.getOperandStack().push(i);
                break;
            case l:
                long l = frame.getLocalVars().getLong(index);
                frame.getOperandStack().pushLorD(l);
                break;
            case f:
                float f = frame.getLocalVars().getFloat(index);
                frame.getOperandStack().push(f);
                break;
            case d:
                double d = frame.getLocalVars().getDouble(index);
                frame.getOperandStack().pushLorD(d);
                break;
        }

    }
}
