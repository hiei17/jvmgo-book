package com.github.jvmgo.classFile.attributeInfo;

import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/3 0003 19:47
 */
public class SyntheticAttribute implements AttributeInfo {
    @Override
    public AttributeInfo readInfo(BytecodeReader reader) {
        return null;
    }
}
