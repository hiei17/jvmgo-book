package com.github.jvmgo.classFile;


import com.github.jvmgo.classFile.attributeInfo.AttributeInfo;
import com.github.jvmgo.classFile.attributeInfo.CodeAttribute;
import com.github.jvmgo.classFile.attributeInfo.ConstantValueAttribute;
import com.github.jvmgo.classFile.constantPool.ConstantPool;
import com.github.jvmgo.util.BytecodeReader;
import lombok.Getter;

/*

field_info {
        u2 access_flags;//访问标志
        u2 name_index;//常量池索引 名字
        u2 descriptor_index;//常量池索引  描述符

//属性表
        u2 attributes_count;
        attribute_info attributes[attributes_count];
        }

        */
@Getter
public class MemberInfo {

    private ConstantPool cp;

    private int accessFlags    ;
    private int nameIndex;
    private int  descriptorIndex ;
    private AttributeInfo[] attributes    ;






    public MemberInfo(ConstantPool constantPool, BytecodeReader reader) {
                cp=constantPool;
                accessFlags=reader.nextU2ToInt();
                nameIndex=reader.nextU2ToInt();
                descriptorIndex=reader.nextU2ToInt();
                attributes=AttributeInfo.readAttributes(reader, cp);
    }

    public static MemberInfo[] readMembers(ConstantPool constantPool, BytecodeReader reader) {
        int  fieldCount=reader.nextU2ToInt();
        MemberInfo[] memberInfos=new MemberInfo[fieldCount];

        for (int i=0;i<fieldCount;i++){
            memberInfos[i]=new MemberInfo(constantPool,reader);
        }

        return memberInfos;
    }

    public String getName() {

            return cp.getUTF8(nameIndex);

    }

    public String getDescriptor() {

            return cp.getUTF8(descriptorIndex);


    }

    public CodeAttribute getCodeAttr() {

        for (AttributeInfo attribute : attributes) {
            if(attribute instanceof CodeAttribute){
                return (CodeAttribute) attribute;
            }
        }
      return null;
    }


    public int getConstantValueIndex() {
        for (AttributeInfo attribute : attributes) {
            if (attribute instanceof ConstantValueAttribute){
                return ((ConstantValueAttribute) attribute).constantValueIndex;
            }
        }
       return -99;
    }

}
