package com.github.jvmgo.instructions.base;

/**
 跳转指令
 */

import com.github.jvmgo.rtda.Zframe;
import com.github.jvmgo.util.BytecodeReader;

public abstract class BranchInstruction implements Instruction{

    protected int offSet;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        offSet=reader.nextU2ToShort();
    }

    protected    void branch(Zframe frame) {
        int pc = frame.getThread().getPc();
        int nextPC = pc + offSet;
        frame.setNextPC(nextPC);
    }

}
