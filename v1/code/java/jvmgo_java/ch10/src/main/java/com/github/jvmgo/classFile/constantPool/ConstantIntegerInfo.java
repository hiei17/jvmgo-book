package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.rtda.heap.CovertRuntimeConstant;
import com.github.jvmgo.rtda.heap.ref.RuntimeConstantPool;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/3 0003 12:41
 */
public class ConstantIntegerInfo implements CovertRuntimeConstant {

    private Integer value;

    public ConstantIntegerInfo( BytecodeReader reader) {

        value = reader.next4ByteToSInt();
    }



    @Override
    public Object getValue(RuntimeConstantPool runtimeConstantPool) {
        return value;
    }

    @Override
    public String toString() {
        return "Integer: "+value;
    }

}
