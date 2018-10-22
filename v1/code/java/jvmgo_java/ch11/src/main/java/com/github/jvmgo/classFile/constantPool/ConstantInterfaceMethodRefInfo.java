package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.rtda.heap.CovertRuntimeConstant;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.ref.InterfaceMethodRef;
import com.github.jvmgo.rtda.heap.RuntimeConstantPool;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 16:25
 */
public class ConstantInterfaceMethodRefInfo extends ConstantMemberrefInfo  implements CovertRuntimeConstant {


    public ConstantInterfaceMethodRefInfo(ConstantPool aConstPool, BytecodeReader reader) {
        super(aConstPool, reader);
    }

    @Override
    public InterfaceMethodRef getValue(RuntimeConstantPool runtimeConstantPool) {
        //就用使用类的加载器
        CClass useClass = runtimeConstantPool.getClazz();
        String className = constPool.getUTF8(super.classIndex);
        resolveNameAndType();

        return new InterfaceMethodRef(useClass,className,name,type);
    }

 
}
