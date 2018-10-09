package com.github.jvmgo.instructions.constants;


import com.github.jvmgo.instructions.base.Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.util.BytecodeReader;

//从操作数中获取一个short型整数，扩展成int型，然后推入栈顶
public class Sipush implements Instruction {
    private int val;
    @Override
    public void fetchOperands(BytecodeReader reader) {
        val=reader.nextU2ToShort();
    }

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().push(val);
    }
}
