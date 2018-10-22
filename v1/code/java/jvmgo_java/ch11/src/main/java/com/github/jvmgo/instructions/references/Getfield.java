package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.*;
import com.github.jvmgo.rtda.heap.ref.FieldRef;
import com.github.jvmgo.rtda.heap.RuntimeConstantPool;

/**
 field=frame.method.class.runtimeConstantsPool[指令操作数index];
 stack=frame.operandStack;
 object=stack.pop;
 stack.push(object.slot[field.slotId]);
 */

public class Getfield extends Index16Instruction {
    @Override
    public void execute(Frame frame) {


        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getClazz().getRuntimeConstantPool();
        Field field= ((FieldRef) runtimeConstantPool.getConstant(index)).resolveField();


        if( field.isStatic() ){
            throw new IncompatibleClassChangeError();
        }

        OperandStack stack = frame.getOperandStack();
        OObject jObject = stack.popRef();
        Object slot = jObject.getSlots()[field.getSlotId()];
        switch (field.getDescriptor().charAt(0)) {
            case 'J' :
            case  'D':
                stack.pushLorD(slot);
                break;

            default:
                stack.push(slot);
        }
    }
}
