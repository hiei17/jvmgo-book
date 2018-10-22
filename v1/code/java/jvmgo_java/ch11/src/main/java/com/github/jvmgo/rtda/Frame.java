package com.github.jvmgo.rtda;

import com.github.jvmgo.rtda.heap.Method;
import lombok.Data;

/**
 * @Author: panda
 * @Date: 2018/10/4 0004 12:46
 */
@Data
public class Frame {



    //这2者大小由编译器就计算好了, classFile里面的method表里面有写
    //局部变量表
  private   LocalVars localVars    ;

  //操作数栈
  private OperandStack operandStack ;

  private Method method ;

  private Thread thread;//本栈帧所属线程

  private int nextPC=0 ;// the next instruction after the call




    public Frame(Method method, Thread thread) {
        this.localVars = new LocalVars(method.getMaxLocals());
        this.operandStack = new OperandStack(method.getMaxStack());
        this.method=method;
        this.thread=thread;
    }

    public Frame(Thread thread, OperandStack ops) {
        this.method=Method.newShimReturnMethod();
        this.operandStack =ops;
        this.thread=thread;
    }

    public static Frame newShimFrame(Thread thread, OperandStack ops) {
        return new Frame(thread,ops);
    }

    public void revertNextPC() {
        nextPC=thread.getPc();
    }

    @Override
    public String toString() {
        return method.getName();

    }


}
