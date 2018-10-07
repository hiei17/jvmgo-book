package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.*;

/**
pop一个值找到field 弹出值val 弹出对象引用  set对象 这个field 为val
 */
public class Putfield extends Index16Instruction {
    @Override
    public void execute(Frame frame) {

        Method method = frame.getMethod();
        RuntimeConstantPool runtimeConstantPool = method.getJClass().getRuntimeConstantPool();
        Field field= ((FieldRef) runtimeConstantPool.getConstant(index)).resolveField();
        JClass currentClass= method.getJClass();
        JClass fieldClass=field.getJClass();

        // todo: init class

        if( field.isStatic() ){
            throw new IncompatibleClassChangeError();
        }
        if( field.isFinal() ){
            if (currentClass != fieldClass || !"<clinit>".equals(method.getName())) {
                throw new IllegalAccessError();
            }
        }

        OperandStack operandStack = frame.getOperandStack();
        Object val=null;
        switch (field.getDescriptor().charAt(0)) {//field需要的是什么类型的数
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                val=operandStack.popInt();
                break;
            case 'F':
                val=operandStack.popFloat();
                break;
            case 'J':
                val=operandStack.popLong();
                break;
            case 'D':
                val=operandStack.popDouble();
                break;
            case 'L':
            case '[':
                val=operandStack.popRef();
                break;
            default:
                break;
        }
        int  slotId = field.getSlotId();
        JObject jObject = operandStack.popRef();
        jObject.getSlots()[slotId]=val;
    }
}
