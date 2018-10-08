package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.MemberInfo;

import java.util.Arrays;

/**
 * @Author: panda
 * @Date: 2018/10/6 0006 21:16
 */
public class Field extends ClassMember {

    int constValueIndex ;//classFile得 在属性里 字段的初始值index


    private   int slotId          ;//类加载时计算

    private Field(MemberInfo memberInfo, JClass jClass) {

        super(memberInfo,jClass);//field method只有属性不同
    }


    public static   Field[] newFields(JClass jvmClass, MemberInfo[] memberInfos) {

       return Arrays.stream(memberInfos)
               .map(memberInfo-> new Field(memberInfo,jvmClass))
               .toArray(Field[]::new);

    }

     @Override
     void copyAttributes(MemberInfo info) {

        //暂时只有值属性
        constValueIndex=info.getConstantValueIndex();
    }


    public boolean isLongOrDouble() {
        return "J".equals(descriptor) || "D".equals(descriptor);
    }


    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }
}
