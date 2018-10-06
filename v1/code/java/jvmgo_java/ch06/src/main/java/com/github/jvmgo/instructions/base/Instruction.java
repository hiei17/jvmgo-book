package com.github.jvmgo.instructions.base;

import com.github.jvmgo.rtda.Zframe;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/4 0004 15:02
 */
public interface Instruction {


    //从字节码中提取操作数
    void fetchOperands(BytecodeReader reader);
    //执行指令
    void execute(Zframe frame);
}
