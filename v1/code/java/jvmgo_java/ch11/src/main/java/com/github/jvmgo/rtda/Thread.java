package com.github.jvmgo.rtda;

import com.github.jvmgo.rtda.heap.Method;
import lombok.Data;

/**
线程私有的运行时数据区
 * 创建线程时才创建，线程退出时销毁。用于辅助执行Java字节码。
 * 每个线程都有一个
 */
@Data
public class Thread {

    //如果当前是本地方法 这个值没规定
    //当前method执行的字节码在方法的code[]里面排第几个
    private int pc;// 指令复位(先去类初始化以后要回来再执行本方法) 和 跳转(跳转从这个跳转指令之前的位置开始算距离)

    //这个实现里面 本地方法栈 和 虚拟机栈 用同一个
    //Java虚拟机栈 由栈帧（Stack Frame，简称帧）构成，一方法一帧
    private JVMStack jvmStack=new JVMStack();//平时我们所说的 栈内存




    public Frame popFrame()  {
        return jvmStack.pop();
    }

    public Frame currentFrame()  {
        return jvmStack.top();
    }

    public Frame pushFrame(Method method) {
        Frame frame = new Frame(method,this);
        jvmStack.push(frame);
        return frame;
    }

    public void pushFrame(Frame frame) {
        jvmStack.push(frame);
    }


    public boolean isStackEmpty() {
        return jvmStack.isEmpty();
    }

    public void clearStack() {
        jvmStack.clearn();
    }

    public Frame[] getFrames(int skip) {
       return jvmStack.getFrames(skip);
    }
}
