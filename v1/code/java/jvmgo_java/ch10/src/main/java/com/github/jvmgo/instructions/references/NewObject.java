package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.ClassInitLogic;
import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.heap.ref.ClassRef;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.ref.RuntimeConstantPool;

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

        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getClazz().getRuntimeConstantPool();
        CClass clazz= ((ClassRef) runtimeConstantPool.getConstant(index)).resolveClass();
        if(clazz.isInterface()||clazz.isAbstract()){
            throw new InstantiationError(clazz.getName());
        }
        if (!clazz.initStarted ){
            frame.revertNextPC();//pc恢复本指令执行以前 初始化以后会再进本Invokestatic
            ClassInitLogic.init(frame.getThread(), clazz);
            return;
        }

        frame.getOperandStack().push(clazz.newObject());
    }
}
