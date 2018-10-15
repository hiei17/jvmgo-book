package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.MemberInfo;
import com.github.jvmgo.classFile.attributeInfo.CodeAttribute;
import com.github.jvmgo.rtda.heap.util.MethodDescriptorParser;
import lombok.Getter;

import java.util.ArrayList;
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

    private Method(MemberInfo methodInfo, CClass ownClass) {
        super(methodInfo, ownClass);
        MethodDescriptor methodDescriptor = (new MethodDescriptorParser(this.descriptor)).parse();
        calcArgSlotCount( methodDescriptor.getParameterTypes());
        if(this.isNative()){
            injectCodeAttribute(methodDescriptor.getReturnType());
        }
    }

   /* 我们用__Java虚拟机栈__执行本地方法，
    Java虚拟机规范预留了两条指令，操作码分别是0xFE和0xFF。

    下面将使用0xFE指令来达到这个目的*/
    private void injectCodeAttribute(String returnType) {
        //本地方法帧的操作数栈至少要能容纳返回值，
        //为了简化代码，暂时给maxStack字段赋值为4
        maxStack=4;
       // 本地方法帧的局部变量表只用来存放参数值，所以把argSlotCount赋给maxLocals 字段刚好
        maxLocals=argSlotCount;
        code=new byte[2];
        code[0]= (byte) 0xfe;
        switch (returnType.charAt(0)){
            case 'V': code[1] =(byte) 0xb1;break;// return
            case 'L':case '[':code[1] = (byte) 0xb0;break; // areturn
            case 'D': code[1] =  (byte)0xaf;break;// dreturn
            case 'F': code[1] = (byte)0xae;break; // freturn
            case 'J': code[1] = (byte) 0xad;break; // lreturn
            default: code[1] = (byte) 0xac;break; // ireturn
        }
        
    }

    //计算参数个数
    private void calcArgSlotCount( ArrayList<String> parameterTypes) {
        
        for (String paramType : parameterTypes) {

            argSlotCount++;
            if ("J".equals(paramType) || "D".equals(paramType)) {
                argSlotCount++;
            }
        }
        if (!isStatic()) {
            argSlotCount++; // `this` reference
        }

    }

    public static Method[] newMethods(CClass ownClass, MemberInfo[] memberInfos) {

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
