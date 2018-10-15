package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.ClassInitLogic;
import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.*;
import com.github.jvmgo.rtda.heap.ref.FieldRef;
import com.github.jvmgo.rtda.heap.ref.RuntimeConstantPool;

/**
 根据操作数index去运行时常量池拿到field,弹出一个值set进field值
 field=frame.method.class.runtimeConstantsPool[index]
 field.class.staticVars[field.slotId]
 */
public class PutStatic extends Index16Instruction {
    @Override
    public void execute(Frame frame) {

        Method method = frame.getMethod();
        RuntimeConstantPool runtimeConstantPool = method.getClazz().getRuntimeConstantPool();
        Field field= ((FieldRef) runtimeConstantPool.getConstant(index)).resolveField();
        CClass currentClass= method.getClazz();
        CClass fieldClass=field.getClazz();

        if (!fieldClass.initStarted ){
            frame.revertNextPC();//pc恢复本指令执行以前 初始化以后会再进本Invokestatic
            ClassInitLogic.init(frame.getThread(), fieldClass);
            return;
        }

        if( !field.isStatic() ){
            throw new IncompatibleClassChangeError();
        }
        if( field.isFinal() ){
            if (currentClass != fieldClass || !"<clinit>".equals(method.getName())) {
                throw new IllegalAccessError();
            }
        }

        String descriptor = field.getDescriptor();

        OperandStack operandStack = frame.getOperandStack();
        Object val=null;
        switch (descriptor.charAt(0)) {
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
        fieldClass.getStaticVars()[slotId]=val;
    }
}
