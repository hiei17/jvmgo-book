package com.github.jvmgo.instructions.math;

import com.github.jvmgo.instructions.base.Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.LocalVars;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/5 0005 17:41
 */
public class Iinc implements Instruction {
    public int index;
    public int add;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.nextU1toInt();
        add = reader.readInt8();
    }

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        int newVal = localVars.getInt(index) + add;
        localVars.setInt(index,newVal);

    }
}
