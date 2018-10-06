package com.github.jvmgo.instructions.base;

import com.github.jvmgo.util.BytecodeReader;

/**
 操作数为索引 取常量池
 */
public abstract class Index16Instruction implements Instruction {
  private int index ;//常量池索引


    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.nextU2ToInt();
    }

}
