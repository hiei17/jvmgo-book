package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.*;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 19:03
 */
//取出栈顶对象的field值 入栈 field 操作数指定
public class Getfield extends Index16Instruction {
    @Override
    public void execute(Frame frame) {

        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getJClass().getRuntimeConstantPool();
        Field field= ((FieldRef) runtimeConstantPool.getConstant(index)).resolveField();


        if( field.isStatic() ){
            throw new IncompatibleClassChangeError();
        }



        OperandStack stack = frame.getOperandStack();
        JObject jObject = stack.popRef();
        Object slot = jObject.getSlots()[field.getSlotId()];
        switch (field.getDescriptor().charAt(0)) {
            case 'J' :
            case  'D':
                stack.pushLorD(slot);
                break;

            default:
                stack.push(slot);

                // todo
        }
    }
}
