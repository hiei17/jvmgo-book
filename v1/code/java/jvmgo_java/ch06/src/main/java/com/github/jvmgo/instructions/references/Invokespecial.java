package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 20:37
 */
public class Invokespecial extends Index16Instruction {
    // hack!
    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pop();
    }
}
