package com.github.jvmgo.instructions.base;

import com.github.jvmgo.util.BytecodeReader;

/**
 操作数为索引 存取局部变量表
 */

public abstract class Index8Instruction implements Instruction {
     public Integer index ;//局部变量表索引


    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.nextU1toInt();
    }

}
