package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/3 0003 15:19
 *
 * CONSTANT_XXXXref_info {
 *   u1 tag;
 *   u2 class_index;//指向CONSTANT_Class_info
 *   u2 name_and_type_index;//指向CONSTANT_NameAndType_info
 * }
 *
 */
public class ConstantMemberrefInfo implements ConstantInfo {

    protected ConstantPool constPool;

    protected int classIndex;
    protected int nameAndTypeIndex;


    public ConstantMemberrefInfo(ConstantPool aConstPool, BytecodeReader reader) {
        this.classIndex = reader.nextU2ToInt();
        this.nameAndTypeIndex = reader.nextU2ToInt();
        this.constPool = aConstPool;
    }


    @Override
    public String toString() {
        ConstantInfo[] constantInfos = constPool.getConstantInfos();
        ConstClassInfo constClassInfo= (ConstClassInfo) constantInfos[classIndex];
        ConstantNameAndTypeInfo nameAndTypeInfo= (ConstantNameAndTypeInfo) constantInfos[nameAndTypeIndex];
        return "ConstantMemberrefInfo{" +
                 constClassInfo +"  "+
                  nameAndTypeInfo +
                '}';
    }

    protected String name;
    protected String type;

    protected void  resolveNameAndType() {
        ConstantNameAndTypeInfo nameAndTypeInfo= (ConstantNameAndTypeInfo) constPool.getContantInfo(nameAndTypeIndex);
        name=nameAndTypeInfo.getName();
        type=nameAndTypeInfo.getType();
    }
}
