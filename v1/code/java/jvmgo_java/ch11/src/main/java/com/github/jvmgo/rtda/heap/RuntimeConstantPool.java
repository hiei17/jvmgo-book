package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.constantPool.ConstantInfo;
import com.github.jvmgo.classFile.constantPool.ConstantPool;
import lombok.Getter;

/**
 主要存放两类信息：字面量（literal）和符号引用（symbolic reference）。
 字面量包括整数、浮点数和字符串字面量；
 符号引用包括类符号引用、字段符号引用、方法符号引用和接口方法符号引用。
 */
public class RuntimeConstantPool {//属于方法区的一部分

    @Getter
    CClass clazz;//所属的类
    private   Object[] constants ;

    public RuntimeConstantPool(CClass clazz, ConstantPool classFileConstantPool) {

        this.clazz = clazz;
        ConstantInfo[] constantInfos = classFileConstantPool.getConstantInfos();
        int len = constantInfos.length;
        constants=new Object[len];
        for (int i = 1; i < constantInfos.length; i++) {
            ConstantInfo constantInfo = constantInfos[i];
            if (!(constantInfo instanceof CovertRuntimeConstant)){
                continue;
            }
            constants[i]=((CovertRuntimeConstant)constantInfo).getValue(this);
        }
    }

    public Object getConstant(int index)  {
        Object constant = constants[index];
        if (constant==null){
            throw new RuntimeException(String.format("No constants at index %d", index));
        }
        return constant;
    }


}
