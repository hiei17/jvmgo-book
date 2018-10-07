package com.github.jvmgo.instructions.constants;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 20:30
 * 当前类的运行时常量池中取出常量 入栈
 */
public class Ldc extends Index16Instruction {
    private Boolean isIndexW;
    private Boolean isPushW;


    public Ldc(Boolean isIndexW, Boolean isPushW) {
        this.isIndexW = isIndexW;
        this.isPushW = isPushW;
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        if(!isIndexW) {
            index = reader.nextU1toInt();
        }
    }

    @Override
    public void execute(Frame frame) {

        OperandStack operandStack = frame.getOperandStack();
        Object constant = frame.getMethod().getJClass().getRuntimeConstantPool().getConstant(index);
        if (isPushW){
            operandStack.pushLorD(constant);
        }else {
            operandStack.push(constant);
        }
    }
}
