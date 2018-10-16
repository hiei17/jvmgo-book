package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.ref.ClassRef;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.OObject;

/**
 class=frame.method.class.runtimeConstantPool(操作数index);
 stack=frame.operandStack;
 object=stack.pop;
 if(object.class isInstanceOf class){
    stack.push(1);
 }else{
   stack.push(0);
 }
 */

public class Instanceof extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        OObject ref = stack.popRef();
        //null什么类型都不是
        if(ref==null){
            stack.push(0);
            return;
        }

       CClass clazz= ((ClassRef) frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(index)).resolveClass();

        if (ref.isInstanceOf(clazz) ){
            stack.push(1);
        } else {
            stack.push(0);
        }
    }
}
