package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.ref.ClassRef;
import com.github.jvmgo.rtda.heap.JClass;
import com.github.jvmgo.rtda.heap.JObject;

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
        JObject ref = stack.popRef();
        stack.push(ref);

        if(ref==null){
            return;
        }

        JClass jClass = ((ClassRef) frame.getMethod().getJClass().getRuntimeConstantPool().getConstant(index)).resolveClass();

        if (!ref.isInstanceOf(jClass) ){
            throw new ClassCastException();
        }
    }
}
//
//if (xxx instanceof ClassYYY) {//Instanceof
//        yyy = (ClassYYY) xxx;//Checkcast
//// use yyy
//   }
