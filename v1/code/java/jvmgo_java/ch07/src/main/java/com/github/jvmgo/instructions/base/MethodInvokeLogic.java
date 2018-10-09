package com.github.jvmgo.instructions.base;

import com.github.jvmgo.Main;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.LocalVars;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.rtda.heap.Method;

/**
 * @Author: panda
 * @Date: 2018/10/8 0008 20:51
 */
public class MethodInvokeLogic {
    public static void invoke(Frame invokerFrame, Method method) {

        //方法产生一新frame 入线程的虚拟机栈
        Thread thread = invokerFrame.getThread();//原来方法的线程继续用
        thread.pushFrame(method);

        //传参 新方法局部变量表要几个 旧方法就弹出几个
        int argSlotCount = method.getArgSlotCount();
        OperandStack operandStack = invokerFrame.getOperandStack();
        LocalVars localVars = thread.currentFrame().getLocalVars();
        for (int i = argSlotCount - 1; i > -1; i--) {
            Object slot = operandStack.pop();
            localVars.slots[i] = slot;
        }


        // hack!
        if (method.isNative()) {
            if ( "registerNatives".equals(method.getName())) {
                thread.popFrame();
            } else{
                Main.panic(String.format("native method: %s.%s%s\n",
                        method.getJClass().getName(), method.getName(), method.getDescriptor()));
            }
        }

    }
}
