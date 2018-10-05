package com.github.jvmgo.instructions.constants;


import com.github.jvmgo.instructions.base.Instruction;
import com.github.jvmgo.rtda.Zframe;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/4 0004 20:37
 */
public class Bipush implements Instruction {
    private Integer val;
    @Override
    public void fetchOperands(BytecodeReader reader) {
        val=reader.nextU1toInt();
    }

    @Override
    public void execute(Zframe frame) {
        frame.getOperandStack().push(val);
    }
}
