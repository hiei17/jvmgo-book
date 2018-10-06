package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/3 0003 12:41
 */
public class ConstantDoubleInfo implements ConstantInfo{

    private Double value;

    public ConstantDoubleInfo( BytecodeReader reader) {
        //u4
        value  = reader.next2U4Double();
    }

    @Override
    public String getValue() {
        return value+"";
    }

    @Override
    public String toString() {
        return "Double:"+value;
    }
}
