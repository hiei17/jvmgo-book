package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.heap.ClassRef;
import com.github.jvmgo.rtda.heap.JClass;
import com.github.jvmgo.rtda.heap.RuntimeConstantPool;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 18:24
 */

/**
 * class=frame.method.class.runtimeConstantPool(操作数index)
 * newObject={
 *     class
 *     slot[class.instanceSlotCount]
 * }
 * frame.operandStack.push(newObject);
 */
public class NewObject extends Index16Instruction {
    @Override
    public void execute(Frame frame) {

        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getJClass().getRuntimeConstantPool();
        JClass jClass= ((ClassRef) runtimeConstantPool.getConstant(index)).resolveClass();
        if(jClass.isInterface()||jClass.isAbstract()){
            throw new InstantiationError(jClass.getName());
        }


        frame.getOperandStack().push(jClass.newObject());
    }
}
