package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.ref.ClassRef;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.OObject;

/**
 object=stack.pop()
 stack.push(object);
 class=frame.method.class.runtimeConstantPool(操作数index);

 if(!object.class isInstanceOf class){
    throw new ClassCastException();
 }
 */
public class Checkcast extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        OObject ref = stack.popRef();
        stack.push(ref);

        if(ref==null){
            return;
        }

        CClass clazz = ((ClassRef) frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(index)).resolveClass();

        if (!ref.isInstanceOf(clazz) ){
            throw new ClassCastException();
        }
    }
}
//
//if (xxx instanceof ClassYYY) {//Instanceof
//        yyy = (ClassYYY) xxx;//Checkcast
//// use yyy
//   }
