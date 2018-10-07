package com.github.jvmgo.rtda;

import lombok.Data;

/**
线程私有的运行时数据区
 * 创建线程时才创建，线程退出时销毁。用于辅助执行Java字节码。
 * 每个线程都有一个
 */
@Data
public class Thread {


    private int pc;
    //Java虚拟机栈 由栈帧（Stack Frame，简称帧）构成，一方法一帧
    private JVMStack jvmStack=new JVMStack();



   public  void pushFrame( Frame frame) {
       frame.setThread(this);
       jvmStack.push(frame);
    }
    public Frame popFrame()  {
        return jvmStack.pop();
    }

    public Frame currentFrame()  {
        return jvmStack.top();
    }
}
