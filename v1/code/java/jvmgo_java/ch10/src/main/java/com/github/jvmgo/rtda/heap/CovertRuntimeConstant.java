package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.constantPool.ConstantInfo;
import com.github.jvmgo.rtda.heap.ref.RuntimeConstantPool;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 15:13
 */
public interface CovertRuntimeConstant extends ConstantInfo {
    Object getValue(RuntimeConstantPool runtimeConstantPool);
}
