package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.rtda.heap.*;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 16:25
 */
public class ConstantFieldRefInfo extends ConstantMemberrefInfo  implements CovertRuntimeConstant {


    public ConstantFieldRefInfo(ConstantPool aConstPool, BytecodeReader reader) {
        super(aConstPool, reader);
    }

    @Override
    public FieldRef getValue(RuntimeConstantPool runtimeConstantPool) {

        //就用使用类的加载器
        JClass useClass = runtimeConstantPool.getJclass();
        String className = constPool.getUTF8(super.classIndex);
        resolveNameAndType();

        return new FieldRef(useClass,className,name,type);

    }




}