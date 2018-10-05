package com.github.jvmgo.instructions.extended;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Zframe;
import com.github.jvmgo.util.BytecodeReader;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc: goto_w指令和goto指令的唯一区别就是索引从2字节变成了4字节
 */
public class GOTO_W extends BranchInstruction {


    @Override
    public void fetchOperands(BytecodeReader reader) {
        super.offSet = reader.nextU4ToInt();
    }

    @Override
    public void execute(Zframe frame) {
        super.branch(frame);
    }
}
