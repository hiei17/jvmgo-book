package com.github.jvmgo.instructions.base;

import com.github.jvmgo.util.BytecodeReader;

/**
 没有操作数的指令
 */
public abstract class NoOperandsInstruction implements Instruction {
    @Override
    public void fetchOperands(BytecodeReader reader) {}


}
