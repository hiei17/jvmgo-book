package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.ClassRef;
import com.github.jvmgo.rtda.heap.JClass;
import com.github.jvmgo.rtda.heap.JObject;

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
        JObject ref = stack.popRef();
        //null什么类型都不是
        if(ref==null){
            stack.push(0);
            return;
        }

       JClass jClass= ((ClassRef) frame.getMethod().getJClass().getRuntimeConstantPool().getConstant(index)).resolveClass();

        if (ref.isInstanceOf(jClass) ){
            stack.push(1);
        } else {
            stack.push(0);
        }
    }
}
