package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.MethodRef;
import com.github.jvmgo.rtda.heap.RuntimeConstantPool;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 20:39
 */
public class Invokevirtual extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getJClass().getRuntimeConstantPool();
        MethodRef methodRef = (MethodRef)runtimeConstantPool.getConstant(index);
        if ("println".equals(methodRef.getName())){
            OperandStack stack = frame.getOperandStack();
            switch (methodRef.getDescriptor()){
                
                case "(Z)V": System.out.print( stack.popInt() != 0);break;
                case "(C)V": System.out.print( stack.popInt());break;
                case "(B)V": System.out.print( stack.popInt());break;
                case "(S)V": System.out.print( stack.popInt());break;
                case "(I)V":
                    System.err.println( stack.popInt());
                break;
                case "(J)V": System.out.print( stack.popLong());break;
                case "(F)V": System.out.print( stack.popFloat());break;
                case "(D)V": System.out.print( stack.popDouble());break;
                default:
                    throw new RuntimeException("print: " + methodRef.getDescriptor());

            }
            stack.pop();
        }
      
    }
}
