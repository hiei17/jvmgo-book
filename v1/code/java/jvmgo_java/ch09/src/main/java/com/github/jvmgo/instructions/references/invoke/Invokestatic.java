package com.github.jvmgo.instructions.references.invoke;

import com.github.jvmgo.instructions.base.ClassInitLogic;
import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.instructions.base.MethodInvokeLogic;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.rtda.heap.ref.MethodRef;

/**
 * @Author: panda
 * @Date: 2018/10/9 0009 11:18
 * 静态方法是静态绑定的，最终调用的是哪个方法在编译期就已经确定,调用前会检查类有没有初始化,没初始化先初始化再调用
 * methodRef=frame.method.runTimeConstantPool.constant[index来自操作数]
 * method=methodRef.method
 */
public class Invokestatic extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        Object constant = frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(index);
        Method method = ((MethodRef) constant).resolveMethod();

        if (!method.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        //类还没有被初始化，则要先初始化该类
        CClass clazz = method.getClazz();

        if (!clazz.initStarted ){
            frame.revertNextPC();//pc恢复本指令执行以前 初始化以后会再进本Invokestatic
            ClassInitLogic.init(frame.getThread(), clazz);
            return;
        }

        MethodInvokeLogic.invoke(frame, method);
    }
}
