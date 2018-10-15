package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.rtda.heap.*;
import com.github.jvmgo.rtda.heap.ref.MethodRef;
import com.github.jvmgo.rtda.heap.ref.RuntimeConstantPool;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 16:25
 */
public class ConstantMethodRefInfo extends ConstantMemberrefInfo  implements CovertRuntimeConstant {


    public ConstantMethodRefInfo(ConstantPool aConstPool, BytecodeReader reader) {
        super(aConstPool, reader);
    }

    @Override
    public MethodRef getValue(RuntimeConstantPool runtimeConstantPool) {
        //就用使用类的加载器
        CClass useClass = runtimeConstantPool.getClazz();
        String className = constPool.getUTF8(super.classIndex);
        resolveNameAndType();

        return new MethodRef(useClass,className,name,type);
    }


}
