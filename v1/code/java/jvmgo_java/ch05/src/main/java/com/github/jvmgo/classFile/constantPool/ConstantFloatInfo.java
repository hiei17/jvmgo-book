package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/3 0003 12:41
 */
public class ConstantFloatInfo implements ConstantInfo{

    private float value;

    public ConstantFloatInfo( BytecodeReader reader) {
        //u4
        value = reader.nextU4ToFloat();
    }

    @Override
    public String getValue() {
        return value+"";
    }

    @Override
    public String toString() {
        return "Float: "+value;
    }
}
