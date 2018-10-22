package com.github.jvmgo.instructions.base;

import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.LocalVars;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.rtda.heap.Method;

/**
 * @Author: panda
 * @Date: 2018/10/8 0008 20:51
 *
 *
 */
public class MethodInvokeLogic {

    /**
     * 方法调用公共逻辑 这里的方法是确定的了
     *
     * 用方法产生新帧入线程里面的虚拟机栈 传参是 老帧弹出入新栈局部变量表
     * @param invokerFrame 要调用方法的帧
     * @param method 被调用的方法
     */
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


    }
}
