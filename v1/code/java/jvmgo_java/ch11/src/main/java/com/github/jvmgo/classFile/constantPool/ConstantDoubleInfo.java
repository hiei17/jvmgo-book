package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.rtda.heap.CovertRuntimeConstant;
import com.github.jvmgo.rtda.heap.RuntimeConstantPool;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/3 0003 12:41
 */
public class ConstantDoubleInfo implements CovertRuntimeConstant {

    private Double value;

    public ConstantDoubleInfo( BytecodeReader reader) {
        //u4
        value  = reader.next2U4Double();
    }

    @Override
    public Object getValue(RuntimeConstantPool runtimeConstantPool) {
        return value;
    }

    @Override
    public String toString() {
        return "Double:"+value;
    }
}
