package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.*;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 19:03
 */
//field=frame.method.class.runtimeConstantsPool[指令操作数index]
    //frame.operandStack.push(field.class.staticVars[field.slotId])
public class GetStatic extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        //拿到运行时常量池
        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getJClass().getRuntimeConstantPool();
        Field field= ((FieldRef) runtimeConstantPool.getConstant(index)).resolveField();

        JClass fieldClass=field.getJClass();

        // todo: init class

        if( !field.isStatic() ){
            throw new IncompatibleClassChangeError();
        }

        int  slotId = field.getSlotId();
        //static field 属于class 不用指定实例
        Object val = fieldClass.getStaticVars()[slotId];

        OperandStack operandStack = frame.getOperandStack();
        switch (field.getDescriptor().charAt(0)) {
            case 'J' :
            case  'D':
                operandStack.pushLorD(val);
                break;

            default:
                operandStack.push(val);

                // todo
        }
    }
}
