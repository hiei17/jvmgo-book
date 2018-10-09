package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.MemberInfo;
import com.github.jvmgo.classFile.attributeInfo.CodeAttribute;
import com.github.jvmgo.rtda.heap.util.MethodDescriptorParser;
import lombok.Getter;

import java.util.Arrays;

import static com.github.jvmgo.rtda.heap.Access_flags.*;

/**
 * @Author: panda
 * @Date: 2018/10/6 0006 21:40
 */
@Getter
public class Method extends ClassMember {

    private int maxStack;
    private int maxLocals;
    private byte[] code;

    private int argSlotCount;

    private Method(MemberInfo memberInfo, JClass ownClass) {
        super(memberInfo, ownClass);
        calcArgSlotCount();
    }

    //计算参数个数
    private void calcArgSlotCount() {

        MethodDescriptor methodDescriptor = (new MethodDescriptorParser(this.descriptor)).parse();

        for (String paramType : methodDescriptor.getParameterTypes()) {

            argSlotCount++;
            if ("J".equals(paramType) || "D".equals(paramType)) {
                argSlotCount++;
            }
        }
        if (!isStatic()) {
            argSlotCount++; // `this` reference
        }

    }

    public static Method[] newMethods(JClass ownClass, MemberInfo[] memberInfos) {

        return Arrays.stream(memberInfos)
                .map(memberInfo -> new Method(memberInfo, ownClass))
                .toArray(Method[]::new);

    }

    @Override
    void copyAttributes(MemberInfo info) {

        //暂时只处理Code属性
        CodeAttribute codeAttribute = info.getCodeAttr();
        if (codeAttribute == null) {
            return;
        }
        maxLocals = codeAttribute.getMaxLocals();
        maxStack = codeAttribute.getMaxStack();
        code = codeAttribute.getCode();
    }

    public boolean isNative() {
        return 0 != (accessFlags & ACC_NATIVE);
    }
    public boolean isAbstract() {
        return 0 != (accessFlags & ACC_ABSTRACT);
    }

}
