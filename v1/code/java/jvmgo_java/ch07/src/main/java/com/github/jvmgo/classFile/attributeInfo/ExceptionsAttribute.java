package com.github.jvmgo.classFile.attributeInfo;

import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/3 0003 21:37
 */
public class ExceptionsAttribute implements AttributeInfo {
    private int[] eceptionIndexTable;

    @Override
    public AttributeInfo readInfo(BytecodeReader reader) {
       eceptionIndexTable = reader.nextUint16s();
       return this;
    }
}
