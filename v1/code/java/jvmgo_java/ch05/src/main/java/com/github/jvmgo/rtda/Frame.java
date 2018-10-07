package com.github.jvmgo.rtda;

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

  private Thread thread;//本栈帧所属线程

  private int nextPC=0 ;// the next instruction after the call

    //执行方法所需的局部变量表大小和操作数栈深度是由编译器预先计算好的，
    // 存储在class文件method_info结构的Code属性中
    public Frame(int maxLocals, int maxStack) {
        this.localVars = new LocalVars(maxLocals);
        this.operandStack = new OperandStack(maxStack);
    }



}
