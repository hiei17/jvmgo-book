package com.github.jvmgo.rtda;

import com.github.jvmgo.rtda.heap.Method;
import lombok.Data;

/**
 * @Author: panda
 * @Date: 2018/10/4 0004 12:46
 */
@Data
public class Frame {
    //局部变量表
  private   LocalVars localVars    ;

  //操作数栈
  private OperandStack operandStack ;
  private Method method ;


  private Thread thread;//本栈帧所属线程

  private int nextPC=0 ;// the next instruction after the call


    public Frame(Method method) {
        this.localVars = new LocalVars(method.getMaxLocals());
        this.operandStack = new OperandStack(method.getMaxStack());
        this.method=method;
    }
}
